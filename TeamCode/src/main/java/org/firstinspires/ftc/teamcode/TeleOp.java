package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.teamcode.BaseOpMode.OpModeType.TELEOP;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOpMode", group="Linear Opmode")
public class TeleOp extends BaseOpMode {

    private ElapsedTime cooldown;
    private boolean outreachMode = false;
    private boolean manualSet = false;
    private int langIndex = 0;
    private double buttonACooldown, buttonYCooldown, buttonXCooldown, buttonBCooldown, buttonLBCooldown, buttonRBCooldown;

    @Override
    public void runOpMode() {

        runtime = new ElapsedTime();
        cooldown = new ElapsedTime();

        loggingEnabled = true;
        type = TELEOP;

        accelData = new RobotLog(runtime, "Acceleration", RobotLog.LogType.DATA, loggingEnabled);
        accelData.openLog();
        accelData.startCollection();
        robotDebug = new RobotLog(runtime, "TeleOp", RobotLog.LogType.DEBUG, loggingEnabled);
        robotDebug.openLog();

        initialize();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        tts.lang = tts.randomLang();

        boolean lastInput = false, lastLeft = false, lastRight = false;

        //------------------------------------------------------------------------------------------
        // Pre-run Menu
        //------------------------------------------------------------------------------------------

        while (!opModeIsActive()) {
            telemetry.addLine("Press A to toggle Outreach Mode");
            telemetry.addLine("Use Dpad to select Language");
            if ((gamepad1.a || gamepad2.a) && !lastInput) {
                outreachMode = !outreachMode;
            }

            if ((gamepad1.dpad_right || gamepad1.dpad_right) && !lastRight) {
                manualSet = true;
                langIndex++;
                langIndex = (langIndex > tts.languages.length - 1) ? 0 : langIndex;
                telemetry.update();
            }
            else if ((gamepad1.dpad_left || gamepad2.dpad_left) && !lastLeft) {
                manualSet = true;
                langIndex--;
                langIndex = (langIndex < 0) ? 4 : langIndex;
                telemetry.update();
            }

            if (manualSet) {
                tts.lang = tts.languages[langIndex];
            }

            telemetry.addData("Language: ", tts.lang);
            telemetry.addData("Outreach Mode: ", outreachMode);
            telemetry.addData("Modules Loaded: ", modules);
            telemetry.addData("Loggers Running: ", loggers);
            telemetry.update();

            lastInput = gamepad1.a || gamepad2.a;
            lastLeft = gamepad1.dpad_left || gamepad2.dpad_left;
            lastRight = gamepad1.dpad_right || gamepad2.dpad_right;
        }

        waitForStart();

        if (loggingEnabled) {
            robotDebug.addDbgMessage(
                    RobotLog.DbgLevel.INFO,
                    "OpMode",
                    "---------- Starting TeleOp ----------"
            );
            if (outreachMode) {
                robotDebug.addDbgMessage(
                        RobotLog.DbgLevel.INFO,
                        "OpMode",
                        "OUTREACH MODE ON"
                );
            }
        }

        runtime.reset();

        tts.setLanguage();
        tts.speak(tts.welcomeText());

        while (opModeIsActive()) {

            // Telemetry
            buildTelemetry();

            logData();

            if (Math.abs(gamepad1.left_stick_y) > 0.1) {
                double speed = outreachMode ? gamepad1.left_stick_y / 2 : gamepad1.left_stick_y;
                drive.curveDrive(speed, gamepad1.left_trigger, gamepad1.right_trigger, position);
            }
            else if (gamepad1.left_trigger > .1){
                double speed = outreachMode ? gamepad1.left_trigger / 2 : gamepad1.left_trigger;
                drive.turnLeft(speed);
            }
            else if (gamepad1.right_trigger > .1){
                double speed = outreachMode ? gamepad1.right_trigger / 2 : gamepad1.right_trigger;
                drive.turnRight(speed);
            }
            else if ((gamepad1.left_bumper) && Math.abs(cooldown.time() - buttonLBCooldown) >= .2) {
                drive.turnLeftFromCurrent(90, 0.5, position);
                buttonLBCooldown = cooldown.time();
            }
            else if ((gamepad1.right_bumper) && Math.abs(cooldown.time() - buttonRBCooldown) >= .2) {
                drive.turnRightFromCurrent(90, 0.5, position);
                buttonRBCooldown = cooldown.time();
            }

            else {
                drive.stopAll();
            }

            if ((gamepad1.a || gamepad2.a) && Math.abs(cooldown.time() - buttonACooldown) >= .2) {
                tts.speak(tts.getRandomLine());
                buttonACooldown = cooldown.time();
            }

            if ((gamepad1.b || gamepad2.b) && Math.abs(cooldown.time() - buttonACooldown) >= .2) {
                tts.speak(tts.randomLines()[1]);
                buttonBCooldown = cooldown.time();
            }

            if ((gamepad1.y || gamepad2.y) && Math.abs(cooldown.time() - buttonYCooldown) >= .2) {
                tts.lang = tts.randomLang();
                tts.setLanguage();
                buttonACooldown = cooldown.time();
            }

            if (gamepad1.x && Math.abs(cooldown.time() - buttonXCooldown) >= .2) {
                tts.readySound(tts.LITTLE_BOXES);
                tts.playSound();
                buttonXCooldown = cooldown.time();
            }

            if (isStopRequested()) {
                if (loggingEnabled) {
                    robotDebug.addDbgMessage(
                            RobotLog.DbgLevel.INFO,
                            "OpMode",
                            "---------- Stopping TeleOp ----------"
                    );
                }
                shutdown();
            }
        }
    }

    private void buildTelemetry() {
        if (outreachMode) telemetry.addLine("Outreach Mode Enabled");
        telemetry.addData("Language: ", tts.lang);
        telemetry.addData("Heading: ", position.getHeading());
        telemetry.addData("Drive enabled: ", drive.enabled);
        telemetry.addData("Position enabled: ", position.enabled);

        telemetry.update();
    }

    private void logData() {
        accelData.addData(.5f, position.getSpeedDerivative());
    }

}
