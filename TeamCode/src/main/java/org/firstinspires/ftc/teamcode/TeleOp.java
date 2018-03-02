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

        while (opModeIsActive()) {

            // Telemetry
            telemetry.update();
            telemetry.addData("Status", "Run Time: " + runtime.toString());

            // Controls checking
            if(Math.abs(gamepad1.left_stick_y) >= 0.05) {
                drive.curveDrive(gamepad1.left_stick_y, gamepad1.left_trigger, gamepad1.right_trigger);
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
