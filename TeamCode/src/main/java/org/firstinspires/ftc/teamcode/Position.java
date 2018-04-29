package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Position {

    private BNO055IMU imu;
    private Orientation angles;
    private Orientation lastAngles = new Orientation();
    private float angle;
    private BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

    Position (BNO055IMU givenImu) {
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled      = false;

        imu = givenImu;
        imu.initialize(parameters);

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
}
