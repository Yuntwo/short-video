package com.irdc.shortvideo.service.impl;

import com.irdc.shortvideo.constant.RedisConstant;
import com.irdc.shortvideo.mapper.ReportTagMapper;
import com.irdc.shortvideo.mapper.VideoTagMapper;
import com.irdc.shortvideo.service.TagService;
import com.irdc.shortvideo.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/10/7 16:32
 * Description：
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private ReportTagMapper reportTagMapper;

    @Autowired
    private VideoTagMapper videoTagMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<String> queryAllVideoTag() {
        List<String> videoTagListFromRedis = redisUtil.lGet(RedisConstant.VIDEO_TAG, 0, -1);


        if(videoTagListFromRedis == null || videoTagListFromRedis.isEmpty()){
            List<String> videoTagListFromDb = videoTagMapper.queryAllVideoTag();
//            System.out.println("videoTagFromDB: " + videoTagListFromDb);
            redisUtil.lSet(RedisConstant.VIDEO_TAG, videoTagListFromDb);
//            System.out.println("videoTag from db");
//            return videoTagListFromDb;
            videoTagListFromRedis = redisUtil.lGet(RedisConstant.VIDEO_TAG, 0, -1);
        }
//        System.out.println("videoTag from redis");

//        System.out.println("before: ");
//        for(String str : videoTagListFromRedis){
//            System.out.println(str);
//        }
//
//
//
//        int competitionIndex = videoTagListFromRedis.indexOf("短视频竞赛");
//        if (competitionIndex != -1){
//            Collections.swap(videoTagListFromRedis, competitionIndex, 0);
//        }
//
//        int otherIndex = videoTagListFromRedis.indexOf("其他");
//        if(otherIndex != -1){
//            Collections.swap(videoTagListFromRedis, otherIndex, videoTagListFromRedis.size() - 1);
//        }
//
//        System.out.println("after: ");
//        for(String str : videoTagListFromRedis){
//            System.out.println(str);
//        }


        return videoTagListFromRedis;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<String> queryAllReportTag() {
        List<String> reportTagListFromRedis = redisUtil.lGet(RedisConstant.REPORT_TAG,0, -1);
        if(reportTagListFromRedis == null || reportTagListFromRedis.isEmpty()){
            List<String> reportTagListFromDb = reportTagMapper.queryAllReportTag();
//            System.out.println("reportTagFromDB: " + reportTagListFromDb);
            redisUtil.lSet(RedisConstant.REPORT_TAG,reportTagListFromDb);
//            System.out.println("reportTag from db");
//            return reportTagListFromDb;
            reportTagListFromRedis = redisUtil.lGet(RedisConstant.REPORT_TAG,0, -1);
        }
//        System.out.println("reportTag from redis");
        return reportTagListFromRedis;
    }
}
