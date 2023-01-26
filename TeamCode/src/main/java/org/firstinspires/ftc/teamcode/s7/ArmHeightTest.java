package org.firstinspires.ftc.teamcode.s7;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.s7.framework.S7Robot;

@TeleOp
public class ArmHeightTest extends LinearOpMode {

    private S7Robot robot;

    public int armHeight = 0;  //Set this in dashboard

    public void runOpMode() throws InterruptedException {
        /*            Init                  */
        robot = new S7Robot(hardwareMap, "LeftLiftMotor", "RightLiftMotor", "ClawLeft", "ClawRight");
        robot.setArmPosition(0);
        robot.setArmPower(1);

        waitForStart();
        /*            Run                   */

        /*            Loop                  */
        while (opModeIsActive() && !isStopRequested()) {
            armHeight += gamepad1.left_stick_y;
            robot.setArmPosition(armHeight);

            telemetry.addData("arm height", armHeight);
            telemetry.update();
        }

    }

}
