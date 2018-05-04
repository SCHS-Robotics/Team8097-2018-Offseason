package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;

public class Drive {

    final float SPEED_INCREMENT = 0.1f;
    final float SPEED_DECREMENT = 0.1f;
    final float SPEED_TOLERANCE = 0.05f;
    final float CURVE_SENSITIVITY = 0.5f;
    final float CORRECTION_FACTOR = 0.5f;

    final int ANGLE_TOLERANCE = 5;

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

    void curveDrive(double magnitude, double inputLeft, double inputRight, Position position) {
        double curve = inputRight - inputLeft;
        double leftPower, rightPower;
        float startingAngle = 0;
        boolean straightAngleSet = false;

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
            straightAngleSet = false;
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
            straightAngleSet = false;
        }
        else
        {
            if (!straightAngleSet) {
                startingAngle = position.getHeading();
                straightAngleSet = true;
            }
            double correction = pidCorrection(startingAngle, position);
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
            motor.setPower(targetPower);
        }
        for(DcMotor motor : rightMotors) {
            motor.setPower(-targetPower);
        }
    }

    void turnRight(double targetPower) {
        for(DcMotor motor : leftMotors) {
            motor.setPower(-targetPower);
        }
        for(DcMotor motor : rightMotors) {
            motor.setPower(targetPower);
        }
    }

    void turnRightFromCurrent(float angle, double targetPower, Position robotPosition) {
        float turnAngle = robotPosition.getHeading() - Math.abs(angle);
        turnTo(turnAngle, targetPower, robotPosition);
    }

    void turnLeftFromCurrent(float angle, double targetPower, Position robotPosition) {
        float turnAngle = robotPosition.getHeading() + Math.abs(angle);
        turnTo(turnAngle, targetPower, robotPosition);
    }

    void turnTo(float angle, double power, Position robotPosition) {
        double targetPower = power;
        while (Math.abs(robotPosition.getHeading() - angle) > ANGLE_TOLERANCE) {
            if (Math.abs(robotPosition.getHeading() - angle) < 40){
                if (Math.abs(robotPosition.getHeading() - angle) < 20) {
                    power = 0.1;
                }
                else {
                    power = 0.2;
                }
            }
            else {
                power = targetPower;
            }

            if (robotPosition.getHeading() > angle) {
                turnRight(power);
            } else {
                turnLeft(power);
            }
        }
        stopAll();
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
            if (currentMeanSpeed() > 0.1) {
                power = currentMeanSpeed() - SPEED_DECREMENT;
            } else if (currentMeanSpeed() < -.1) {
                power = currentMeanSpeed() + SPEED_DECREMENT;
            } else {
                power = 0;
            }
        }

        return power;
    }

    double pidCorrection(float startingAngle, Position position) {
        float angleDiff = position.getHeading() - startingAngle;
        float correction;

        if (Math.abs(angleDiff) > 0) {
            correction = angleDiff;
        } else {
            correction = 0;
        }

        correction = correction * CORRECTION_FACTOR;

        return correction;
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
