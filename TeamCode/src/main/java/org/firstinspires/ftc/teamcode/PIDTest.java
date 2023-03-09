package org.firstinspires.ftc.teamcode;

import android.annotation.SuppressLint;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "MecanumDrive", group = "PIDTest")

public class PIDTest extends LinearOpMode {
    private final ElapsedTime runtime = new ElapsedTime();

    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        DcMotor motor1 = hardwareMap.get(DcMotor.class, "motor 1");

        double position = motor1.getCurrentPosition()*(1f/28f)*(360f/1f)*(1f/20f);
        double target = 0;
        double Kp = 0.04;
        double Ki = 0.00001;
        double Kd = 0.001;
        double time = getRuntime();
        double previousTime = time;
        double error = target - position;
        double previousError = error;
        double errorSum = 0;

        waitForStart();
        runtime.reset();

        while(opModeIsActive()) {
            position = motor1.getCurrentPosition()*(1f/28f)*(360f/1f)*(1f/20f);
            time = getRuntime();
            error = target - position;
            errorSum += error;

            double frameTime = (time-previousTime);
            double changeInError = (error - previousError);

            double P = error * Kp;
            double I = Ki*errorSum;
            double D = (changeInError/frameTime)*Kd;

            motor1.setPower(P+I+D);

            target += gamepad1.right_stick_x*frameTime*2000;

            previousTime = time;
            previousError = error;

            telemetry.addData("Runtime", getRuntime());
            telemetry.addData("Frame Time", frameTime);
            telemetry.addData("Target", target);
            telemetry.addData("Current Position", position);
            telemetry.addData("Error", error);
            telemetry.addData("Change in Error", changeInError);
            telemetry.addData("P", P);
            telemetry.addData("I", I);
            telemetry.addData("D", D);
            telemetry.addData("Motor Power", motor1.getPower()*100d);
            telemetry.update();
        }
    }


}
