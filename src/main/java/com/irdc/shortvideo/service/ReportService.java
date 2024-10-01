package com.irdc.shortvideo.service;

import com.irdc.shortvideo.VO.ReportVideoVO;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/10/7 8:56
 * Description：
 */
public interface ReportService {

    /**
     * 创建举报信息
     * @param userId
     * @param reportVideoVO
     */
    void createReportVideo(String userId, ReportVideoVO reportVideoVO);
}
