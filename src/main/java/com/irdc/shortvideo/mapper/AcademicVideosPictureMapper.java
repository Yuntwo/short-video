package com.irdc.shortvideo.mapper;

import com.irdc.shortvideo.VO.video.AcademicPictureInfoVO;
import com.irdc.shortvideo.entity.AcademicVideosPicture;
import com.irdc.shortvideo.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/11 14:27
 * Description：
 */
@Mapper
public interface AcademicVideosPictureMapper extends MyMapper<AcademicVideosPicture> {

    void updateAllPictureStatusByVideoId(String videoId, Integer status);

    List<AcademicPictureInfoVO> queryAcademicPictureInfoVOByVideoId(String videoId);
}
