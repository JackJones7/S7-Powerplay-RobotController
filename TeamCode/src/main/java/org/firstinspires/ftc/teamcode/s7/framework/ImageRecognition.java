package org.firstinspires.ftc.teamcode.s7.framework;

import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import java.util.List;

public class ImageRecognition {

    private static final String VUFORIA_KEY =
            "AUi0aKj/////AAABmXTlGAMS7ElAl7wgWIxVKWxeoxIvuQjEO8bL3NSZa1QAvP6uPT1n2wcNinINBfUHvxS7W3cNP2Z7SMmFvuZnUlfv4zZ7it6zz1A/KALMuKQ0/64zdM2AsJH7TotFBlg3EhyNzKcTvw/tclxaO8AiQualjV2g1wmiyIjosLF5zEKVfEQKRlf13RJZX1F9qElreZv+SJ6guswbv50k8ZiwOKLGtSGtzNxgf9GCLHDwt1WUTK6a+upJPJA9uddiC9gPoqLTc6EsDMQ81NWvKLjk8YHQxVZUTkFYpV6RfmABwhkudAWbIcv9voWA2tVAGDTmLZKwo1U8w+WBDvSOZdZCDowy0e58SBhT33g0o1m0HKCz";
    private static final String TFOD_MODEL_ASSET = "PowerPlay.tflite";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    public static final String[] LABELS = {
            "1 Bolt",
            "2 Bulb",
            "3 Panel"
    };


    public ImageRecognition () {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parameters.fillCameraMonitorViewParent = true;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters();
        tfodParameters.minResultConfidence = 0.75f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 300;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
        tfod.activate();
    }

    public List<Recognition> getRecognitions() {
        return tfod.getRecognitions();
    }

    public List<Recognition> getUpdatedRecognitions() {
        return tfod.getUpdatedRecognitions();
    }

    public String detectSignal() {
        List<Recognition> updatedRecognitions = getUpdatedRecognitions();
        if (updatedRecognitions == null) {return "";}

        double bestConfidence = 0.0;
        String label = "";

        for (Recognition recognition : updatedRecognitions) {
            if (recognition.getConfidence() > bestConfidence) {
                label = recognition.getLabel();
                bestConfidence = recognition.getConfidence();
            }
        }

        return label;
    }

}
