package org.firstinspires.ftc.teamcode.blocks;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceBuilder;

//TODO: Get rid of this convoluted junk, make simple movement blocks
public class RoadrunnerBlocks extends BlocksOpModeCompanion {

    @ExportToBlocks (
        tooltip = "Create new SampleMechanumDrive",
        parameterLabels = {"Hardware Map"}
    )
    public static SampleMecanumDrive newSampleMechanumDrive() {
        return new SampleMecanumDrive(hardwareMap);
    }

    @ExportToBlocks (
            tooltip = "Get SampleMechanumDrive's pose estimate",
            parameterLabels = {"SampleMechanumDrive"}
    )
    public static Pose2d getPoseEstimate(SampleMecanumDrive drive) {
        return drive.getPoseEstimate();
    }

    @ExportToBlocks (
            tooltip = "Create new TrajectorySequenceBuilder",
            parameterLabels = {"SampleMechanumDrive"}
    )
    public static TrajectorySequenceBuilder newTrajectorySequenceBuilder(SampleMecanumDrive drive, Pose2d pose) {
        return drive.trajectorySequenceBuilder(pose);
    }

    @ExportToBlocks (
            tooltip = "Add forward movement to trajectory sequence",
            parameterLabels = {"TrajectorySequenceBuilder", "distance"}
    )
    public static void forward(TrajectorySequenceBuilder builder, float distance) {
        builder.forward(distance);
    }

}
