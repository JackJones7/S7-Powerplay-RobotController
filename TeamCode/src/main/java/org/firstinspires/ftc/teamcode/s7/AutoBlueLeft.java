package org.firstinspires.ftc.teamcode.s7;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class AutoBlueLeft extends AutoBlueRight{

     protected void initParameters() {
        parkDistance[0] = -27;
        parkDistance[1] = 10;
        parkDistance[2] = 35;
        inward = 1;
        outward = -1;
    }

}
