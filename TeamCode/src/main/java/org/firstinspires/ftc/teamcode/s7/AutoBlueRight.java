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
    protected double[] parkDistance = {-35, -10, 27};
    protected int inward = -1;
    protected int outward = 1;

    protected void initParameters() {
        parkDistance[0] = 1;
        parkDistance[1] = 28;
        parkDistance[2] = 52;
        inward = -1;
        outward = 1;
    }

    public void runOpMode() throws InterruptedException {

        /*               Init                       */
        robot = new S7Robot(hardwareMap, "LeftLiftMotor", "RightLiftMotor", "ClawLeft", "ClawRight");
        robot.setGrabberPosition(0.45);
        robot.setArmPower(0);

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

        //Move to high junction
        robot.forward(4);
        robot.waitForRR(this);
        robot.turn(90 * outward);
        robot.waitForRR(this);
        robot.forward(18);
        robot.waitForRR(this);
        strafe(43 * outward);
        robot.waitForRR(this);

        //Deploy cone
        robot.setArmPower(1);
        robot.setArmPosition(918);
        robot.waitForArm(this);
        robot.forward(5);
        robot.waitForRR(this);
        robot.setGrabberPosition(1);
        robot.setArmPower(0);

//
        ////Back up and get ready to park
        ////robot.back(8);
        //robot.waitForRR(this);
        //strafe(12 * inward);
        //robot.waitForRR(this);
        //robot.forward(26);
        //robot.waitForRR(this);
//
        ////Park in the correct spot
        //if (signalLabel == "1 Bolt" || signalLabel == "") {
        //    strafe(parkDistance[0]);
        //} if (signalLabel == "2 Bulb") {
        //    strafe(parkDistance[1]);
        //} if (signalLabel == "3 Panel") {
        //    strafe(parkDistance[2]);
        //}
        //robot.waitForRR(this);
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
