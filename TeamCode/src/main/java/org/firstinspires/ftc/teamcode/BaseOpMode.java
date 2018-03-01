package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

public abstract class BaseOpMode extends LinearOpMode {

    // Declare hardware variables here
    DcMotor motorLeft;
    DcMotor motorRight;
    Drive drive;
    ArrayList<DcMotor> leftMotors;
    ArrayList<DcMotor> rightMotors;

    ElapsedTime runtime;

    public void initialize() {

        runtime = new ElapsedTime();

        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");

        leftMotors.add(motorLeft);
        rightMotors.add(motorRight);

        drive = new Drive(leftMotors, rightMotors);

        switch (type) {
            case TELEOP:
                // Put any TeleOp-only hardware requirements here;
            case AUTONOMOUS:
                // Put any Autonomous-only hardware requirements here (Mostly sensors);
        }
    }

    enum OpModeType {
        AUTONOMOUS,
        TELEOP
    }

    OpModeType type;
}
