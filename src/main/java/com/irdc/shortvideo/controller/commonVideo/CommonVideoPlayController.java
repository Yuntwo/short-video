package com.irdc.shortvideo.controller.commonVideo;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.VO.VideoUpdateVO;
import com.irdc.shortvideo.annotation.UserAuthentication;
import com.irdc.shortvideo.VO.response.ResponseVideoVO;
import com.irdc.shortvideo.config.rabbitmq.MQConfig;
import com.irdc.shortvideo.entity.AdvertisementVideos;
import com.irdc.shortvideo.entity.Videos;
import com.irdc.shortvideo.enums.AuthAopEnum;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.rabbitmq.message.LikeVideoMessage;
import com.irdc.shortvideo.service.AdvertisementVideosService;
import com.irdc.shortvideo.service.LikeService;
import com.irdc.shortvideo.service.VideoService;
import com.irdc.shortvideo.utils.RedisUtil;
import com.irdc.shortvideo.utils.ResultVOUtil;
import com.irdc.shortvideo.utils.aliyun.vod.VideoPlayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author yangshu
 */

@Api(value = "普通视频播放接口", tags = {"视频播放"})
@RestController
@RequestMapping("/commonVideo")
@Slf4j
public class CommonVideoPlayController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private AdvertisementVideosService advertisementVideosService;


    @ApiOperation(value = "获取普通视频播放地址",notes = "/commonVideo/getVideoUploadAuth")
    @PostMapping("/getVideoPlayAuth")
    public ResultVO getVideoPlayAuth(@RequestBody VideoUpdateVO videoUpdateVO) {

        if(StringUtils.isEmpty(videoUpdateVO.getVideoId())) {
            log.error("【获取视频播放地址】videoId不存在，videoId: {}",videoUpdateVO.getVideoId());
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        Videos video = videoService.findVideoInfoByVideoId(videoUpdateVO.getVideoId());


        AdvertisementVideos advertisementVideo;
        if(video == null) {
            advertisementVideo = advertisementVideosService.findByVideoId(videoUpdateVO.getVideoId());
            if(advertisementVideo == null){
                log.error("【获取视频播放地址】视频不存在，videoId: {}",videoUpdateVO.getVideoId());
                return ResultVOUtil.error(ResultEnum.VIDEO_IS_NOT_EXIST.getCode(),ResultEnum.VIDEO_IS_NOT_EXIST.getMessage());
            }
        }

//        if(video == null){
//            log.error("【获取视频播放地址】视频不存在，videoId: {}",videoUpdateVO.getVideoId());
//            return ResultVOUtil.error(ResultEnum.VIDEO_IS_NOT_EXIST.getCode(),ResultEnum.VIDEO_IS_NOT_EXIST.getMessage());
//        }

        GetVideoPlayAuthResponse response;
        try {
            response = VideoPlayUtil.getVideoPlayAuth(videoUpdateVO.getVideoId());
        } catch (ClientException e) {
            log.error("【获取视频播放地址】阿里云异常，ClientException: {}",e.getMessage());
            return ResultVOUtil.error(ResultEnum.ALIYUN_VIDEO_VOD_ERROR.getCode(),ResultEnum.ALIYUN_VIDEO_VOD_ERROR.getMessage());
        }
        if (response == null) {
            log.error("【获取视频播放地址】获取不到阿里云的播放凭证，response: {}",response);
            return ResultVOUtil.error(ResultEnum.ALIYUN_VIDEO_VOD_ERROR.getCode(),ResultEnum.ALIYUN_VIDEO_VOD_ERROR.getMessage());
        }
        return ResultVOUtil.success(ResponseVideoVO.fromGetVideoPlayAuthResponse(response));
    }
}
