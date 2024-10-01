package com.irdc.shortvideo.service;

import com.irdc.shortvideo.VO.remind.CommentLikeRemindVO;
import com.irdc.shortvideo.VO.remind.VideoCommentRemindVO;
import com.irdc.shortvideo.VO.remind.VideoLikeRemindVO;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/8 14:15
 * Description：
 */
public interface RemindService {

    List<VideoCommentRemindVO> getVideoCommentRemind(String userId);

    List<CommentLikeRemindVO> getCommentLikeRemind(String userId);

    List<VideoLikeRemindVO> getVideoLikeRemind(String userId);
}
