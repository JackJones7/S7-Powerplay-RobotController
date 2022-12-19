package org.firstinspires.ftc.teamcode.s7.framework;

import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

public class ImageRecognition {

    private static final String VUFORIA_KEY =
            "AUi0aKj/////AAABmXTlGAMS7ElAl7wgWIxVKWxeoxIvuQjEO8bL3NSZa1QAvP6uPT1n2wcNinINBfUHvxS7W3cNP2Z7SMmFvuZnUlfv4zZ7it6zz1A/KALMuKQ0/64zdM2AsJH7TotFBlg3EhyNzKcTvw/tclxaO8AiQualjV2g1wmiyIjosLF5zEKVfEQKRlf13RJZX1F9qElreZv+SJ6guswbv50k8ZiwOKLGtSGtzNxgf9GCLHDwt1WUTK6a+upJPJA9uddiC9gPoqLTc6EsDMQ81NWvKLjk8YHQxVZUTkFYpV6RfmABwhkudAWbIcv9voWA2tVAGDTmLZKwo1U8w+WBDvSOZdZCDowy0e58SBhT33g0o1m0HKCz";
    private static final String TFOD_ASSET = "PowerPlay.tflite";
    private static final String[] LABELS = {
            "1 Bolt",
            "2 Bulb",
            "3 Panel"
    };

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    public ImageRecognition () {

    }

}
