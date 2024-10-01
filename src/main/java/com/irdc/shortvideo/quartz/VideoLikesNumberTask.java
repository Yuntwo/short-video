package com.irdc.shortvideo.quartz;

import com.irdc.shortvideo.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/14 20:27
 * Description：
 */
@Slf4j
public class VideoLikesNumberTask extends QuartzJobBean {

    @Autowired
    LikeService likeService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        log.info("VideoLikesNumberTask-------- {}", sdf.format(new Date()));

        //将 Redis 里的点赞信息同步到数据库里
        likeService.transLikedCountFromRedis2DB();
    }
}
