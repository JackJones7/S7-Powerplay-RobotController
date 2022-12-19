package org.firstinspires.ftc.teamcode.blocks;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;
import org.firstinspires.ftc.teamcode.s7.framework.S7Robot;

public class S7RobotBlocks extends BlocksOpModeCompanion {

    @ExportToBlocks (
            tooltip = "Create new S7Robot",
            parameterLabels = {"Arm Motor Name", "Left Grabber Name", "Right Grabber Name"}
    )
    public static S7Robot S7Robot (String armMotorName, String grabberLeftName, String grabberRightName) {
        return new S7Robot(hardwareMap, armMotorName, grabberLeftName, grabberRightName);
    }

    @ExportToBlocks (
            tooltip = "Move robot forward",
            parameterLabels = {"Robot", "Distance"}
    )
    public static void forward(S7Robot robot, double distance) {
        robot.forward(distance);
    }

    @ExportToBlocks (
            tooltip = "Move robot backward",
            parameterLabels = {"Robot", "Distance"}
    )
    public static void back(S7Robot robot, double distance) {
        robot.back(distance);
    }

    @ExportToBlocks (
            tooltip = "Move robot right",
            parameterLabels = {"Robot", "Distance"}
    )
    public static void strafeRight(S7Robot robot, double distance) {
        robot.strafeRight(distance);
    }

    @ExportToBlocks (
            tooltip = "Move robot left",
            parameterLabels = {"Robot", "Distance"}
    )
    public static void strafeLeft(S7Robot robot, double distance) {
        robot.strafeLeft(distance);
    }

}
