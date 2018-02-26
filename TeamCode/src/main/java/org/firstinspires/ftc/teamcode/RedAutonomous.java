package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Autonomous.Team.RED;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Red Autonomous", group ="Autonomous")
public class RedAutonomous extends Autonomous {

    @Override
    void setAutoVars() {
        team = RED;
    }
}
