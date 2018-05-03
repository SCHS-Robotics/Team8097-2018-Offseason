package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.ArrayList;

public class MecanumDrive extends Drive{

    private DcMotor motorFL, motorFR, motorBL, motorBR;

    final int TICKS_PER_INCH = 100;

    MecanumDrive(DcMotor motorFrontLeft, DcMotor motorFrontRight, DcMotor motorBackLeft, DcMotor motorBackRight) {
        this.motorFL = motorFrontLeft;
        this.motorFR = motorFrontRight;
        this.motorBL = motorBackLeft;
        this.motorBR = motorBackRight;

        leftMotors = new ArrayList<>();
        rightMotors = new ArrayList<>();
        allMotors = new ArrayList<>();

        leftMotors.add(motorFL);
        leftMotors.add(motorBL);

        rightMotors.add(motorFR);
        rightMotors.add(motorBR);

        allMotors.addAll(leftMotors);
        allMotors.addAll(rightMotors);

        for (DcMotor motor : allMotors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        setDirection(leftMotors, DcMotorSimple.Direction.REVERSE);

        resetEncoders(allMotors);
    }

    void strafeLeft(double power) {
        motorBL.setPower(speed(power));
        motorBR.setPower(-speed(power));
        motorFL.setPower(-speed(power));
        motorFR.setPower(speed(power));
    }

    void strafeRight(double power) {
        motorBL.setPower(-speed(power));
        motorBR.setPower(speed(power));
        motorFL.setPower(speed(power));
        motorFR.setPower(-speed(power));
    }

    void strafeForwardRight(double power) {
        motorBL.setPower(0);
        motorBR.setPower(speed(power));
        motorFL.setPower(speed(power));
        motorFR.setPower(0);
    }

    void strafeForwardLeft(double power) {
        motorBL.setPower(speed(power));
        motorBR.setPower(0);
        motorFL.setPower(0);
        motorFR.setPower(speed(power));
    }

    void strafeBackwardRight(double targetPower) {

        motorBL.setPower(-speed(targetPower));
        motorBR.setPower(0);
        motorFL.setPower(0);
        motorFR.setPower(-speed(targetPower));
    }

    void strafeBackwardLeft(double targetPower) {
        motorBL.setPower(0);
        motorBR.setPower(-speed(targetPower));
        motorFL.setPower(-speed(targetPower));
        motorFR.setPower(0);
    }


    // Autonomous Functions

    void goForwardDistance(int distance, double targetPower) throws InterruptedException{
        int targetPosition = distance * TICKS_PER_INCH;
        resetEncoders(allMotors);

        setTarget(allMotors, targetPosition);
        setMode(allMotors, DcMotor.RunMode.RUN_TO_POSITION);
        setPower(allMotors, targetPower);

        while (motorBL.isBusy() && motorFR.isBusy() && motorBR.isBusy() && motorFL.isBusy()) {}

        stopMotors(allMotors);
    }

    void goBackwardDistance(int distance, double targetPower) throws InterruptedException{
        int targetPosition = -distance * TICKS_PER_INCH;
        resetEncoders(allMotors);

        setTarget(allMotors, targetPosition);
        setMode(allMotors, DcMotor.RunMode.RUN_TO_POSITION);
        setPower(allMotors, speed(targetPower));

        while (motorBL.isBusy() && motorFR.isBusy() && motorBR.isBusy() && motorFL.isBusy()) {}

        stopMotors(allMotors);
    }
}
