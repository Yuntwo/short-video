package com.irdc.shortvideo.mapper;

import com.irdc.shortvideo.VO.VideoWithUserVO;
import com.irdc.shortvideo.entity.UserLikeVideo;
import com.irdc.shortvideo.entity.Videos;
import com.irdc.shortvideo.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/10/5 22:49
 * Description：
 */
@Mapper
public interface VideoAndUserLikeVideoMapper extends MyMapper<Videos> {

    /**
     * 用户喜欢的视频
     * @param userId
     * @return
     */
    public List<VideoWithUserVO> queryUserLikeVideoInfoByUserId(String userId);


}
