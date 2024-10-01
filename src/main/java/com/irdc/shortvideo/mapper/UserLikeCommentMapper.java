package com.irdc.shortvideo.mapper;

import com.irdc.shortvideo.entity.UserLikeComment;
import com.irdc.shortvideo.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author HR at 2020/10/07 16:19
 * Description:
 */

@Mapper
public interface UserLikeCommentMapper extends MyMapper<UserLikeComment> {

    /**
     * 判断评论点赞记录是否存在
     * @return
     */
    UserLikeComment queryCommentLikeIsExists(String commentId, String userId);
}