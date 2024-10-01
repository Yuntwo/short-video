package com.irdc.shortvideo.service;


import com.irdc.shortvideo.VO.PagedResultVO;
import com.irdc.shortvideo.VO.video.AcademicVideoUpdateVO;
import com.irdc.shortvideo.VO.video.AcademicVideoUploadVO;
import com.irdc.shortvideo.VO.video.AcademicVideoVO;
import com.irdc.shortvideo.VO.video.AcademicVideoWithUserVO;
import com.irdc.shortvideo.entity.AcademicVideos;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/26 20:12
 * Description：
 */

public interface AcademicVideosService {

    AcademicVideoVO queryAcademicVideoVOById(String academicVideoId);

    AcademicVideoWithUserVO queryAcademicVideoWithUserVOByVideoId(String videoId);

    PagedResultVO queryRecommendAcademicVideoWithUserVO(Integer page, Integer size);

    AcademicVideos queryAcademicVideoByVideoIdAndStatusAndIsPublic(String academicVideoId, Integer status, Integer isPublic);

    AcademicVideos queryAcademicVideoByStatus(String academicVideoId, Integer status);

    AcademicVideos queryAcademicVideoByVideoId(String academicVideoId);

    PagedResultVO queryRecommendVideos(Integer page, Integer size);

    void updateAcademicVideo(AcademicVideos academicVideos);

    void delAcademicVideos(AcademicVideos academicVideos);

    void createAcademicVideos(AcademicVideos academicVideos);

//    void updateAcademicVideosWithPaper(AcademicVideoUpdateVO academicVideoUpdateVO);

    AcademicVideos findOneAcademicVideoByVideoId(String videoId);

    /**
     * 计算视频得分，在定时任务中执行
     */
    void computeScoreForAcademicVideos();
}
