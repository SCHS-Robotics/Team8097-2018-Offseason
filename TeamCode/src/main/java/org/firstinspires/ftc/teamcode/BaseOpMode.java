package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

public abstract class BaseOpMode extends LinearOpMode {

    // Declare hardware variables here

    // Modules Declaration
    Drive drive;
    Position position;
    RobotTts tts;

    // Drive / Motors

    DcMotor motorBackLeft;
    DcMotor motorBackRight;
    DcMotor motorFrontLeft;
    DcMotor motorFrontRight;

    private ArrayList<DcMotor> leftMotors;
    private ArrayList<DcMotor> rightMotors;

    // Servos

    // Sensors
    BNO055IMU revImu;

    // Telemetry
    ElapsedTime runtime;

    // Etc.

    void initialize() {

        runtime = new ElapsedTime();

        motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
        motorBackRight = hardwareMap.dcMotor.get("motorBackRight");
        motorFrontLeft= hardwareMap.dcMotor.get("motorFrontLeft");
        motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");

        leftMotors = new ArrayList<>();
        rightMotors = new ArrayList<>();

        leftMotors.add(motorBackLeft);
        rightMotors.add(motorBackRight);
        leftMotors.add(motorFrontLeft);
        rightMotors.add(motorFrontRight);

        revImu = hardwareMap.get(BNO055IMU.class, "imu");

        drive = new TankDrive(leftMotors, rightMotors);
        position = new Position(revImu);
        tts = new RobotTts(hardwareMap.appContext);

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
