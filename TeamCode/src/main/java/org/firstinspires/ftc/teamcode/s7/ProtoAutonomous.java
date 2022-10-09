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

import org.firstinspires.ftc.teamcode.s7.framework.tfHandler;

import java.util.List;

@Autonomous
public class ProtoAutonomous extends OpMode{

    /*
    * Variables and Constants
    */

    /* Vuforia */

    private static final String VUFORIA_KEY =    //Vufoira license key
            "AUi0aKj/////AAABmXTlGAMS7ElAl7wgWIxVKWxeoxIvuQjEO8bL3NSZa1QAvP6uPT1n2wcNinINBfUHvxS7W3cNP2Z7SMmFvuZnUlfv4zZ7it6zz1A/KALMuKQ0/64zdM2AsJH7TotFBlg3EhyNzKcTvw/tclxaO8AiQualjV2g1wmiyIjosLF5zEKVfEQKRlf13RJZX1F9qElreZv+SJ6guswbv50k8ZiwOKLGtSGtzNxgf9GCLHDwt1WUTK6a+upJPJA9uddiC9gPoqLTc6EsDMQ81NWvKLjk8YHQxVZUTkFYpV6RfmABwhkudAWbIcv9voWA2tVAGDTmLZKwo1U8w+WBDvSOZdZCDowy0e58SBhT33g0o1m0HKCz";

    private VuforiaLocalizer vuforia;    //Instance of vuforia localizer



    /* TensorFlow */

    private static final String TFOD_MODEL_ASSET = "PowerPlay.tflite";      //Path to TensorFlow model

    private TFObjectDetector tfod;   //TensorFlow object detection engine instance

    private static final String[] LABELS = {    //Tensor flow labels
            "1 Bolt",
            "2 Bulb",
            "3 Panel"
    };



    //Core

    //TODO: Change this to enum or other better type
    private String signalFront = ""; //Stores image on front of signal, determined on loop()



    @Override
    public void init() {
        //TF relies on Vuforia, so init Vuforia first
        vuforia = tfHandler.initVuforia(VUFORIA_KEY);
        tfod = tfHandler.initTfod(hardwareMap, vuforia, TFOD_MODEL_ASSET, LABELS);

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


        findSignalFront();
        telemetry.addData("Signal front", signalFront);
        telemetry.update();


    }


    private void findSignalFront() {
        if (signalFront == "") {     //If he haven't found the signal front, try again
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions(); //Get new recognitions, if any

            if (updatedRecognitions != null) {
                double bestConfidence = 0.0;

                for (Recognition recognition : updatedRecognitions) {

                    if (recognition.getConfidence() > bestConfidence) {
                        signalFront = recognition.getLabel();
                        bestConfidence = recognition.getConfidence();
                    }
                }
            }
        }
    }



}
