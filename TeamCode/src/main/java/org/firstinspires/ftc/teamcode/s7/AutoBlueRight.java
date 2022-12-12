package org.firstinspires.ftc.teamcode.s7;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.s7.framework.S7Robot;

@Autonomous
public class AutoBlueRight extends LinearOpMode {

    protected S7Robot robot;

    public void runOpMode() throws InterruptedException {

        /*               Init                       */
        robot = new S7Robot(hardwareMap, "LiftMotor", "ClawLeft", "ClawRight");

        waitForStart();
        /*               Run                       */

        robot.strafeRight(26);
        robot.waitForRR(this);
        robot.forward(51);
        robot.waitForRR(this);
        robot.turn(-90);
        robot.waitForRR(this);
    }

}
