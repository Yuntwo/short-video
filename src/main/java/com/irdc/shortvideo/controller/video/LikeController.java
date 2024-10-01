package com.irdc.shortvideo.controller.video;

import com.irdc.shortvideo.VO.video.LikeVideoVO;
import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.VO.video.QueryUserLikeVO;
import com.irdc.shortvideo.annotation.UserAuthentication;
import com.irdc.shortvideo.config.rabbitmq.MQConfig;
import com.irdc.shortvideo.enums.AuthAopEnum;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.rabbitmq.message.LikeVideoMessage;
import com.irdc.shortvideo.service.AdvertisementVideosService;
import com.irdc.shortvideo.service.LikeService;
import com.irdc.shortvideo.service.VideoService;
import com.irdc.shortvideo.utils.RedisUtil;
import com.irdc.shortvideo.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.HashMap;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/28 21:00
 * Description：
 */
@Api(value = "视频点赞接口", tags = {"视频点赞"})
@RestController
@RequestMapping("/video")
@Slf4j
public class LikeController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private LikeService likeService;

    @Autowired
    private AmqpTemplate amqpTemplate;


    @ApiOperation(value = "查询用户对是否点赞当前的视频",notes = "/video/queryUserLikeVideo")
    @PostMapping("/queryUserLikeVideo")
    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO queryUserLike(@RequestBody QueryUserLikeVO queryUserLikeVO, HttpServletRequest request) {
        String videoId = queryUserLikeVO.getVideoId();
        if(StringUtils.isBlank(videoId)) {
            log.error("【用户是否点赞视频】输入参数不完整，queryUserLikeVO: {}",videoId);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }
        String userId = redisUtil.getUserIdByToken(request);
        boolean res = videoService.isUserLikeVideo(userId, videoId);
        HashMap<String,Boolean> map =new HashMap<>();
        map.put("isLike",res);
        return ResultVOUtil.success(map);
    }

    @ApiOperation(value = "视频点赞", notes = "/video/likeVideo")
    @PostMapping("/likeVideo")
    @UserAuthentication(pass = true, role = AuthAopEnum.USER)
    public ResultVO like(@RequestBody LikeVideoVO likeVideoVO, HttpServletRequest request) throws IOException, EncodeException {

        // 1. 前端参数完整性检验
        if (org.springframework.util.StringUtils.isEmpty(likeVideoVO.getVideoId())) {
            log.error("【点赞视频】传入的参数不正确，likeVideoVO: {}",likeVideoVO);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        // 2. 查询是否存在
        String userId = redisUtil.getUserIdByToken(request);

        boolean like = videoService.isUserLikeVideo(userId, likeVideoVO.getVideoId());
        if(like){
            return ResultVOUtil.error(ResultEnum.VIDEO_IS_LIKED.getCode(),ResultEnum.VIDEO_IS_LIKED.getMessage());
        }


        LikeVideoMessage likeVideoMessage = new LikeVideoMessage();
        likeVideoMessage.setLike(true);
        likeVideoMessage.setUserId(userId);
        likeVideoMessage.setVideoId(likeVideoVO.getVideoId());

        //发送到消息队列
        amqpTemplate.convertAndSend(MQConfig.LIKE_DIRECT_EXCHANGE,MQConfig.ROUTINGKEY2,likeVideoMessage);
        //修改点赞数
        likeService.likeVideoNum(likeVideoVO,userId);

        likeService.likeVideoRemind(likeVideoVO, userId);


        return ResultVOUtil.success();
    }

    @ApiOperation(value = "取消视频点赞", notes = "/video/dislikeVideo")
    @PostMapping("/dislikeVideo")
    @UserAuthentication(pass = true, role = AuthAopEnum.USER)
    public ResultVO unlike(@RequestBody LikeVideoVO likeVideoVO, HttpServletRequest request){

        // 1. 前端参数完整性检验
        if (likeVideoVO.getVideoId().isEmpty()){
            log.error("【点赞视频】传入的参数不正确，likeVideoVO: {}", likeVideoVO);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        // 2. 查询是否存在
        String userId = redisUtil.getUserIdByToken(request);

        boolean like = videoService.isUserLikeVideo(userId, likeVideoVO.getVideoId());
        if(!like){
            return ResultVOUtil.error(ResultEnum.VIDEO_IS_NOT_LIKED.getCode(), ResultEnum.VIDEO_IS_NOT_LIKED.getMessage());
        }

        LikeVideoMessage cancelLikeVideoMessage = new LikeVideoMessage();
        cancelLikeVideoMessage.setLike(false);
        cancelLikeVideoMessage.setUserId(userId);
        cancelLikeVideoMessage.setVideoId(likeVideoVO.getVideoId());

        amqpTemplate.convertAndSend(MQConfig.LIKE_DIRECT_EXCHANGE,MQConfig.ROUTINGKEY2,cancelLikeVideoMessage);
        likeService.unlikeVideoNum(likeVideoVO,userId);

//        // 3. 修改数据库
//        likeService.unlikeVideo(likeVideoVO, userId);

        return ResultVOUtil.success();
    }

}
