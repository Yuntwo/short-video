package com.irdc.shortvideo.controller.advertisementVideo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.VO.VideoUpdateVO;
import com.irdc.shortvideo.annotation.UserAuthentication;
import com.irdc.shortvideo.entity.AdvertisementVideos;
import com.irdc.shortvideo.enums.AuthAopEnum;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.enums.VideoStatusEnum;
import com.irdc.shortvideo.service.AdvertisementVideosService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/9 15:53
 * Description：上传广告视频接口
 */

@Api(value = "上传广告视频接口", tags = {"上传广告视频"})
@RestController
@Slf4j
public class UploadAdvertisementVideoController {

    @Autowired
    private AdvertisementVideosService advertisementVideosService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private FastDfsUtil fastDfsUtil;

    @Value("${aliyun.httpBack.callbackUrl}")
    private String callbackUrl;

    @Value("${aliyun.httpBack.privateKey}")
    private String privateKey;


    @ApiOperation(value = "获取上传视频的凭证",notes = "/advertisementVideo/getVideoUploadAuth")
    @PostMapping("/advertisementVideo/getVideoUploadAuth")
    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO getVideoUploadAuth(@RequestBody VideoUpdateVO videoUpdateVO, HttpServletRequest request) throws Exception {
        if(StringUtils.isEmpty(videoUpdateVO.getTitle()) || StringUtils.isEmpty(videoUpdateVO.getTag()) || org.springframework.util.StringUtils.isEmpty((videoUpdateVO.getIsPublic()))) {
            log.error("【获取广告视频上传凭证错误】输入参数不完整，videoUpdateVO: {}",videoUpdateVO.getTitle() + "," + videoUpdateVO.getTag() + "," + videoUpdateVO.getIsPublic());
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }
        // TODO: 2020/10/7 使用消息队列？？？？
        CreateUploadVideoResponse response = VideoPlayUtil.createUploadVideo(videoUpdateVO);

        HashMap<String,String> map = new HashMap<>();
        map.put("VideoId",response.getVideoId());
        map.put("UploadAddress",response.getUploadAddress());
        map.put("UploadAuth",response.getUploadAuth());

        AdvertisementVideos video = new AdvertisementVideos();
        BeanUtils.copyProperties(videoUpdateVO,video);
        video.setVideoId(response.getVideoId());
        video.setUserId(redisUtil.getUserIdByToken(request));
        video.setVideoDesc(videoUpdateVO.getDescription());

        advertisementVideosService.createAdvertisementVideos(video);

        return ResultVOUtil.success(map);
    }

    @ApiOperation(value = "上传视频",notes = "/advertisementVideo/upload")
    @ApiImplicitParams({
            @ApiImplicitParam(name="videoId", value="视频主键id", required=true,
                    dataType="String", paramType="form")
    })
    @PostMapping("/advertisementVideo/upload")
    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO upload(String videoId,
                           @ApiParam(value="视频封面", required=true) MultipartFile coverFile) throws Exception {

        if(StringUtils.isEmpty(videoId)) {
            log.error("【视频上传】输入参数不完整，videoUpdateVO: {}",videoId + "+" + coverFile);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        // 查询视频是否存在
        AdvertisementVideos video = advertisementVideosService.findByVideoId(videoId);
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

        advertisementVideosService.updateVideo(video);

        return ResultVOUtil.successMsg(ResultEnum.UPLOAD_VIDEO_SUCCESS.getMessage());
    }

    @ApiOperation(value = "视频回调接口",notes = "/advertisementVideo/httpBack")
    @PostMapping("/httpBack")
    public ResultVO httpBack(@RequestBody String body, HttpServletRequest request) {

        // UNIX时间戳，整形正数
        String vodTimestamp = request.getHeader("X-VOD-TIMESTAMP");
        //签名字符串，为32位MD5值
        String vodSignature = request.getHeader("X-VOD-SIGNATURE");

        String sign = DigestUtils.md5Hex(callbackUrl + "|" + vodTimestamp + "|" + privateKey);

        if (!sign.equalsIgnoreCase(vodSignature)) {
            log.error("【阿里云视频回调】服务异常，Exception: {}", vodSignature);
            return ResultVOUtil.error(ResultEnum.ALIYUN_CALL_BACK_ERROR.getCode(),ResultEnum.ALIYUN_CALL_BACK_ERROR.getMessage());
        }

        System.out.println("视频回调：" + body);
        if (StringUtils.isNotBlank(body)) {
            JSONObject jsonObject = JSON.parseObject(body);

            if (jsonObject.getString("EventType").equalsIgnoreCase("TranscodeComplete")
                    && jsonObject.getString("Status").equalsIgnoreCase("success")
            ) {
                String videoId = jsonObject.getString("VideoId");

                // 查询视频是否存在
                AdvertisementVideos video = advertisementVideosService.findByVideoId(videoId);
                if (video == null) {
                    log.error("【视频上传】视频不存在，videoId: {}", videoId);
                    return ResultVOUtil.error(ResultEnum.VIDEO_IS_NOT_EXIST.getCode(), ResultEnum.VIDEO_IS_NOT_EXIST.getMessage());
                }

                video.setStatus(VideoStatusEnum.SUCCESS.getCode());
                advertisementVideosService.updateVideo(video);
            }
        }

        return ResultVOUtil.success();
    }
}
