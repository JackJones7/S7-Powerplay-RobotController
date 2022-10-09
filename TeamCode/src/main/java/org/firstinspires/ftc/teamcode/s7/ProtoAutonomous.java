package org.firstinspires.ftc.teamcode.s7;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous
public class ProtoAutonomous extends OpMode{

    /*
    * Constants
    */

    /* Vuforia */

    //Vufoira license key
    private static final String VUFORIA_KEY =
            "AUi0aKj/////AAABmXTlGAMS7ElAl7wgWIxVKWxeoxIvuQjEO8bL3NSZa1QAvP6uPT1n2wcNinINBfUHvxS7W3cNP2Z7SMmFvuZnUlfv4zZ7it6zz1A/KALMuKQ0/64zdM2AsJH7TotFBlg3EhyNzKcTvw/tclxaO8AiQualjV2g1wmiyIjosLF5zEKVfEQKRlf13RJZX1F9qElreZv+SJ6guswbv50k8ZiwOKLGtSGtzNxgf9GCLHDwt1WUTK6a+upJPJA9uddiC9gPoqLTc6EsDMQ81NWvKLjk8YHQxVZUTkFYpV6RfmABwhkudAWbIcv9voWA2tVAGDTmLZKwo1U8w+WBDvSOZdZCDowy0e58SBhT33g0o1m0HKCz";

    //Instance of vuforia localizer
    private VuforiaLocalizer vuforia;


    /* TensorFlow */

    //Path to TensorFlow model
    private static final String TFOD_MODEL_ASSET = "PowerPlay.tflite"; //Image recognition model

    //TensorFlow object detection engine instance
    private TFObjectDetector tfod;

    //Tensor flow labels
    private static final String[] LABELS = {
            "1 Bolt",
            "2 Bulb",
            "3 Panel"
    };


    @Override
    public void init() {
        //TF relies on Vuforia, so init Vuforia first
        initVuforia();
        initTfod();

        //If TF was successfully initialized
        if (tfod != null) {
            tfod.activate();

            //If problems arise, try messing with the zoom. see ConceptTensorFlowObjectDetection line 113
        }
        //TODO: Add Telemetry if TF fails

        telemetry.addData("Tensorflow", "Ready");
        telemetry.update();
    }

    /*
    @Override
    public void start() {

    }
    */

    @Override
    public void loop() {
        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions(); //Get new recognitions, if any

        if (updatedRecognitions != null) {
            for (Recognition recognition : updatedRecognitions) {
                telemetry.addData("Image", recognition.getLabel());
                telemetry.addData("confidence", recognition.getConfidence());
            }
        }
    }



    //TODO: Move these init functions to static class for abstraction
    private void initVuforia() {
        //Create parameters to pass to vuforia
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.75f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 300;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);

        // Use loadModelFromAsset() if the TF Model is built in as an asset by Android Studio
        // Use loadModelFromFile() if you have downloaded a custom team model to the Robot Controller's FLASH.
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
        // tfod.loadModelFromFile(TFOD_MODEL_FILE, LABELS);
    }


}
