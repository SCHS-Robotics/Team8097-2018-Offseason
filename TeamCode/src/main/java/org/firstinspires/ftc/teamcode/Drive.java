package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.ArrayList;

public class Drive {

    final float SPEED_INCREMENT = 0.05f;
    final float SPEED_DECREMENT = 0.015f;
    final float SPEED_TOLERANCE = 0.05f;
    final float CURVE_SENSITIVITY = 0.5f;

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
        double curve = inputRight - inputLeft;
        double leftPower, rightPower;

        if (curve < 0.0)
        {
            double value = Math.log(-curve);
            double ratio = (value - CURVE_SENSITIVITY)/(value + CURVE_SENSITIVITY);
            if (ratio == 0.0)
            {
                ratio = 0.0000000001;
            }
            leftPower = magnitude/ratio;
            rightPower = magnitude;
        }
        else if (curve > 0.0)
        {
            double value = Math.log(curve);
            double ratio = (value - CURVE_SENSITIVITY)/(value + CURVE_SENSITIVITY);
            if (ratio == 0.0)
            {
                ratio = 0.0000000001;
            }
            leftPower = magnitude;
            rightPower = magnitude/ratio;
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
            motor.setPower(-targetPower);
        }
        for(DcMotor motor : rightMotors) {
            motor.setPower(targetPower);
        }
    }

    void turnRight(double targetPower) {
        for(DcMotor motor : leftMotors) {
            motor.setPower(targetPower);
        }
        for(DcMotor motor : rightMotors) {
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
            if (currentMeanSpeed() > target + SPEED_TOLERANCE) {
                power = currentMeanSpeed() - SPEED_INCREMENT;
            } else if (currentMeanSpeed() < target - SPEED_TOLERANCE){
                power = currentMeanSpeed() + SPEED_DECREMENT;
            } else {
                power = target;
            }
        }

        else if (target > 0) {
            if (currentMeanSpeed() < target - SPEED_TOLERANCE) {
                power = currentMeanSpeed() + SPEED_INCREMENT;
            }
            else if (currentMeanSpeed() > target + SPEED_TOLERANCE){
                power = currentMeanSpeed() - SPEED_DECREMENT;
            } else {
                power = target;
            }
        }

        else {
            if (currentMeanSpeed() > 0.2) {
                power = currentMeanSpeed() - SPEED_DECREMENT;
            } else if (currentMeanSpeed() < -.2) {
                power = currentMeanSpeed() + SPEED_DECREMENT;
            } else {
                power = 0;
            }
        }

        return power;
    }

    double currentMeanSpeed() {
        double leftTotal = 0;
        double rightTotal = 0;
        double average;
        int totalMotors;

        for (DcMotor motor : leftMotors) leftTotal += motor.getPower();
        for (DcMotor motor : rightMotors) rightTotal += motor.getPower();

        totalMotors = leftMotors.size() + rightMotors.size();

        average = (leftTotal + rightTotal) / totalMotors;

        return average;
    }
}
