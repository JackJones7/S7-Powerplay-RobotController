package org.firstinspires.ftc.teamcode.s7.framework;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class S7Robot {

    //hardware map
    private HardwareMap hardwareMap;
    //sample mechanum drive
    private SampleMecanumDrive drive;
    //arm elevation motor
    private DcMotor liftMotorLeft;
    private DcMotor liftMotorRight;
    //left grabber servo
    private Servo grabberLeft;
    //right grabber servo
    private Servo grabberRight;

    /*constructor: takes a hardware map and names for arm motor and grabber servos.
    * gets motor and servos from hardware map
    * initializes drive
    * initializes elevation motor for runToPosition mode
    * */
    public S7Robot(HardwareMap hardwareMap, String liftMotorLeftName, String liftMotorRightName, String grabberLeftName, String grabberRightName) {
        this.hardwareMap = hardwareMap;
        drive = new SampleMecanumDrive(hardwareMap);

        this.liftMotorLeft = hardwareMap.get(DcMotor.class, liftMotorLeftName);
        this.liftMotorRight = hardwareMap.get(DcMotor.class, liftMotorRightName);
        this.grabberLeft = hardwareMap.get(Servo.class, grabberLeftName);
        this.grabberRight = hardwareMap.get(Servo.class, grabberRightName);

        this.grabberLeft.setDirection(Servo.Direction.REVERSE);

        this.liftMotorLeft.setTargetPosition(0);
        this.liftMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /*constructor: same as above + takes start pose, sets drive's pose */


    /*Move forward: Takes move distance, uses RR to move forward that distance from pose estimate*/
    public void forward(double distance) {
        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                .forward(distance)
                .build());
    }

    /*Move back: Takes move distance, uses RR to move backward that distance from pose estimate*/
    public void back(double distance) {
        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                .back(distance)
                .build());
    }

    /*Strafe Right: Takes move distance, uses RR to strafe right that distance from pose estimate*/
    public void strafeRight(double distance) {
        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                .strafeRight(distance)
                .build());
    }

    /*Strafe Left: Takes move distance, uses RR to strafe left that distance from pose estimate*/
    public void strafeLeft(double distance) {
        drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                .strafeLeft(distance)
                .build());
    }

    /*Turn: takes angle, use RR to turn at that angle*/
    public void turn(double angle) {
        drive.turn(Math.toRadians(angle));
    }

    /*Set Arm Position: Sets arm motor to RunToPosition mode if necessary, set arm motor's position*/
    public void setArmPosition(int position) {
        if (liftMotorLeft.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
            liftMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        liftMotorLeft.setTargetPosition(position);
        liftMotorRight.setTargetPosition(position);
    }

    /*Power Arm: set arm motor's power*/
    public void setArmPower(double power) {
        liftMotorLeft.setPower(power);
        liftMotorRight.setPower(power);
    }

    /*Set grabber position: Set position of left and right servos. 0 is closed, 1 is open*/
    public void setGrabberPosition(double position) {
        grabberRight.setPosition(position);
        grabberLeft.setPosition(position);
    }

    //public void waitForServos(LinearOpMode opMode) {
    //
    //}

    public void waitForRR(LinearOpMode opMode) {
        while (!opMode.isStopRequested() && drive.isBusy()) {
            opMode.idle();
        }
    }

    public void waitForArm(LinearOpMode opMode) {
        while (!opMode.isStopRequested() && liftMotorLeft.isBusy() && liftMotorRight.isBusy()) {
            opMode.idle();
        }
    }

    /*IsBusy: return drive's IsBusy*/
    public boolean isBusy() {
        return drive.isBusy();
    }
}
