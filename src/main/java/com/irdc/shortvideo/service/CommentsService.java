package com.irdc.shortvideo.service;

import com.irdc.shortvideo.VO.CommentVO;
import com.irdc.shortvideo.VO.CommentWithUserVO;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.List;

/**
 * @author HR at 2020/10/07 21:30
 * Description:
 */

public interface CommentsService {

    /**
     * 上传评论
     * @param commentVO
     * @param userId
     */
    boolean createComments(CommentVO commentVO, String userId) throws IOException, EncodeException;

    /**
     * 根据视频id获取评论列表以及该用户的评论点赞情况
     * @param videoId
     * @param userId
     * @return
     */
    List<CommentWithUserVO> queryCommentsByVideoId(String videoId, String userId);

    /**
     * 根据评论id获取评论
     * @param commentId
     * @return
     */
    String getCommenterById(String commentId);

}
