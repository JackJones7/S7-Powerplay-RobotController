package org.firstinspires.ftc.teamcode.s7;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class AutoBlueLeft extends AutoBlueRight{

     protected void initParameters() {
        parkDistance[0] = 50;
        parkDistance[1] = 28;
        parkDistance[2] = 1;
        inward = 1;
        outward = -1;
    }

}
