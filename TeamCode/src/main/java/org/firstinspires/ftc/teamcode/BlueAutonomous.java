package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Autonomous.Team.BLUE;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Blue Autonomous", group ="Autonomous")
public class BlueAutonomous extends Autonomous {

    @Override
    void setAutoVars() {
        team = BLUE;
    }
}
