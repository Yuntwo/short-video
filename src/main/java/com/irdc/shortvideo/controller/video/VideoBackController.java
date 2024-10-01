package com.irdc.shortvideo.controller.video;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.entity.AcademicVideos;
import com.irdc.shortvideo.entity.Videos;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.enums.VideoStatusEnum;
import com.irdc.shortvideo.service.AcademicVideosService;
import com.irdc.shortvideo.service.VideoService;
import com.irdc.shortvideo.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/29 22:15
 * Description：
 */

@Api(value = "视频回调接口", tags = {"视频回调"})
@RestController
@RequestMapping("/video")
@Slf4j
public class VideoBackController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private AcademicVideosService academicVideosService;

    @Value("${aliyun.httpBack.callbackUrl}")
    private String callbackUrl;

    @Value("${aliyun.httpBack.privateKey}")
    private String privateKey;

    @ApiOperation(value = "视频回调接口",notes = "/video/httpBack")
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
                Videos video = videoService.findVideoInfoByVideoId(videoId);

                AcademicVideos academicVideos = academicVideosService.queryAcademicVideoByVideoId(videoId);


                if (video == null && academicVideos == null) {
                    log.error("【视频上传】视频不存在，videoId: {}", videoId);
                    return ResultVOUtil.error(ResultEnum.VIDEO_IS_NOT_EXIST.getCode(), ResultEnum.VIDEO_IS_NOT_EXIST.getMessage());
                }

//                if(video != null) {
//                    video.setStatus(VideoStatusEnum.SUCCESS.getCode());
//                    videoService.updateVideo(video);
//                }
//
//                if(academicVideos != null){
//                    academicVideos.setStatus(VideoStatusEnum.SUCCESS.getCode());
//                    academicVideosService.updateAcademicVideo(academicVideos);
//                }

            }
        }

        return ResultVOUtil.success();
    }
}
