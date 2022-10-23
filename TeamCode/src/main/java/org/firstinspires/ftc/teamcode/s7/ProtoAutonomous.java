package org.firstinspires.ftc.teamcode.s7;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.s7.framework.tfHandler;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.List;

@Autonomous
public class ProtoAutonomous extends OpMode{

    /*
    * Variables and Constants
    */

    /* Vuforia */

    private static final String VUFORIA_KEY =    //Vufoira license key
            "AUi0aKj/////AAABmXTlGAMS7ElAl7wgWIxVKWxeoxIvuQjEO8bL3NSZa1QAvP6uPT1n2wcNinINBfUHvxS7W3cNP2Z7SMmFvuZnUlfv4zZ7it6zz1A/KALMuKQ0/64zdM2AsJH7TotFBlg3EhyNzKcTvw/tclxaO8AiQualjV2g1wmiyIjosLF5zEKVfEQKRlf13RJZX1F9qElreZv+SJ6guswbv50k8ZiwOKLGtSGtzNxgf9GCLHDwt1WUTK6a+upJPJA9uddiC9gPoqLTc6EsDMQ81NWvKLjk8YHQxVZUTkFYpV6RfmABwhkudAWbIcv9voWA2tVAGDTmLZKwo1U8w+WBDvSOZdZCDowy0e58SBhT33g0o1m0HKCz";

    private VuforiaLocalizer vuforia;    //Instance of vuforia localizer



    /* TensorFlow */

    private static final String TFOD_MODEL_ASSET = "PowerPlay.tflite";      //Path to TensorFlow model

    private TFObjectDetector tfod;   //TensorFlow object detection engine instance

    private static final String[] LABELS = {    //Tensor flow labels
            "1 Bolt",
            "2 Bulb",
            "3 Panel"
    };



    /* Core */

    private int jobIndex = 0;

    //TODO: Change this to enum or other better type
    private String signalFront = ""; //Stores image on front of signal, determined on loop()

    private SampleMecanumDrive drive;

    private double inwardDir = -1;

    /* A ton of trajectories */

    Trajectory trForward;
    Trajectory trOutward1Tile;
    Trajectory trOutward;
    Trajectory trForward3Tiles;

    private TrajectorySequence currentSequence;



    @Override
    public void init() {
        //TF relies on Vuforia, so init Vuforia first
        vuforia = tfHandler.initVuforia(VUFORIA_KEY);
        tfod = tfHandler.initTfod(hardwareMap, vuforia, TFOD_MODEL_ASSET, LABELS);
        drive = new SampleMecanumDrive(hardwareMap);

        //If TF was successfully initialized
        if (tfod != null) {
            tfod.activate();

            //If problems arise, try messing with the zoom. see ConceptTensorFlowObjectDetection line 113
        }
        //TODO: Add Telemetry if TF fails

        telemetry.addData("Tensorflow", "Ready");
        telemetry.update();

        buildTrajectories();
    }

    /*
    @Override
    public void start() {

    }
    */

    @Override
    public void loop() {

        //I guess this is a state machine
        switch(jobIndex) {

            case 0:
                findSignalFront();
                telemetry.addData("Signal front", signalFront);
                telemetry.update();

                if (signalFront != "") {
                    jobIndex++;
                }
                jobIndex++;
                break;

            case 1:

                currentSequence = drive.trajectorySequenceBuilder(new Pose2d())
                        .strafeRight(24.0)
                        .forward(72.0)
                        .build();

                break;

            //case 2:
            //    drive.followTrajectory(trForward3Tiles);
//
            //    if(!drive.isBusy()) {
            //        jobIndex++;
            //    }
//
            //    break;

        }

        if (currentSequence != null) {
            drive.followTrajectorySequence(currentSequence);
        }

        if (!drive.isBusy()) {
            currentSequence = null;
        }






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


    private void buildTrajectories() {
        trForward = drive.trajectoryBuilder(new Pose2d())
                .forward(12.0)
                .build();

        trOutward1Tile = drive.trajectoryBuilder(new Pose2d())
                .strafeRight(24.0)
                .build();

        trOutward = drive.trajectoryBuilder(new Pose2d())
                .strafeRight(24.0)
                .build();

        trForward3Tiles = drive.trajectoryBuilder(new Pose2d())
                .forward(72.0)
                .build();
    }

}
