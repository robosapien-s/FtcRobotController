package com.example.meepmeeppowerplay;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepRedOffense {
    public static void main(String[] args) {


//        AddTrajectorySequenceCallback callback = new AddTrajectorySequenceCallback() {
//            @NotNull
//            @Override
//            public TrajectorySequence buildTrajectorySequence(@NotNull DriveShim drive) {
//
//                TrajectorySequenceBuilder builder = drive.trajectorySequenceBuilder(new Pose2d(-36, -60, 90));
//
//                builder = builder.lineToLinearHeading(new Pose2d(-36,-61,Math.toRadians(45)));
//                builder = builder.lineToLinearHeading(new Pose2d(-12,-60,Math.toRadians(0)));
//
//                return null;
//            }
//        };

        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13.25)
                //.followTrajectorySequence(callback);
                .followTrajectorySequence(drive ->

                        drive.trajectorySequenceBuilder(new Pose2d(-36, -60, Math.toRadians(90)))
                                .lineToLinearHeading(new Pose2d(-12,-60,Math.toRadians(90)))
                                .lineToLinearHeading(new Pose2d(-12,-24,Math.toRadians(0)))
                                .lineToLinearHeading(new Pose2d(-12,-12,Math.toRadians(180)))
                                .lineToLinearHeading(new Pose2d(-60,-12,Math.toRadians(180)))
                                .lineToLinearHeading(new Pose2d(-48,-12,Math.toRadians(-90)))
                                .lineToLinearHeading(new Pose2d(-60,-12,Math.toRadians(180)))
                                .lineToLinearHeading(new Pose2d(-24,-12,Math.toRadians(90)))
                                .lineToLinearHeading(new Pose2d(-60,-12,Math.toRadians(180)))
                                .lineToLinearHeading(new Pose2d(-24,-12,Math.toRadians(-90)))

                                /*.turn(Math.toRadians(90))
                                .forward(30)
                                .turn(Math.toRadians(90))
                                .forward(30)
                                .turn(Math.toRadians(90))*/
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}