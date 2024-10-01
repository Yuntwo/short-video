package com.irdc.shortvideo.mapper;

import com.irdc.shortvideo.VO.CommentWithUserVO;
import com.irdc.shortvideo.entity.Comments;
import com.irdc.shortvideo.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentsWithUsersMapper extends MyMapper<Comments> {

    /**
     * 根据视频id和用户的id获取评论列表以及该用户的评论点赞情况
     * @param videoId
     * @param userId
     * @return
     */
    List<CommentWithUserVO> queryCommentsByVideoId(String videoId, String userId);
}
