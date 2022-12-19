package org.firstinspires.ftc.teamcode.s7;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.s7.framework.S7Robot;

@Autonomous
public class AutoBlueRight extends LinearOpMode {

    protected S7Robot robot;

    //All strafing is done with strafeRight.
    //Strafe inward using strafeRight(inward * x); outward with strafeRight(outward * x)
    //In inherited programs, change these variables to match orientation on field
    protected double inward = -1;
    protected double outward = 1;

    public void runOpMode() throws InterruptedException {

        /*               Init                       */
        robot = new S7Robot(hardwareMap, "LiftMotor", "ClawLeft", "ClawRight");

        waitForStart();
        /*               Run                       */

        robot.strafeRight(outward * 26);
        robot.waitForRR(this);
        robot.forward(51);
        robot.waitForRR(this);
        robot.turn(inward * 90);
        robot.waitForRR(this);
    }

}
