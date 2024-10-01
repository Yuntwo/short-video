package com.irdc.shortvideo.controller.academicVideo;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.VO.VideoUpdateVO;
import com.irdc.shortvideo.VO.response.ResponseVideoVO;
import com.irdc.shortvideo.VO.video.VideoIdVO;
import com.irdc.shortvideo.entity.AcademicVideos;
import com.irdc.shortvideo.entity.AdvertisementVideos;
import com.irdc.shortvideo.entity.Videos;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.service.AcademicVideosService;
import com.irdc.shortvideo.service.AdvertisementVideosService;
import com.irdc.shortvideo.service.VideoService;
import com.irdc.shortvideo.utils.ResultVOUtil;
import com.irdc.shortvideo.utils.aliyun.vod.VideoPlayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/12 16:02
 * Description：
 */
@Api(value = "学术视频播放接口", tags = {"学术视频播放"})
@RestController
@RequestMapping("/academicVideo")
@Slf4j
public class AcademicVideoPlayController {

    @Autowired
    private AcademicVideosService academicVideosService;

    @Autowired
    private AdvertisementVideosService advertisementVideosService;


    @ApiOperation(value = "获取学术视频播放地址",notes = "/academicVideo/getVideoPlayAuth")
    @PostMapping("/getVideoPlayAuth")
    public ResultVO getVideoPlayAuth(@RequestBody VideoIdVO videoIdVO) {

        String videoId = videoIdVO.getVideoId();
        if(StringUtils.isEmpty(videoId)) {
            log.error("【获取视频播放地址】videoId不存在，videoId: {}",videoId);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        AcademicVideos academicVideos = academicVideosService.findOneAcademicVideoByVideoId(videoId);

        AdvertisementVideos advertisementVideo;
        if(academicVideos == null) {
            advertisementVideo = advertisementVideosService.findByVideoId(videoId);
            if(advertisementVideo == null){
                log.error("【获取视频播放地址】视频不存在，videoId: {}",videoId);
                return ResultVOUtil.error(ResultEnum.VIDEO_IS_NOT_EXIST.getCode(),ResultEnum.VIDEO_IS_NOT_EXIST.getMessage());
            }
        }

//        if(video == null){
//            log.error("【获取视频播放地址】视频不存在，videoId: {}",videoUpdateVO.getVideoId());
//            return ResultVOUtil.error(ResultEnum.VIDEO_IS_NOT_EXIST.getCode(),ResultEnum.VIDEO_IS_NOT_EXIST.getMessage());
//        }

        GetVideoPlayAuthResponse response;
        try {
            response = VideoPlayUtil.getVideoPlayAuth(videoId);
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
