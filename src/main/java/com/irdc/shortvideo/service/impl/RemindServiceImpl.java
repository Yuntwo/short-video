package com.irdc.shortvideo.service.impl;

import com.irdc.shortvideo.VO.remind.CommentLikeRemindVO;
import com.irdc.shortvideo.VO.remind.VideoCommentRemindVO;
import com.irdc.shortvideo.VO.remind.VideoLikeRemindVO;
import com.irdc.shortvideo.constant.RedisConstant;
import com.irdc.shortvideo.service.RemindService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/8 14:19
 * Description：
 */
@Service
@Slf4j
public class RemindServiceImpl implements RemindService {

    @Autowired
    private RedisTemplate redisTemplate;

//    @Autowired
//    private UserLikeCommentMapper userLikeCommentMapper;
//
//    @Autowired
//    private CommentsMapper commentsMapper;
//
//    @Autowired
//    private UserLikeVideoMapper userLikeVideoMapper;

    @Override
    public List<VideoCommentRemindVO> getVideoCommentRemind(String userId){
        try{
            Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisConstant.REMIND_VIDEO_COMMENT, ScanOptions.NONE);
            List<VideoCommentRemindVO> list = new ArrayList<>();

            while (cursor.hasNext()) {
                Map.Entry<Object, Object> map = cursor.next();

                String hashkey = (String) map.getKey();

                String key = hashkey.split("::")[0];

                if(key.equals(userId)) {
                    String value = (String) map.getValue();

                    String[] s = value.split("::");

                    if (s.length == 8) {
                        VideoCommentRemindVO videoCommentRemindVO = new VideoCommentRemindVO();
                        videoCommentRemindVO.setType(Integer.valueOf(s[0]));
                        videoCommentRemindVO.setVideoId(s[1]);
                        videoCommentRemindVO.setTitle(s[2]);
                        videoCommentRemindVO.setCommentId(s[3]);
                        videoCommentRemindVO.setCommentContext(s[4]);
                        videoCommentRemindVO.setUserId(s[5]);
                        videoCommentRemindVO.setUsername(s[6]);

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date currentTime = formatter.parse(s[7]);

                        videoCommentRemindVO.setCreateTime(currentTime);

                        list.add(videoCommentRemindVO);
                    }
                    redisTemplate.opsForHash().delete(RedisConstant.REMIND_VIDEO_COMMENT, hashkey);

                }


            }
            cursor.close();
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<CommentLikeRemindVO> getCommentLikeRemind(String userId) {
        try{
            Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisConstant.REMIND_COMMENT_LIKE, ScanOptions.NONE);
            List<CommentLikeRemindVO> list = new ArrayList<>();

            while (cursor.hasNext()) {
                Map.Entry<Object, Object> map = cursor.next();

                String hashkey = (String) map.getKey();

                String key = hashkey.split("::")[0];

                if(key.equals(userId)) {
                    String value = (String) map.getValue();

                    String[] s = value.split("::");

                    if (s.length == 8) {
                        CommentLikeRemindVO commentLikeReminderVO = new CommentLikeRemindVO();
                        commentLikeReminderVO.setType(Integer.valueOf(s[0]));
                        commentLikeReminderVO.setVideoId(s[1]);
                        commentLikeReminderVO.setTitle(s[2]);
                        commentLikeReminderVO.setCommentId(s[3]);
                        commentLikeReminderVO.setOriCommentContext(s[4]);
                        commentLikeReminderVO.setUserId(s[5]);
                        commentLikeReminderVO.setUsername(s[6]);

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date currentTime = formatter.parse(s[7]);

                        commentLikeReminderVO.setCreateTime(currentTime);

                        list.add(commentLikeReminderVO);
                    }
                    redisTemplate.opsForHash().delete(RedisConstant.REMIND_COMMENT_LIKE, hashkey);
                }

            }
            cursor.close();
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<VideoLikeRemindVO> getVideoLikeRemind(String userId) {
        try{
            Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisConstant.REMIND_VIDEO_LIKE, ScanOptions.NONE);
            List<VideoLikeRemindVO> list = new ArrayList<>();

            while (cursor.hasNext()) {
                Map.Entry<Object, Object> map = cursor.next();

                String hashkey = (String) map.getKey();

                String key = hashkey.split("::")[0];

                if(key.equals(userId)) {
                    String value = (String) map.getValue();

                    String[] s = value.split("::");

                    if (s.length == 6) {
                        VideoLikeRemindVO videoLikeRemindVO = new VideoLikeRemindVO();
                        videoLikeRemindVO.setType(Integer.valueOf(s[0]));
                        videoLikeRemindVO.setVideoId(s[1]);
                        videoLikeRemindVO.setTitle(s[2]);
                        videoLikeRemindVO.setUserId(s[3]);
                        videoLikeRemindVO.setUsername(s[4]);

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date currentTime = formatter.parse(s[5]);
                        videoLikeRemindVO.setCreateTime(currentTime);

                        list.add(videoLikeRemindVO);
                    }
                    redisTemplate.opsForHash().delete(RedisConstant.REMIND_VIDEO_LIKE, hashkey);
                }

            }
            cursor.close();
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
