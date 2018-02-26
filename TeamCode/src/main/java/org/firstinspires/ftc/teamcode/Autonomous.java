package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Autonomous.Team.BLUE;
import static org.firstinspires.ftc.teamcode.Autonomous.Team.RED;
import static org.firstinspires.ftc.teamcode.BaseOpMode.OpModeType.AUTONOMOUS;

public abstract class Autonomous extends BaseOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        type = AUTONOMOUS;
        setAutoVars();

        initialize();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while(opModeIsActive()) {
            // Things to do in autonomous
        }
    }

    // Neat auto functions to go in that while loop right over there

    abstract void setAutoVars();

    enum Team {
        RED,
        BLUE
    }

    Team team;
}
