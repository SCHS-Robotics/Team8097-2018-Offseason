package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.BaseOpMode.OpModeType.TELEOP;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOpMode", group="Linear Opmode")
public class TeleOp extends BaseOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        type = TELEOP;

        initialize();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        runtime.reset();

        tts.getTts().setLanguage(tts.langToLocale(tts.lang));
        tts.speak(tts.welcomeText());

        while (opModeIsActive()) {

            // Telemetry
            telemetry.update();

            if(Math.abs(gamepad1.left_stick_y) > 0) {
                drive.curveDrive(-gamepad1.left_stick_y, gamepad1.left_trigger, gamepad1.right_trigger);
                telemetry.addLine("Curve driving");
                telemetry.addData("Drive Speed", drive.speed(gamepad1.left_stick_y));
                telemetry.addData("Stick Input: ", gamepad1.left_stick_y);
                telemetry.addData("Left Trigger", gamepad1.left_trigger);
                telemetry.addData("Right Trigger", gamepad1.right_trigger);
            }

            else if (gamepad1.left_trigger > .1){
                drive.turnLeft(gamepad1.left_trigger);
            }

            else if (gamepad1.right_trigger > .1){
                drive.turnRight(gamepad1.right_trigger);
            }

            else {
                drive.stopAll();
            }

        }
    }

}
