package org.firstinspires.ftc.teamcode.wrappers;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ExtensionWrapperDD {
    HardwareMap hardwareMap;
    Telemetry telemetry;
    //DcMotor bottomMotor;
    //DcMotor topMotor;
    public Servo leftServo; //1
    public Servo rightServo; //0

    public Servo guideServo; //0 expansion
    DcMotorEx slideMotorRight; //0 expansion
    DcMotorEx slideMotorLeft; //1 expansion
    double leftSlideFactor = 0.98;
    public Integer slidePos = 0;
    int Ratio = 28;
    final int slideEncoderFactor = 20;

    boolean limit = true;
    int deltaCount = 0;
    boolean guideOut = true;


    double servoPos = 0.5;
    double currentRotation;
    double MotorTicks = ((((1+(46/17))) * (1+(46/11))) * 28);


    boolean open = true;

    public ExtensionWrapperDD(HardwareMap inHardwareMap, Telemetry inTelemetry) {
        hardwareMap = inHardwareMap;
        telemetry = inTelemetry;

        //bottomMotor  = hardwareMap.get(tDcMotor.class, "bottomMotor");
        //topMotor  = hardwareMap.get(DcMotor.class, "topMotor");
        leftServo = hardwareMap.get(Servo.class, "leftServo");
        rightServo = hardwareMap.get(Servo.class, "rightServo");
        guideServo = hardwareMap.get(Servo.class, "guideServo");
        slideMotorLeft = hardwareMap.get(DcMotorEx.class, "slideMotorLeft");
        slideMotorRight = hardwareMap.get(DcMotorEx.class, "slideMotorRight");
        slideMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotorLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        slideMotorLeft.setTargetPosition(0);
        slideMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotorRight.setDirection(DcMotorSimple.Direction.REVERSE);
        slideMotorRight.setTargetPosition(0);
    }
    double N = 10;
    int slowRange = 300;
    int slideOldPos = 0;
    public void PPArmMove(JoystickWrapper joystickWrapper) {
        if (joystickWrapper.gamepad1GetRightBumperDown()) {
            if (limit) {
                limit = false;
            } else {
                limit = true;
            }
        }

        slidePos = (int) (slideMotorRight.getTargetPosition() + (joystickWrapper.gamepad1GetRightTrigger()-joystickWrapper.gamepad1GetLeftTrigger()) * slideEncoderFactor);

        if (deltaCount == 0 && !limit) {
            deltaCount=1;
        }
        if (deltaCount == 1 && limit) {
            slideMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            slideMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            deltaCount = 0;
        }
       /* if (joystickWrapper.gamepad2GetDDown()) {
            clawBase.setPower(-.5);
            servoPos = clawBase.getPosition() - .01;
        }
        if(joystickWrapper.gamepad2GetDUp()) {
            servoPos = clawBase.getPosition();

        }*/

        //clawBase.setPower(joystickWrapper.gamepad1GetLeftStickY());

        if (joystickWrapper.gamepad1GetA()) {
            slidePos = 70;
            if (guideServo.getPosition() > 0.06) {
                guideServo.setPosition(0.05);
            }
        }else if (joystickWrapper.gamepad1GetX()) {
            slidePos = 220;
            if (guideServo.getPosition() > 0.06) {
                guideServo.setPosition(0.05);
            }
        }
        else if (joystickWrapper.gamepad1GetY()) {
            slidePos = 390;
            if (guideServo.getPosition() > 0.06) {
                guideServo.setPosition(0.05);
            }
        }else if (joystickWrapper.gamepad1GetB()) {
            slidePos = 550;
            if (guideServo.getPosition() > 0.06) {
                guideServo.setPosition(0.05);
            }
        }
        if (joystickWrapper.gamepad1GetDDown()) {
            slidePos = 5;
            if (guideServo.getPosition() > 0.06) {
                guideServo.setPosition(0.05);
            }
        }else if (joystickWrapper.gamepad1GetDLeft()) {
            slidePos = 1670;
            if (guideServo.getPosition() > 0.06) {
                guideServo.setPosition(0.05);
            }
        }
        else if (joystickWrapper.gamepad1GetDUp()) {
            slidePos = 2800;
            if (guideServo.getPosition() < 0.65) {
                guideServo.setPosition(.7);
            }
        }else if (joystickWrapper.gamepad1GetDRight()) {
            slidePos = 4000;
            if (guideServo.getPosition() < 0.65) {
                guideServo.setPosition(.7);
            }
        }


        if (slidePos<5 && limit) {
            slidePos = 5;
        }
        if (slidePos>4000 && limit) {
            slidePos = 4000;
        }

        telemetry.addData("limit mode (true means limit IS there", limit);
        telemetry.update();

//        telemetry.addData("CurrentPosition:slide", slideMotorRight.getCurrentPosition());
//        telemetry.addData("TargetPosition:slide", slideMotorRight.getTargetPosition());
//        telemetry.addData("CurrentPosition:rightServo", rightServo.getPosition());
//        telemetry.addData("CurrentPosition:leftServo", leftServo.getPosition());
//        telemetry.addData("TargetPosition", slidePos);
//        telemetr``y.addData("Limit?", limit);
//        telemetry.update();

        if (slideMotorRight.getTargetPosition() == 4000) {
            if (slideMotorRight.getCurrentPosition()> 4000-slowRange) {
                slideMotorRight.setPower(.25);
                slideMotorLeft.setPower(.25);
            } else {
                slideMotorRight.setPower(1);
                slideMotorLeft.setPower(1);
            }
        } else if (slideMotorRight.getTargetPosition() == 2900) {
            if (slideMotorRight.getCurrentPosition()>2900-slowRange && slideMotorRight.getCurrentPosition() < 2900+slowRange) {
                slideMotorRight.setPower(.25);
                slideMotorLeft.setPower(.25);
            } else {
                slideMotorRight.setPower(1);
                slideMotorLeft.setPower(1);
            }
        } else if (slideMotorRight.getTargetPosition() == 1670) {
            if (slideMotorRight.getCurrentPosition()> 1670-slowRange && slideMotorRight.getCurrentPosition() < 1670+slowRange) {
                slideMotorRight.setPower(.25);
                slideMotorLeft.setPower(.25);
            } else {
                slideMotorRight.setPower(1);
                slideMotorLeft.setPower(1);
            }
        } else {
            slideMotorRight.setPower(1);
            slideMotorLeft.setPower(1);
        }
        //slideMotorRight.setPower(1); //COMMENT THIS OUT IF WE ADD THE LOOPS PRIOR
        //slideMotorLeft.setPower(1);

        if (slidePos != null) {
            slideMotorRight.setTargetPosition(slidePos);
            slideMotorLeft.setTargetPosition((int) ((slidePos)*leftSlideFactor));
            slideMotorRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            slideMotorLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        }
        //topMotor.setPower(-joystickWrapper.gamepad2GetLeftStickY());
        telemetry.addData("Current Encoder", slideMotorRight.getCurrentPosition());
        telemetry.update();
        if(joystickWrapper.gamepad1GetLeftBumperDown()) {
            if (open) {
                rightServo.setPosition(.80);
                leftServo.setPosition(.20);
                open = false;
            } else {
                rightServo.setPosition(.9);
                leftServo.setPosition(0.1);
                open = true;
            }
        }

    }


    public void UpdatePos() {
        slideMotorRight.setPower(1);
        slideMotorLeft.setPower(1);

        slideMotorRight.setTargetPosition(slidePos);
        slideMotorLeft.setTargetPosition(slidePos);

        slideMotorRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        slideMotorLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    /*public double GetCurrentRotation(){
        double n = (360*(bottomMotor.getCurrentPosition()/(MotorTicks*Ratio)))%360;
        if(n>180){
            return n-360;
        }else return n;
    }*/

    /*public void RotateArm(double angle){
        if(0>angle - GetCurrentRotation()){
            bottomMotor.setPower(-1 * 0.25);
            telemetry.addData("Input", "Negative");
        }else if (0<angle - GetCurrentRotation()){
            bottomMotor.setPower(1 * 0.25);
            telemetry.addData("Input", "Positive");
        }
        telemetry.update();
    }*/

}





/*
init 192.168.43.1:5555 at ip ADB
push REABD library -- =: 17
POP REABD library -- =: 17(grade.strip().split(1::2))
 */