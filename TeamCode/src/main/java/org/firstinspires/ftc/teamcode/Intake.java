package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import static org.firstinspires.ftc.teamcode.Intake.IntakeState.CLOSED;
import static org.firstinspires.ftc.teamcode.Intake.IntakeState.OPEN;

public class Intake {

    final int MOTOR_LEFT_OPEN = 0;
    final int MOTOR_RIGHT_OPEN = 0;
    final int MOTOR_LEFT_CLOSED = 500; // TODO: Set
    final int MOTOR_RIGHT_CLOSED = -500; // TODO: Set
    final float MOVEMENT_SPEED = 0.6f;

    DcMotor leftArm;
    DcMotor rightArm;

    public Intake(DcMotor intakeLeft, DcMotor intakeRight) {
        this.leftArm = intakeLeft;
        this.rightArm = intakeRight;

    }

    void open() {
        leftArm.setTargetPosition(MOTOR_LEFT_OPEN);
        rightArm.setTargetPosition(MOTOR_RIGHT_OPEN);

        leftArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftArm.setPower(MOVEMENT_SPEED);
        rightArm.setPower(MOVEMENT_SPEED);

        while (leftArm.isBusy() && rightArm.isBusy()) {}

        leftArm.setPower(0);
        rightArm.setPower(0);

        state = OPEN;
    }

    void close(){
        leftArm.setTargetPosition(MOTOR_LEFT_CLOSED);
        rightArm.setTargetPosition(MOTOR_RIGHT_CLOSED);

        leftArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftArm.setPower(MOVEMENT_SPEED);
        rightArm.setPower(MOVEMENT_SPEED);

        while (leftArm.isBusy() && rightArm.isBusy()) {}

        leftArm.setPower(0);
        rightArm.setPower(0);

        state = CLOSED;
    }

    void toggle() {
        switch (state) {
            case OPEN:
                close();
            case CLOSED:
                open();
        }
    }

    enum IntakeState {
        OPEN,
        CLOSED;
    }

    public IntakeState state;
}
