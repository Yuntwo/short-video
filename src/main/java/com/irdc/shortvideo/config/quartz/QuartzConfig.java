package com.irdc.shortvideo.config.quartz;

import com.irdc.shortvideo.quartz.VideoLikesNumberTask;
import com.irdc.shortvideo.quartz.VideoScoreTask;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/14 20:23
 * Description：
 */
@Configuration
public class QuartzConfig {

    private static final String VIDEO_LIKES_NUMBER_TASK = "videoLikesNumberTask";

    private static final String VIDEO_SCORE_TASK = "videoScoreTask";

    @Bean
    public JobDetail quartzDetail(){
        return JobBuilder.newJob(VideoLikesNumberTask.class).withIdentity(VIDEO_LIKES_NUMBER_TASK).storeDurably().build();
    }

    @Bean
    public JobDetail quartzDetail2(){
        return JobBuilder.newJob(VideoScoreTask.class).withIdentity(VIDEO_SCORE_TASK).storeDurably().build();
    }


    @Bean
    public Trigger quartzTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMinutes(30)
//                设置时间周期单位秒
//                .withIntervalInSeconds(60)
                .repeatForever();

        return TriggerBuilder.newTrigger().forJob(quartzDetail())
                .withIdentity(VIDEO_LIKES_NUMBER_TASK)
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public Trigger quartzTrigger2(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMinutes(60)
//                设置时间周期单位秒
//                .withIntervalInSeconds(60)
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(quartzDetail2())
                .withIdentity(VIDEO_SCORE_TASK)
                .withSchedule(scheduleBuilder)
                .build();
    }
}
