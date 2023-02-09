package org.firstinspires.ftc.teamcode;

import android.annotation.SuppressLint;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "MecanumDrive", group = "test")

public class Test extends LinearOpMode {
    private final ElapsedTime runtime = new ElapsedTime();


    private DcMotor epicMotor;

    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "do the stuff");
        telemetry.speak("this will not at all become extremely annoying in the future");

        telemetry.update();

        waitForStart();
        runtime.reset();


        epicMotor = hardwareMap.get(DcMotor.class,"motor 1");
        double position = epicMotor.getCurrentPosition()*(1f/28f)*(360f/1f)*(1f/20f);

        double target = 0;

        double Kp = 0.04;
        double Ki = 0.00001;
        double Kd = 0.001;



        double time = getRuntime();
        double previousTime = time;

        double error = target - position;
        double previousError = error;
        double errorSum = 0;

        while(opModeIsActive()) {
            position = epicMotor.getCurrentPosition()*(1f/28f)*(360f/1f)*(1f/20f);
            time = getRuntime();
            error = target - position;
            errorSum += error;

            double frameTime = (time-previousTime);
            double changeInError = (error - previousError);

            double P = error * Kp;
            double I = Ki*errorSum;
            double D = (changeInError/frameTime)*Kd;

            epicMotor.setPower(P+I+D);

            target += gamepad1.right_stick_x*frameTime*2000;

            previousTime = time;
            previousError = error;





            telemetry.addData("runtime",getRuntime());
            telemetry.addData("frameTime",frameTime);
            telemetry.addData("target",target);
            telemetry.addData("current position",position);
            telemetry.addData("error",error);
            telemetry.addData("change in error",changeInError);
            telemetry.addData("P ",P);
            telemetry.addData("I " ,I);
            telemetry.addData("D ",D);

            telemetry.addData("motor power ",epicMotor.getPower()*100d);

            telemetry.update();
        }
    }


}
