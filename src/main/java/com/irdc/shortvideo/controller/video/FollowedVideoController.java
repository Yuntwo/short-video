package com.irdc.shortvideo.controller.video;

import com.irdc.shortvideo.VO.PagedResultVO;
import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.VO.video.GetFollowedVideoVO;
import com.irdc.shortvideo.annotation.UserAuthentication;
import com.irdc.shortvideo.constant.VideoConstant;
import com.irdc.shortvideo.enums.AuthAopEnum;
import com.irdc.shortvideo.service.VideoService;
import com.irdc.shortvideo.utils.RedisUtil;
import com.irdc.shortvideo.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/28 21:59
 * Description：
 */

@Api(value = "获得点赞/关注视频接口", tags = {"获得点赞/关注视频"})
@RestController
@RequestMapping("/video")
@Slf4j
public class FollowedVideoController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "获得关注视频列表", notes = "/video/getFollowedVideo")
    @PostMapping("/getFollowedVideo")
    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO getFollowedVideo(@RequestBody GetFollowedVideoVO getFollowedVideoVO, HttpServletRequest request) {
        Integer page = StringUtils.isEmpty(getFollowedVideoVO.getPage()) ? 1 : getFollowedVideoVO.getPage();
        Integer size = StringUtils.isEmpty(getFollowedVideoVO.getSize()) ? VideoConstant.PAGE_SIZE : getFollowedVideoVO.getSize();
        String userId = redisUtil.getUserIdByToken(request);

        PagedResultVO result = videoService.queryUserFollowVideosByUserId(userId, page, size);

        return ResultVOUtil.success(result);
    }
}
