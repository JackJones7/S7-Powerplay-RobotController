package org.firstinspires.ftc.teamcode.s7;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.s7.framework.S7Robot;

@Config
@Autonomous
public class ArmHeightTest extends LinearOpMode {

    private S7Robot robot;

    public static int armHeight = 0;  //Set this in dashboard

    public void runOpMode() throws InterruptedException {
        /*            Init                  */
        robot = new S7Robot(hardwareMap, "LeftLiftMotor", "RightLiftMotor", "ClawLeft", "ClawRight");
        robot.setArmPosition(0);
        robot.setArmPower(1);

        waitForStart();
        /*            Run                   */

        /*            Loop                  */
        while (opModeIsActive() && !isStopRequested()) {
            robot.setArmPosition(armHeight);
        }

    }

}
