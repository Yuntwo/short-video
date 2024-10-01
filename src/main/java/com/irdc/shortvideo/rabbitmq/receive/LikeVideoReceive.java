package com.irdc.shortvideo.rabbitmq.receive;

import com.irdc.shortvideo.VO.video.LikeVideoVO;
import com.irdc.shortvideo.config.rabbitmq.MQConfig;
import com.irdc.shortvideo.rabbitmq.message.LikeVideoMessage;
import com.irdc.shortvideo.service.LikeService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.EncodeException;
import java.io.IOException;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/12 21:42
 * Description：
 */

@Service
public class LikeVideoReceive {

    @Autowired
    private LikeService likeService;

    @RabbitHandler
    @RabbitListener(queues = MQConfig.LIKE_QUEUE)
    public void processLikeVideo(LikeVideoMessage likeVideoMessage) throws IOException, EncodeException {
//        LikeVideoVO likeVideoVO = new LikeVideoVO();
//        likeVideoVO.setVideoId(likeVideoMessage.getVideoId());
        String userId = likeVideoMessage.getUserId();
        if(likeVideoMessage.isLike()){
            likeService.likeVideo(likeVideoMessage.getVideoId(),userId);
        } else{
            likeService.unlikeVideo(likeVideoMessage.getVideoId(),userId);
        }
    }
}
