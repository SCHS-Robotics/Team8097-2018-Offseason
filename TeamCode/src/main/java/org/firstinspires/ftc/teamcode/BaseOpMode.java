package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

public abstract class BaseOpMode extends LinearOpMode {

    // Declare hardware variables here
    DcMotor motorBackLeft;
    DcMotor motorBackRight;
    DcMotor motorFrontLeft;
    DcMotor motorFrontRight;

    Drive drive;

    private ArrayList<DcMotor> leftMotors;
    private ArrayList<DcMotor> rightMotors;

    ElapsedTime runtime;

    void initialize() {

        runtime = new ElapsedTime();

        motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
        motorBackRight = hardwareMap.dcMotor.get("motorBackRight");

        leftMotors = new ArrayList<>();
        rightMotors = new ArrayList<>();

        leftMotors.add(motorBackLeft);
        leftMotors.add(motorFrontLeft);
        rightMotors.add(motorBackRight);
        rightMotors.add(motorFrontRight);

        drive = new TankDrive(leftMotors, rightMotors);

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
