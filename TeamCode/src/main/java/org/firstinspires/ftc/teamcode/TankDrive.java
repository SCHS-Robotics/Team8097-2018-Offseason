package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.ArrayList;

public class TankDrive extends Drive {

    TankDrive(ArrayList<DcMotor> leftMotors, ArrayList<DcMotor> rightMotors, RobotLog debugLogger) {

        this.debugLogger = debugLogger;
        this.leftMotors = leftMotors;
        this.rightMotors = rightMotors;
        this.moduleName = "Tank Drive";
        this.enabled = true;

        allMotors = new ArrayList<>();

        allMotors.addAll(leftMotors);
        allMotors.addAll(rightMotors);

        for (DcMotor motor : allMotors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }

        setDirection(leftMotors, DcMotorSimple.Direction.REVERSE);

        resetEncoders(allMotors);

        if (this.debugLogger.loggingEnabled) {
            this.debugLogger.addDbgMessage(
                    RobotLog.DbgLevel.INFO,
                    moduleName,
                    "Initialized"
            );
        }

    }
}
