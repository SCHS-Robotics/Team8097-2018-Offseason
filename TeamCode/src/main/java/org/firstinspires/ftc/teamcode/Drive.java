package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.ArrayList;

// Simple Drive system for the two stupid FRC motors, should be adaptable for a non-mecanum/omni drive of any sort though
public class Drive {

    final float SPEED_INCREMENT = 0.05f;

    ArrayList<DcMotor> allMotors;
    ArrayList<DcMotor> leftMotors;
    ArrayList<DcMotor> rightMotors;

    Drive () {

    }

    void goForward(double targetPower) {
        for(DcMotor motor : allMotors) {
            motor.setPower(speed(targetPower));
        }
    }

    void goBackward(double targetPower) {
        for(DcMotor motor : allMotors) {
            motor.setPower(-speed(targetPower));
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
            leftPower = speed(magnitude)/ratio;
            rightPower = speed(magnitude);
        }
        else if (curve > 0.0)
        {
            double value = Math.log(curve);
            double ratio = (value - 0.5)/(value + 0.5);
            if (ratio == 0.0)
            {
                ratio = 0.0000000001;
            }
            leftPower = speed(magnitude);
            rightPower = speed(magnitude)/ratio;
        }
        else
        {
            leftPower = speed(magnitude);
            rightPower = speed(magnitude);
        }

        for(DcMotor motor : leftMotors) {
            motor.setPower(leftPower);
        }
        for (DcMotor motor : rightMotors) {
            motor.setPower(rightPower);
        }

    }

    void turnLeft(double targetPower) {
        for(DcMotor motor : leftMotors) {
            motor.setPower(-speed(targetPower));
        }
        for(DcMotor motor : rightMotors) {
            motor.setPower(speed(targetPower));
        }
    }

    void turnRight(double targetPower) {
        for(DcMotor motor : leftMotors) {
            motor.setPower(speed(targetPower));
        }
        for(DcMotor motor : rightMotors) {
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

    void setMode(ArrayList<DcMotor> motors, DcMotor.RunMode mode) {
        for(DcMotor motor : motors) motor.setMode(mode);
    }

    void setPower(ArrayList<DcMotor> motors, double power) {
        for (DcMotor motor : motors) motor.setPower(power);
    }

    void setTarget(ArrayList<DcMotor> motors, int targetPosition) {
        for (DcMotor motor : motors) motor.setTargetPosition(targetPosition);
    }

    void setDirection(ArrayList<DcMotor> motors, DcMotor.Direction direction) {
        for (DcMotor motor : motors) motor.setDirection(direction);
    }

    void stopMotors(ArrayList<DcMotor> motors) {
        for(DcMotor motor : motors) {
            motor.setPower(speed(0));
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    void stopAll() {
        for(DcMotor motor : allMotors) {
            motor.setPower(speed(0));
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    double speed(double target) {
        double power;

        if(target < 0) {
            if (currentMeanSpeed() > target) {
                power = currentMeanSpeed() - SPEED_INCREMENT;
            } else if (currentMeanSpeed() < target){
                power = currentMeanSpeed() + SPEED_INCREMENT;
            } else {
                power = target;
            }
        }

        else {
            if (currentMeanSpeed() < target) {
                power = currentMeanSpeed() + SPEED_INCREMENT;
            }
            else if (currentMeanSpeed() > target){
                power = currentMeanSpeed() - SPEED_INCREMENT;
            } else {
                power = target;
            }
        }

        return power;
    }

    double currentMeanSpeed() {
        double leftTotal = 0;
        double rightTotal = 0;

        for (DcMotor motor : leftMotors) leftTotal += motor.getPower();
        for (DcMotor motor : rightMotors) rightTotal += motor.getPower();

        return currentMeanSpeed();
    }
}
