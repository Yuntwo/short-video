package com.irdc.shortvideo.controller.remind;

import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.VO.remind.CommentLikeRemindVO;
import com.irdc.shortvideo.VO.remind.VideoCommentRemindVO;
import com.irdc.shortvideo.VO.remind.VideoLikeRemindVO;
import com.irdc.shortvideo.annotation.UserAuthentication;
import com.irdc.shortvideo.enums.AuthAopEnum;
import com.irdc.shortvideo.service.RemindService;
import com.irdc.shortvideo.utils.RedisUtil;
import com.irdc.shortvideo.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/8 14:12
 * Description：
 */
@Api(value = "提醒接口",tags = {"提醒"})
@RestController
@RequestMapping("/remind")
@Slf4j
public class RemindController {

    @Autowired
    private RemindService remindService;

    @Autowired
    private RedisUtil redisUtil;

    @UserAuthentication(pass = true, role = AuthAopEnum.USER)
    @ApiOperation(value = "获取视频点赞提醒",notes = "/remind/getVideoLikeRemind")
    @PostMapping("/getVideoLikeRemind")
    public ResultVO getVideoLikeRemind(HttpServletRequest request) {
        String userId = redisUtil.getUserIdByToken(request);

        List<VideoLikeRemindVO> result = remindService.getVideoLikeRemind(userId);


        return ResultVOUtil.success(result);
    }

    @UserAuthentication(pass = true, role = AuthAopEnum.USER)
    @ApiOperation(value = "获取视频评论提醒",notes = "/remind/getVideoCommentRemind")
    @PostMapping("/getVideoCommentRemind")
    public ResultVO getVideoCommentRemind(HttpServletRequest request){
        String userId = redisUtil.getUserIdByToken(request);

        List<VideoCommentRemindVO> result = remindService.getVideoCommentRemind(userId);


        return ResultVOUtil.success(result);
    }

    @UserAuthentication(pass = true, role = AuthAopEnum.USER)
    @ApiOperation(value = "获取评论点赞提醒",notes = "/remind/getCommentLikeRemind")
    @PostMapping("/getCommentLikeRemind")
    public ResultVO getCommentLikeRemind(HttpServletRequest request) {
        String userId = redisUtil.getUserIdByToken(request);

        List<CommentLikeRemindVO> result = remindService.getCommentLikeRemind(userId);


        return ResultVOUtil.success(result);
    }
}
