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

    RobotLog speedData;
    RobotLog robotDebug;

    private ArrayList<RobotLog> loggers;
    private ArrayList<Module> modules;

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

    //----------------------------------------------------------------------------------------------
    // Common Functions
    //----------------------------------------------------------------------------------------------

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

            boolean motorMissing = false;
            for (int i = 0; i < leftMotors.size(); i++) {
                if (leftMotors.get(i) == null) {
                    if (loggingEnabled) {
                        robotDebug.addDbgMessage(
                                RobotLog.DbgLevel.ERROR,
                                drive.moduleName,
                                "Left motor of index " + i + " not found!"
                        );
                    }
                    motorMissing = true;
                    // Kills the drive train if a motor is not found
                    position.disableModule();
                }
            }
            for (int i = 0; i < rightMotors.size(); i++) {
                if (rightMotors.get(i) == null) {
                    if (loggingEnabled) {
                        robotDebug.addDbgMessage(
                                RobotLog.DbgLevel.ERROR,
                                drive.moduleName,
                                "Right motor of index " + i + " not found!"
                        );
                    }
                    motorMissing = true;
                    position.disableModule();
                }
            }
            if (!motorMissing) {
                modules.add(position);
            }
        } else {
            drive = new Drive();
            if (loggingEnabled) {
                robotDebug.addDbgMessage(
                        RobotLog.DbgLevel.INFO,
                        drive.moduleName,
                        "Disabled"
                );
            }
        }

        if (loggingEnabled) {
            loggers = new ArrayList<>();
            loggers.add(speedData);
            loggers.add(robotDebug);
        }

        if (imuEnabled) {
            revImu = hardwareMap.get(BNO055IMU.class, "imu");
            position = new Position(revImu, robotDebug);
            if (revImu == null) {
                if (loggingEnabled) {
                    robotDebug.addDbgMessage(
                            RobotLog.DbgLevel.ERROR,
                            position.moduleName,
                            "Not Found"
                    );
                }
                position.disableModule();
            } else {
                modules.add(position);
            }
        } else {
            position = new Position();
            if (loggingEnabled) {
                robotDebug.addDbgMessage(
                        RobotLog.DbgLevel.INFO,
                        position.moduleName,
                        "Disabled"
                );
            }
        }

        if (ttsEnabled) {
            tts = new RobotTts(hardwareMap.appContext, runtime, robotDebug);
            modules.add(tts);
        } else {
            tts = new RobotTts();
            if (loggingEnabled) {
                robotDebug.addDbgMessage(
                        RobotLog.DbgLevel.INFO,
                        tts.moduleName,
                        "Disabled"
                );
            }
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
