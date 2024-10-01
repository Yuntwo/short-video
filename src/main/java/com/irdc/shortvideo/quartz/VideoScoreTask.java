package com.irdc.shortvideo.quartz;

import com.irdc.shortvideo.service.AcademicVideosService;
import com.irdc.shortvideo.service.VideoService;
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
 * @author chenliquan on 2021/3/8 20:03
 * Description：
 */
@Slf4j
public class VideoScoreTask extends QuartzJobBean {
    @Autowired
    private VideoService videoService;

    @Autowired
    private AcademicVideosService academicVideosService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        log.info("VideoScoreTask-------- {}", sdf.format(new Date()));

        //计算普通视频得分
        videoService.computeScoreForVideos();

        //计算学术视频得分
        academicVideosService.computeScoreForAcademicVideos();
    }
}
