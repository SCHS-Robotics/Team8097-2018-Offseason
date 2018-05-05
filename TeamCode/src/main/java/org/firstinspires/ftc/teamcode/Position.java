package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Position {

    private BNO055IMU imu;
    private Orientation angles;
    private Orientation lastAngles = new Orientation();
    private float angle;
    private RobotLog debugLogger;

    Position (BNO055IMU imu, RobotLog debugLogger) {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled      = false;

        this.debugLogger = debugLogger;
        this.imu = imu;
        this.imu.initialize(parameters);

        if (this.debugLogger.loggingEnabled) {
            this.debugLogger.addDbgMessage(
                    RobotLog.DbgLevel.INFO,
                    "IMU",
                    "Initialized"
            );
        }
    }

    float getHeading() {
        angles = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        float angleDiff = angles.firstAngle - lastAngles.firstAngle;

        if (angleDiff < -180) angleDiff += 360;
        else if (angleDiff > 180) angleDiff -= 360;

        angle += angleDiff;

        lastAngles = angles;

        return angle;
    }

    void stopTracking() {
        imu.close();
    }
}
