package org.firstinspires.ftc.teamcode.s7;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.s7.framework.ImageRecognition;
import org.firstinspires.ftc.teamcode.s7.framework.S7Robot;

@Autonomous
public class AutoBlueRight extends LinearOpMode {

    protected S7Robot robot;
    protected ImageRecognition imageRecognition;

    protected String signalLabel = "";
    protected ElapsedTime timer;

    public void runOpMode() throws InterruptedException {

        /*               Init                       */
        robot = new S7Robot(hardwareMap, "LiftMotor", "ClawLeft", "ClawRight");
        robot.setGrabberPosition(0.6);

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

        //Move to nearest ground junction
        //robot.strafeRight(10);
        //robot.waitForRR(this);
        //robot.forward(7);
        //robot.waitForRR(this);

        //Park in the correct spot
        if (signalLabel == "1 Bolt") {
            strafe(-35);
        } if (signalLabel == "2 Bulb") {
            robot.strafeLeft(10);
        } if (signalLabel == "3 Panel") {
            robot.strafeRight(27);
        }
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
