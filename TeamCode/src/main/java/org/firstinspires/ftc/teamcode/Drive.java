package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;

// Simple Drive system for the two stupid FRC motors, should be adaptable for a non-mecanum/omni drive of any sort though
public class Drive {

    private ArrayList<DcMotor> leftMotors;
    private ArrayList<DcMotor> rightMotors;
    private ArrayList<DcMotor> allMotors;

    Drive(ArrayList<DcMotor> leftMotors, ArrayList<DcMotor> rightMotors) {
        this.leftMotors = leftMotors;
        this.rightMotors = rightMotors;
        allMotors = new ArrayList<>();
        allMotors.addAll(leftMotors);
        allMotors.addAll(rightMotors);

        for (DcMotor motor : allMotors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }

        resetEncoders(allMotors);
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

    double currentMeanSpeed() {
        return ((rightMotors.get(0).getPower() + leftMotors.get(0).getPower()) / 2) * 10;
    }
}
