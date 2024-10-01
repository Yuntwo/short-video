package com.irdc.shortvideo.controller.commonVideo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.VO.VideoUpdateVO;
import com.irdc.shortvideo.annotation.UserAuthentication;
import com.irdc.shortvideo.entity.Videos;
import com.irdc.shortvideo.enums.AuthAopEnum;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.enums.VideoStatusEnum;
import com.irdc.shortvideo.service.TagService;
import com.irdc.shortvideo.service.VideoService;
import com.irdc.shortvideo.utils.FastDfsUtil;
import com.irdc.shortvideo.utils.RedisUtil;
import com.irdc.shortvideo.utils.ResultVOUtil;
import com.irdc.shortvideo.utils.aliyun.vod.VideoPlayUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/9 15:53
 * Description：上传视频接口
 */

@Api(value = "上传普通视频接口", tags = {"上传普通视频"})
@RestController
@RequestMapping("/commonVideo")
@Slf4j
public class CommonVideoUploadController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TagService tagService;

    @Autowired
    private FastDfsUtil fastDfsUtil;



    @ApiOperation(value = "获取上传视频的凭证",notes = "/commonVideo/getVideoUploadAuth")
    @PostMapping("/getVideoUploadAuth")
    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO getVideoUploadAuth(@Validated @RequestBody VideoUpdateVO videoUpdateVO, HttpServletRequest request) throws Exception {
        if(StringUtils.isEmpty(videoUpdateVO.getTitle()) || StringUtils.isEmpty(videoUpdateVO.getTag()) || org.springframework.util.StringUtils.isEmpty((videoUpdateVO.getIsPublic()))) {
            log.error("【获取视频上传凭证错误】输入参数不完整，videoUpdateVO: {}",videoUpdateVO.getTitle() + "," + videoUpdateVO.getTag() + "," + videoUpdateVO.getIsPublic());
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }
        // TODO: 2020/10/7 使用消息队列？？？？
        CreateUploadVideoResponse response = VideoPlayUtil.createUploadVideo(videoUpdateVO);

        HashMap<String,String> map = new HashMap<>();
        map.put("VideoId",response.getVideoId());
        map.put("UploadAddress",response.getUploadAddress());
        map.put("UploadAuth",response.getUploadAuth());

        Videos video = new Videos();
        BeanUtils.copyProperties(videoUpdateVO,video);
        video.setVideoId(response.getVideoId());
        video.setUserId(redisUtil.getUserIdByToken(request));
        video.setVideoDesc(videoUpdateVO.getDescription());
        video.setStatus(VideoStatusEnum.UPLOADING.getCode());

        videoService.createVideo(video);

        return ResultVOUtil.success(map);
    }

    @ApiOperation(value = "上传普通视频",notes = "/commonVideo/upload")
    @ApiImplicitParams({
            @ApiImplicitParam(name="videoId", value="视频主键id", required=true,
                    dataType="String", paramType="form")
    })
    @PostMapping("/upload")
    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO upload(String videoId,
                           @ApiParam(value="视频封面", required=true) MultipartFile coverFile) throws Exception {

        if(StringUtils.isEmpty(videoId)) {
            log.error("【视频上传】输入参数不完整，videoUpdateVO: {}",videoId + "+" + coverFile);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        // 查询视频是否存在
        Videos video = videoService.findVideoInfoByVideoId(videoId);

        if(video == null) {
            log.error("【视频上传】视频不存在，videoId: {}",videoId);
            return ResultVOUtil.error(ResultEnum.VIDEO_IS_NOT_EXIST.getCode(),ResultEnum.VIDEO_IS_NOT_EXIST.getMessage());
        }

        try {
            String path = fastDfsUtil.uploadFile(coverFile);
            if (!org.springframework.util.StringUtils.isEmpty(coverFile)) {
                video.setVideoCoverPath(path);
            } else {
                return ResultVOUtil.error(ResultEnum.UPDATE_VIDEO_COVER_ERROR.getCode(),ResultEnum.UPDATE_VIDEO_COVER_ERROR.getMessage());
            }
        } catch (Exception e) {
            log.error("【上传视频封面】服务异常，Exception: {}", e.getMessage());
            return ResultVOUtil.error(ResultEnum.UPDATE_VIDEO_COVER_ERROR.getCode(),ResultEnum.UPDATE_VIDEO_COVER_ERROR.getMessage());
        }
        // 更新视频信息

        // video.setStatus(VideoStatusEnum.SUCCESS.getCode());

        videoService.updateVideo(video);

        return ResultVOUtil.successMsg(ResultEnum.UPLOAD_VIDEO_SUCCESS.getMessage());
    }

}
