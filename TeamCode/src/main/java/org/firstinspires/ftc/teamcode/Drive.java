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

        resetEncoders(allMotors);
    }

    void goForward(double targetPower) {
        for(DcMotor motor : leftMotors) {
            motor.setPower(speed(targetPower));
        }
        for (DcMotor motor : rightMotors) {
            motor.setPower(-speed(targetPower));
        }
    }

    void goBackward(double targetPower) {
        for(DcMotor motor : leftMotors) {
            motor.setPower(-speed(targetPower));
        }
        for (DcMotor motor : rightMotors) {
            motor.setPower(speed(targetPower));
        }
    }

    void curveDrive(double magnitude, double inputLeft, double inputRight) {
        double curve = inputLeft - inputRight;
        double leftPower, rightPower;
        double power = speed(magnitude);

        if (curve < 0.0)
        {
            double value = Math.log(-curve);
            double ratio = (value - 0.5)/(value + 0.5);
            leftPower = magnitude/ratio;
            rightPower = -magnitude;
        }
        else if (curve > 0.0)
        {
            double value = Math.log(curve);
            double ratio = (value - 0.5)/(value + 0.5);
            leftPower = magnitude;
            rightPower = -magnitude/ratio;
        }
        else
        {
            leftPower = power;
            rightPower = -power;
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
            motor.setPower(speed(targetPower));
        }
    }

    void turnRight(double targetPower) {
        for(DcMotor motor : allMotors) {
            motor.setPower(-speed(targetPower));
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

    private double speed(double target) {
        double power;

        if(target < 0) {
            if (currentMeanSpeed() > target) {
                power = currentMeanSpeed() - 0.05;
            }
            else {
                power = target;
            }
        }
        else{
            if (currentMeanSpeed() < target) {
                power = currentMeanSpeed() + 0.05;
            }
            else {
                power = target;
            }
        }

        return power;
    }

    private double currentMeanSpeed() {
        double leftTotal = 0;
        double rightTotal = 0;

        for(DcMotor motor: leftMotors) {
            leftTotal += motor.getPower();
        }
        for (DcMotor motor: rightMotors) {
            rightTotal += motor.getPower();
        }

        return (leftTotal + rightTotal) / (leftMotors.size() + rightMotors.size());
    }
}
