package org.firstinspires.ftc.teamcode;

public class Module {

    protected RobotLog debugLogger;
    protected boolean enabled = true;
    protected String moduleName;

    // This could be a useful crash handler, maybe.

    void disableModule() {
        this.enabled = false;
        if (this.debugLogger.loggingEnabled) {
            this.debugLogger.addDbgMessage(
                    RobotLog.DbgLevel.WARN,
                    moduleName,
                    "Shutting Down"
            );
        }
    }
}
