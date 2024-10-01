package com.irdc.shortvideo.service;

import com.irdc.shortvideo.VO.video.AcademicPictureInfoVO;
import com.irdc.shortvideo.VO.video.AcademicPictureUploadVO;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/11 16:23
 * Description：
 */
public interface AcademicVideosPictureService {

    void createAcademicVideosPicture(AcademicPictureUploadVO academicPictureUploadVO, String url);

    void delAllAcademicVideoPicturesByVideoId(String videoId);

    List<AcademicPictureInfoVO> queryAcademicPictureInfoVOByVideoId(String videoId);
}
