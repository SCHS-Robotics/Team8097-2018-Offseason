package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

public abstract class BaseOpMode extends LinearOpMode {

    //----------------------------------------------------------------------------------------------
    // Runtime Variables
    //----------------------------------------------------------------------------------------------

    // Logging
    boolean loggingEnabled; // This is set on a per-OpMode basis.

    RobotLog languageData;
    RobotLog robotDebug;

    private  ArrayList<RobotLog> loggers;

    // Drive / Motors
    boolean driveEnabled = true;

    Drive drive;

    DcMotor motorBackLeft;
    DcMotor motorBackRight;
    DcMotor motorFrontLeft;
    DcMotor motorFrontRight;

    private ArrayList<DcMotor> leftMotors;
    private ArrayList<DcMotor> rightMotors;

    // Servos / Auxiliary

    // Sensors
    boolean imuEnabled = true;

    Position position;

    BNO055IMU revImu;

    // Telemetry
    ElapsedTime runtime;

    // Etc.
    boolean ttsEnabled = true;

    RobotTts tts;

    void initialize() {

        if (driveEnabled) {
            motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
            motorBackRight = hardwareMap.dcMotor.get("motorBackRight");
            motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
            motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");


            leftMotors = new ArrayList<>();
            rightMotors = new ArrayList<>();

            leftMotors.add(motorBackLeft);
            rightMotors.add(motorBackRight);
            leftMotors.add(motorFrontLeft);
            rightMotors.add(motorFrontRight);

            drive = new TankDrive(leftMotors, rightMotors, robotDebug);
        }

        if (loggingEnabled) {
            loggers = new ArrayList<>();
            loggers.add(languageData);
            loggers.add(robotDebug);
        }

        if (imuEnabled) {
            revImu = hardwareMap.get(BNO055IMU.class, "imu");
            position = new Position(revImu, robotDebug);
        }

        if (ttsEnabled) {
            tts = new RobotTts(hardwareMap.appContext, runtime, robotDebug);
        }

        switch (type) {
            case TELEOP:
                // Put any TeleOp-only hardware requirements here;
            case AUTONOMOUS:
                // Put any Autonomous-only hardware requirements here (Mostly sensors);
        }
    }

    void shutdown() {
        if (loggingEnabled && loggers.size() > 0) {
            robotDebug.addDbgMessage(
                    RobotLog.DbgLevel.INFO,
                    "OpMode",
                    "Shutting Down"
            );
            for (RobotLog logger : loggers) {
                logger.closeLog();
            }
        }

        if (driveEnabled) {
            drive.stopAll();
        }

        if (ttsEnabled) {
            tts.stopSound();
            tts.stopTalking();
        }

        if (imuEnabled) {
            position.stopTracking();
        }
    }

    enum OpModeType {
        AUTONOMOUS,
        TELEOP
    }

    OpModeType type;
}
