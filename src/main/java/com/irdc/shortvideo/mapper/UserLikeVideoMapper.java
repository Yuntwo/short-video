package com.irdc.shortvideo.mapper;

import com.irdc.shortvideo.VO.CommentVO;
import com.irdc.shortvideo.entity.UserLikeVideo;
import com.irdc.shortvideo.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author HR at 2020/10/08 9:41
 * Description:
 */

@Mapper
public interface UserLikeVideoMapper extends MyMapper<UserLikeVideo> {

    /**
     * 判断视频点赞记录是否存在
     * @return
     */
    UserLikeVideo queryVideoLikeIsExists(String videoId, String userId);
}