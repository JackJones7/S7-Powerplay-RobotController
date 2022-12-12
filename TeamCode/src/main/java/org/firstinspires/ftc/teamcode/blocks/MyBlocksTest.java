package org.firstinspires.ftc.teamcode.blocks;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportClassToBlocks;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

@ExportClassToBlocks
public class MyBlocksTest extends BlocksOpModeCompanion {

    @ExportToBlocks (
            comment = "This does something really cool",
            tooltip = "Greet the recipient",
            parameterLabels = {"Recipient"}
    )
    public static String greet (String recipient) {
        return "Hello, " + recipient;
    }

}
