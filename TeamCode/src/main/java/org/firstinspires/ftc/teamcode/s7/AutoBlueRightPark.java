package org.firstinspires.ftc.teamcode.s7;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.s7.framework.ImageRecognition;
import org.firstinspires.ftc.teamcode.s7.framework.S7Robot;

@Autonomous
public class AutoBlueRightPark extends LinearOpMode {

    protected S7Robot robot;
    protected ImageRecognition imageRecognition;

    protected String signalLabel = "";
    protected ElapsedTime timer;
    protected double[] parkDistance = {-35, -10, 27};

    protected void initParameters() {
        parkDistance[0] = -35;
        parkDistance[1] = -10;
        parkDistance[2] = 27;
    }

    public void runOpMode() throws InterruptedException {

        /*               Init                       */
        robot = new S7Robot(hardwareMap, "LeftLiftMotor", "ClawLeft", "ClawRight");
        robot.setGrabberPosition(0.6);

        initParameters();

        telemetry.addData("Image Recognition", "Initializing...");
        telemetry.update();
        imageRecognition = new ImageRecognition();
        telemetry.addData("Image Recognition", "Ready");
        telemetry.update();

        timer = new ElapsedTime();
        waitForStart();

        /*               Run                       */
        timer.reset();
        while (signalLabel == "" && !isStopRequested() && timer.seconds() <= 7.0) {
            signalLabel = imageRecognition.detectSignal();
            telemetry.addData("Signal Label", signalLabel);
            telemetry.update();
        }

        //Park in the correct spot
        if (signalLabel == "1 S7" || signalLabel == "") {
            strafe(parkDistance[0]);
        } if (signalLabel == "2 Ring") {
            strafe(parkDistance[1]);
        } if (signalLabel == "3 Yoda") {
            strafe(parkDistance[2]);
        }
        telemetry.addData("park distance", parkDistance);
        telemetry.update();
        robot.waitForRR(this);
        robot.forward(28);
        robot.waitForRR(this);
    }


    //Strafes left or right depending on sign of distance. Use only when robot is initialized.
    protected void strafe(double distance) {
        if (distance >= 0) {
            robot.strafeRight(distance);
            return;
        }
        robot.strafeLeft(-distance);
    }

}
