package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

public class Position extends Module{

    private final float COLLISION_CONST = 2f;
    private BNO055IMU imu;
    private Orientation angles;
    private Orientation lastAngles = new Orientation();
    private float angle;
    private double lastAccel;

    Position (BNO055IMU imu, RobotLog debugLogger) {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";

        this.debugLogger = debugLogger;
        this.moduleName = "IMU";
        this.imu = imu;
        this.imu.initialize(parameters);
        imu.startAccelerationIntegration(new org.firstinspires.ftc.robotcore.external.navigation.Position(), new Velocity(), 500);


        if (this.debugLogger.loggingEnabled) {
            this.debugLogger.addDbgMessage(
                    RobotLog.DbgLevel.INFO,
                    moduleName,
                    "Initialized"
            );
        }
    }

    Position () {
        this.enabled = false;
    }

    float getHeading() {
        if (enabled) {
            angles = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            float angleDiff = angles.firstAngle - lastAngles.firstAngle;

            if (angleDiff < -180) angleDiff += 360;
            else if (angleDiff > 180) angleDiff -= 360;

            angle += angleDiff;

            lastAngles = angles;

            return angle;
        } else {
            return 0;
        }
    }

    double[] getVelocity() {
        double[] velocityValues = new double[] {
                this.imu.getVelocity().xVeloc,
                this.imu.getVelocity().yVeloc,
                this.imu.getVelocity().zVeloc
        };

        return velocityValues;
    }

    double[] getAcceleration() {
        double[] accelerationValues = new double[] {
                this.imu.getAcceleration().xAccel,
                this.imu.getAcceleration().yAccel,
                this.imu.getAcceleration().zAccel
        };

        return accelerationValues;
    }

    double getSpeed() {
        if (enabled) {
            double x = getVelocity()[0];
            double y = getVelocity()[1];

            double magnitude = Math.sqrt((x * x) + (y * y));

            return magnitude;
        } else {
            return 0;
        }
    }

    double getSpeedDerivative() {
        if (enabled) {
            double x = getAcceleration()[0];
            double y = getAcceleration()[1];

            double speedDeriv = Math.sqrt((x * x) + (y * y));

            if (this.debugLogger.loggingEnabled && speedDeriv != lastAccel && speedDeriv - lastAccel > COLLISION_CONST) {
                this.debugLogger.addDbgMessage(
                        RobotLog.DbgLevel.WARN,
                        moduleName,
                        "Acceleration spiked in excess of " + COLLISION_CONST + " m / s^2, possible collision."
                );
            }
            lastAccel = speedDeriv;
            return speedDeriv;
        } else {
            return 0;
        }
    }

    void stopTracking() {
        if (enabled) {
            imu.close();
        }
    }
}
