package org.firstinspires.ftc.teamcode.testopmodes;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.wrappers.ArmWrapper;
import org.opencv.core.Mat;

/*
 * Op mode for preliminary tuning of the follower PID coefficients (located in the drive base
 * classes). The robot drives back and forth in a straight line indefinitely. Utilization of the
 * dashboard is recommended for this tuning routine. To access the dashboard, connect your computer
 * to the RC's WiFi network. In your browser, navigate to https://192.168.49.1:8080/dash if you're
 * using the RC phone or https://192.168.43.1:8080/dash if you are using the Control Hub. Once
 * you've successfully connected, start the program, and your robot will begin moving back() and
 * backward. You should observe the target position (green) and your pose estimate (blue) and adjust
 * your follower PID coefficients such that you follow the target position as accurately as possible.
 * If you are using SampleMecanumDrive, you should be tuning TRANSLATIONAL_PID and HEADING_PID.
 * If you are using SampleTankDrive, you should be tuning AXIAL_PID, CROSS_TRACK_PID, and HEADING_PID.
 * These coefficients can be tuned live in dashboard.
 *
 * This opmode is designed as a convenient, coarse tuning for the follower PID coefficients. It
 * is recommended that you use the FollowerPIDTuner opmode for further fine tuning.
 */
@Config
@Autonomous(group = "drive", name = "circle")
public class RRTestV2 extends LinearOpMode {

    public static double DISTANCE = 5;

    @Override
    public void runOpMode() throws InterruptedException {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        ArmWrapper armWrapper = new ArmWrapper(hardwareMap, telemetry);

        armWrapper.init(true);
        Trajectory trajectory1 = drive.trajectoryBuilder(new Pose2d(10,60, Math.toRadians(-90)))
                .splineTo(new Vector2d(0,40),Math.toRadians(-135))
                .build();
        Trajectory trajectory2 = drive.trajectoryBuilder(
                trajectory1.end()).splineTo(new Vector2d(20,64),0).splineTo(new Vector2d(60,60),0).build();

        Trajectory trajectory3 = drive.trajectoryBuilder(trajectory2.end()).splineTo(new Vector2d(20,64),0).splineTo(new Vector2d(0,40),0).build();


        armWrapper.SetLevel(3);
        drive.followTrajectory(trajectory1);
        armWrapper.IntakeReverse(1);
        drive.followTrajectory(trajectory2);
        armWrapper.Intake(1);
        drive.followTrajectory(trajectory3);
        armWrapper.SetLevel(3);
        armWrapper.IntakeReverse(1);


    }
}
