package com.irdc.shortvideo.controller.commonVideo;

import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.VO.VideoUpdateVO;
import com.irdc.shortvideo.VO.video.DeleteVideoVO;
import com.irdc.shortvideo.annotation.UserAuthentication;
import com.irdc.shortvideo.entity.Videos;
import com.irdc.shortvideo.enums.AuthAopEnum;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.enums.VideoStatusEnum;
import com.irdc.shortvideo.service.VideoService;
import com.irdc.shortvideo.utils.ResultVOUtil;
import com.irdc.shortvideo.utils.aliyun.vod.VideoPlayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/9 16:01
 * Description：视频修改
 */

@Api(value = "普通视频修改接口", tags = {"视频修改"})
@RestController
@RequestMapping("/commonVideo")
@Slf4j
public class CommonVideoModifyController {

    @Autowired
    private VideoService videoService;


    @ApiOperation(value = "编辑普通视频",notes = "/commonVideo/modify")
    @PutMapping("/modify")
    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO modify(@Validated @RequestBody VideoUpdateVO videoUpdateVO) throws Exception {

        String videoId = videoUpdateVO.getVideoId();
        if(StringUtils.isEmpty(videoId)) {
            log.error("【视频修改】输入参数不完整，videoUpdateVO: {}",videoId);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        // 查询视频是否存在
        Videos video = videoService.findVideoInfoByVideoId(videoUpdateVO.getVideoId());
        if(video == null) {
            log.error("【视频修改】视频不存在，videoId: {}",videoId);
            return ResultVOUtil.error(ResultEnum.VIDEO_IS_NOT_EXIST.getCode(),ResultEnum.VIDEO_IS_NOT_EXIST.getMessage());
        }

        // 更新阿里云上的信息
        VideoPlayUtil.updateVideoInfo(videoUpdateVO);

        // 更新数据库中的信息
        BeanUtils.copyProperties(videoUpdateVO,video);
        video.setVideoDesc(videoUpdateVO.getDescription());

        videoService.updateVideo(video);


        return ResultVOUtil.success();
    }


    @ApiOperation(value = "删除普通视频",notes = "/commonVideo/delete")
    @DeleteMapping("/delete")
    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO delete(@RequestBody DeleteVideoVO deleteVideoVO) {

        String videoId = deleteVideoVO.getVideoId();
        if(StringUtils.isEmpty(videoId)) {
            log.error("【视频删除】输入参数不完整，deleteVideoVO: {}",videoId);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        Videos video = videoService.findVideoInfoByVideoId(videoId);

        if(video == null) {
            log.error("【视频删除】视频不存在，videoId: {}",videoId);
            return ResultVOUtil.error(ResultEnum.VIDEO_IS_NOT_EXIST.getCode(),ResultEnum.VIDEO_IS_NOT_EXIST.getMessage());
        }

        video.setStatus(VideoStatusEnum.DELETE.getCode());
        videoService.updateVideo(video);

        return ResultVOUtil.success();

    }
}
