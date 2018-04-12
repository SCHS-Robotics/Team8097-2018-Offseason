package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.ArrayList;

public class TankDrive extends Drive {

    TankDrive(ArrayList<DcMotor> leftMotors, ArrayList<DcMotor> rightMotors) {
        this.leftMotors = leftMotors;
        this.rightMotors = rightMotors;

        allMotors = new ArrayList<>();

        allMotors.addAll(leftMotors);
        allMotors.addAll(rightMotors);

        for (DcMotor motor : allMotors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }

        setDirection(rightMotors, DcMotorSimple.Direction.REVERSE);

        resetEncoders(allMotors);
    }
}
