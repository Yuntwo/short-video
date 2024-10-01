package com.irdc.shortvideo.service.impl;

import com.irdc.shortvideo.VO.CommentVO;
import com.irdc.shortvideo.VO.CommentWithUserVO;
import com.irdc.shortvideo.VO.remind.VideoCommentRemindVO;
import com.irdc.shortvideo.constant.RedisConstant;
import com.irdc.shortvideo.entity.AcademicVideos;
import com.irdc.shortvideo.entity.Comments;
import com.irdc.shortvideo.entity.Videos;
import com.irdc.shortvideo.enums.VideoTypeEnum;
import com.irdc.shortvideo.mapper.*;
import com.irdc.shortvideo.service.CommentsService;
import com.irdc.shortvideo.websocket.WebSocketServer;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author HR at 2020/10/07 21:30
 * Description:
 */
@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private WebSocketServer webSocketServer;

    @Autowired
    private CommentsWithUsersMapper commentsWithUsersMapper;

    @Autowired
    private AcademicVideosMapper academicVideosMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean createComments(CommentVO commentVO, String userId) throws IOException, EncodeException {


        Integer videoType = (commentVO.getType() == 1) ? VideoTypeEnum.ACADEMIC_VIDEO.getCode() : VideoTypeEnum.COMMON_VIDEO.getCode();

        // 1. 评论信息存表
        Comments comments = new Comments();

        BeanUtils.copyProperties(commentVO,comments);

        // comments.setCommentId(UUIDUtil.getId());
        String commentId = Sid.next();
        comments.setCommentId(commentId);
        comments.setFromUserId(userId);

        int i = commentsMapper.insertSelective(comments);

        String videoId = commentVO.getVideoId();
        String title, publisherId;

        // 2. 视频表评论数+1
        if(videoType.equals(VideoTypeEnum.ACADEMIC_VIDEO.getCode())){
            academicVideosMapper.addCommentNum(commentVO.getVideoId());
            AcademicVideos academicVideos = academicVideosMapper.queryAcademicVideoByVideoId(videoId);
            title = academicVideos.getTitle();
            publisherId = academicVideos.getUserId();
        }else {
            videosMapper.addCommentNum(commentVO.getVideoId());
            Videos videos = videosMapper.queryById(videoId);
            title = videos.getTitle();
            publisherId = videos.getUserId();
        }

        //3 推送

        String username = usersMapper.queryUserByUserId(userId).getUsername();

        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);

        if(!publisherId.equals(userId)) {
            if (webSocketServer.isOnline(publisherId)) {
                VideoCommentRemindVO videoCommentRemindVO = new VideoCommentRemindVO();
                videoCommentRemindVO.setType(2);
                videoCommentRemindVO.setVideoId(videoId);
                videoCommentRemindVO.setTitle(title);
                videoCommentRemindVO.setCommentId(commentId);
                videoCommentRemindVO.setCommentContext(commentVO.getContent());
                videoCommentRemindVO.setUserId(userId);
                videoCommentRemindVO.setUsername(username);
                videoCommentRemindVO.setCreateTime(new Date());

                webSocketServer.sendMessageToUser(videoCommentRemindVO, publisherId);
            } else {
                String hashkey = publisherId + "::" + Sid.next();
                String hashValue = "2" + "::" + videoId + "::" + title + "::" + commentId + "::" + commentVO.getContent() + "::" + userId + "::" + username + "::" + (dateString);
                redisTemplate.opsForHash().put(RedisConstant.REMIND_VIDEO_COMMENT, hashkey, hashValue);

            }
        }
        return i != 0;

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CommentWithUserVO> queryCommentsByVideoId(String videoId, String userId){
        return commentsWithUsersMapper.queryCommentsByVideoId(videoId, userId);
    }

    @Override
    public String getCommenterById(String commentId) {
        return commentsMapper.queryPublisherIdByCommentId(commentId);
    }
}
