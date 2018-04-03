package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;

// Simple Drive system for the two stupid FRC motors, should be adaptable for a non-mecanum/omni drive of any sort though
public class Drive {

    ArrayList<DcMotor> allMotors;
    ArrayList<DcMotor> leftMotors;
    ArrayList<DcMotor> rightMotors;

    Drive () {

    }

    void goForward(double targetPower) {
        for(DcMotor motor : leftMotors) {
            motor.setPower(targetPower);
        }
        for (DcMotor motor : rightMotors) {
            motor.setPower(-targetPower);
        }
    }

    void goBackward(double targetPower) {
        for(DcMotor motor : leftMotors) {
            motor.setPower(-targetPower);
        }
        for (DcMotor motor : rightMotors) {
            motor.setPower(targetPower);
        }
    }

    void curveDrive(double magnitude, double inputLeft, double inputRight) {
        double curve = inputLeft - inputRight;
        double leftPower, rightPower;

        if (curve < 0.0)
        {
            double value = Math.log(-curve);
            double ratio = (value - 0.5)/(value + 0.5);
            if (ratio == 0.0)
            {
                ratio = 0.0000000001;
            }
            leftPower = magnitude/ratio;
            rightPower = -magnitude;
        }
        else if (curve > 0.0)
        {
            double value = Math.log(curve);
            double ratio = (value - 0.5)/(value + 0.5);
            if (ratio == 0.0)
            {
                ratio = 0.0000000001;
            }
            leftPower = magnitude;
            rightPower = -magnitude/ratio;
        }
        else
        {
            leftPower = magnitude;
            rightPower = -magnitude;
        }

        for(DcMotor motor : leftMotors) {
            motor.setPower(leftPower);
        }
        for (DcMotor motor : rightMotors) {
            motor.setPower(rightPower);
        }

    }

    void turnLeft(double targetPower) {
        for(DcMotor motor : allMotors) {
            motor.setPower(targetPower);
        }
    }

    void turnRight(double targetPower) {
        for(DcMotor motor : allMotors) {
            motor.setPower(-targetPower);
        }
    }

    void resetEncoders(ArrayList<DcMotor> motors) {
        for(DcMotor motor : motors) {
            if(motor.getCurrentPosition() != 0){
                motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    void setMode(ArrayList<DcMotor> motors, DcMotor.RunMode mode) {
        for(DcMotor motor : motors) {
            motor.setMode(mode);
        }
    }

    void stopMotors(ArrayList<DcMotor> motors) {
        for(DcMotor motor : motors) {
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    void stopAll() {
        for(DcMotor motor : allMotors) {
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}
