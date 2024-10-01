package com.irdc.shortvideo.mapper;

import com.irdc.shortvideo.VO.video.AcademicVideoWithUserVO;
import com.irdc.shortvideo.entity.AcademicVideos;
import com.irdc.shortvideo.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/26 20:10
 * Description：
 */
@Mapper
public interface AcademicVideosMapper extends MyMapper<AcademicVideos> {

    AcademicVideos queryAcademicVideoByVideoIdAndStatusAndIsPublic(String videoId, Integer status, Integer isPublic);

    AcademicVideos queryAcademicVideoByVideoIdAndStatus(String videoId, Integer status);

    Long queryLikeNumByVideoId(String videoId);

    List<AcademicVideos> getRecommendAcademicVideo();

    AcademicVideos queryAcademicVideoByVideoId(String videoId);

    AcademicVideoWithUserVO queryAcademicVideoWithUserVOByVideoId(String videoId);

    List<AcademicVideoWithUserVO> queryRecommendAcademicVideoWithUserVO();

    void addCommentNum(String videoId);

    List<AcademicVideos> queryAllVideos();

    void updateScoreByAcademicVideoId(String academicVideoId, Double score);
}
