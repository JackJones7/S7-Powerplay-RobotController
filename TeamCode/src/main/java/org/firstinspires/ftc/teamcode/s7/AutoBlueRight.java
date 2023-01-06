package org.firstinspires.ftc.teamcode.s7;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.s7.framework.ImageRecognition;
import org.firstinspires.ftc.teamcode.s7.framework.S7Robot;

@Autonomous
public class AutoBlueRight extends LinearOpMode {

    protected S7Robot robot;
    protected ImageRecognition imageRecognition;

    protected String signalLabel = "";

    public void runOpMode() throws InterruptedException {

        /*               Init                       */
        robot = new S7Robot(hardwareMap, "LiftMotor", "ClawLeft", "ClawRight");
        robot.setGrabberPosition(0.3);

        imageRecognition = new ImageRecognition();

        waitForStart();

        /*               Run                       */
        //TODO: Add timer to keep this phase from holding up everything else
        while (signalLabel == "" && !isStopRequested()) {
            signalLabel = imageRecognition.detectSignal();
        }

        robot.strafeRight(22);
        robot.waitForRR(this);
        robot.forward(51);
        robot.waitForRR(this);
        robot.strafeLeft(10);
        robot.waitForRR(this);
        robot.turn(-90);
        robot.waitForRR(this);
        robot.forward(8);
        robot.waitForRR(this);
        robot.setGrabberPosition(0.2);
        sleep(500);
        robot.waitForRR(this);
        robot.back(50);
        robot.waitForRR(this);
        robot.turn(45);
        robot.waitForRR(this);
    }

}
