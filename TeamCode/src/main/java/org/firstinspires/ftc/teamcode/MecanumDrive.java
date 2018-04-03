package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import java.util.ArrayList;

public class MecanumDrive extends Drive{

    private DcMotor motorFL, motorFR, motorBL, motorBR;

    final double TICKS_PER_INCH = 100;

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

        resetEncoders(allMotors);
    }

    void strafeLeft(double power) {
        motorBL.setPower(-power);
        motorBR.setPower(-power);
        motorFL.setPower(power);
        motorFR.setPower(power);
    }

    void strafeRight(double power) {
        motorBL.setPower(power);
        motorBR.setPower(power);
        motorFL.setPower(-power);
        motorFR.setPower(-power);
    }

    void strafeForwardRight(double power) {
        motorBL.setPower(power);
        motorBR.setPower(0);
        motorFL.setPower(0);
        motorFR.setPower(-power);
    }

    void strafeForwardLeft(double power) {
        motorBL.setPower(0);
        motorBR.setPower(-power);
        motorFL.setPower(power);
        motorFR.setPower(0);
    }

    void strafeBackwardRight(double power) {

        motorBL.setPower(0);
        motorBR.setPower(power);
        motorFL.setPower(-power);
        motorFR.setPower(0);
    }

    void strafeBackwardLeft(double power) {
        motorBL.setPower(-power);
        motorBR.setPower(0);
        motorFL.setPower(0);
        motorFR.setPower(power);
    }


    // Autonomous Functions

    void goForwardDistance(double distance, double power) throws InterruptedException{
        double targetPosition = -distance * TICKS_PER_INCH;
        resetEncoders(allMotors);

        motorBL.setTargetPosition((int)targetPosition);
        motorFL.setTargetPosition((int)targetPosition);
        motorBR.setTargetPosition((int)-targetPosition);
        motorFR.setTargetPosition((int)-targetPosition);

        setMode(allMotors, DcMotor.RunMode.RUN_TO_POSITION);

        motorBL.setPower(power);
        motorBR.setPower(power);
        motorFL.setPower(power);
        motorFR.setPower(power);

        while (motorBL.isBusy() && motorFR.isBusy() && motorBR.isBusy() && motorFL.isBusy()) {}

        stopMotors(allMotors);
    }

    void goBackwardDistance(double distance, double speed) throws InterruptedException{
        double targetPosition = -distance * TICKS_PER_INCH;
        resetEncoders(allMotors);

        motorBL.setTargetPosition((int)-targetPosition);
        motorFL.setTargetPosition((int)-targetPosition);
        motorBR.setTargetPosition((int)targetPosition);
        motorFR.setTargetPosition((int)targetPosition);

        setMode(allMotors, DcMotor.RunMode.RUN_TO_POSITION);

        motorBL.setPower(speed);
        motorBR.setPower(speed);
        motorFL.setPower(speed);
        motorFR.setPower(speed);

        while (motorBL.isBusy() && motorFR.isBusy() && motorBR.isBusy() && motorFL.isBusy()) {}

        stopMotors(allMotors);
    }
}
