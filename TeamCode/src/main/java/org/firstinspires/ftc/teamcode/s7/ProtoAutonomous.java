package org.firstinspires.ftc.teamcode.s7;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.s7.framework.tfHandler;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.List;

@Autonomous
public class ProtoAutonomous extends LinearOpMode {

    private static final String VUFORIA_KEY =    //Vufoira license key
            "AUi0aKj/////AAABmXTlGAMS7ElAl7wgWIxVKWxeoxIvuQjEO8bL3NSZa1QAvP6uPT1n2wcNinINBfUHvxS7W3cNP2Z7SMmFvuZnUlfv4zZ7it6zz1A/KALMuKQ0/64zdM2AsJH7TotFBlg3EhyNzKcTvw/tclxaO8AiQualjV2g1wmiyIjosLF5zEKVfEQKRlf13RJZX1F9qElreZv+SJ6guswbv50k8ZiwOKLGtSGtzNxgf9GCLHDwt1WUTK6a+upJPJA9uddiC9gPoqLTc6EsDMQ81NWvKLjk8YHQxVZUTkFYpV6RfmABwhkudAWbIcv9voWA2tVAGDTmLZKwo1U8w+WBDvSOZdZCDowy0e58SBhT33g0o1m0HKCz";
    private static final String TFOD_MODEL_ASSET = "S7Signal.tflite";      //Path to TensorFlow model

    private static final String[] LABELS = {    //Tensor flow labels
            "1 S7",
            "2 Ring",
            "3 Yoda"
    };


    private VuforiaLocalizer vuforia;    //Instance of vuforia localizer
    private TFObjectDetector tfod;   //TensorFlow object detection engine instance
    private SampleMecanumDrive drive;

    //TODO: Change this to enum or other better type
    private String signalFront = ""; //Stores image on front of signal, determined on loop()


    public void runOpMode() throws InterruptedException {

        /*
        ************************Init**********************
        */

        drive = new SampleMecanumDrive(hardwareMap);

        vuforia = tfHandler.initVuforia(VUFORIA_KEY);
        tfod = tfHandler.initTfod(hardwareMap, vuforia, TFOD_MODEL_ASSET, LABELS);
        //If TF was successfully initialized
        if (tfod != null) {
            tfod.activate();

            //If problems arise, try messing with the zoom. see ConceptTensorFlowObjectDetection line 113
        }
        telemetry.addData("Tensorflow", "Ready");
        telemetry.update();
        //TODO: Add Telemetry if TF fails

        Pose2d startPose = new Pose2d(-34.0, 48, 90.0);
        drive.setPoseEstimate(startPose);

        waitForStart();

        /*
        ****************************Run****************************
        */

        if (isStopRequested()) return;

        findSignalFront();
        while (!isStopRequested() && signalFront == "") {
            findSignalFront();
            telemetry.addData("Recognition", "waiting...");
            telemetry.update();
        }
        telemetry.addData("Recognition", "found" + signalFront);
        telemetry.update();

        drive.followTrajectorySequence(drive.trajectorySequenceBuilder(startPose)
                .strafeRight(24.0)
                .forward(50.5)
                .turn(Math.toRadians(90.0))
                .build());
        while (!isStopRequested() && drive.isBusy()) {
            idle();
        }

        //Placeholder for code to grab cone

        drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .forward(48.0)
                .strafeLeft(12.0)
                .build());
        while (!isStopRequested() && drive.isBusy()) {
            idle();
        }

        //Placeholder for code to place cone

        drive.followTrajectorySequence((drive.trajectorySequenceBuilder(drive.getPoseEstimate()))
                .strafeRight(12.0)
                .back(48.0)
                .build());
        while(!isStopRequested() && drive.isBusy()) {
            idle();
        }

        //Placeholder for code to grab cone

        drive.followTrajectorySequence(drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .forward(48.0)
                .strafeLeft(12.0)
                .build());
        while (!isStopRequested() && drive.isBusy()) {
            idle();
        }

        //Placeholder for code to place cone

    }



    private void findSignalFront() {
        if (signalFront == "") {     //If he haven't found the signal front, try again
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions(); //Get new recognitions, if any

            if (updatedRecognitions != null) {
                double bestConfidence = 0.0;

                for (Recognition recognition : updatedRecognitions) {

                    if (recognition.getConfidence() > bestConfidence) {
                        signalFront = recognition.getLabel();
                        bestConfidence = recognition.getConfidence();
                    }
                }
            }
        }
    }


}
