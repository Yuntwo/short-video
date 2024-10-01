package com.irdc.shortvideo.controller.comment;

import com.irdc.shortvideo.VO.*;
import com.irdc.shortvideo.annotation.UserAuthentication;
import com.irdc.shortvideo.enums.AuthAopEnum;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.service.CommentsService;
import com.irdc.shortvideo.service.LikeService;
import com.irdc.shortvideo.utils.RedisUtil;
import com.irdc.shortvideo.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.List;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/9 15:47
 * Description：评论接口
 */

@Api(value = "评论接口", tags = {"评论"})
@RestController
@RequestMapping("/comment")
@Slf4j
public class CommentController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private LikeService likeService;

    @ApiOperation(value = "评论",notes = "/comment/create")
    @PostMapping("/create")
    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO comments(@Validated @RequestBody CommentVO commentVO, HttpServletRequest request) throws IOException, EncodeException {

        // 1. 前端参数完整性检验
        if (StringUtils.isEmpty(commentVO.getVideoId()) || StringUtils.isEmpty(commentVO.getContent())) {
            log.error("【评论】传入的参数不正确，commentVO: {}",commentVO);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        //2. 写入数据库
        String userId = redisUtil.getUserIdByToken(request);

        boolean res = commentsService.createComments(commentVO, userId);
        if(!res) {
            log.error("【评论】评论失败，res: {}",res);
            return ResultVOUtil.error(ResultEnum.COMMENT_FAIL.getCode(),ResultEnum.COMMENT_FAIL.getMessage());
        }

        return ResultVOUtil.success();
    }

    @ApiOperation(value = "获得评论", notes = "/comment/get")
    @PostMapping("/get")
    @UserAuthentication(pass = true, role = AuthAopEnum.USER)
    public ResultVO getComments(@RequestBody GetCommentVO getCommentVO, HttpServletRequest request){

        String videoId = getCommentVO.getVideoId();
        if(StringUtils.isEmpty(videoId)) {
            log.error("【获取视频评论】没有输入视频id，videoId: {}",videoId);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        String userId = redisUtil.getUserIdByToken(request);
        List<CommentWithUserVO> result = commentsService.queryCommentsByVideoId(videoId, userId);
        if(CollectionUtils.isEmpty(result)) {

            return ResultVOUtil.error(ResultEnum.COMMENT_IS_EMPTY.getCode(),ResultEnum.COMMENT_IS_EMPTY.getMessage());
        }

        result.parallelStream().forEach((c) -> {
            if(StringUtils.isEmpty(c.getLikeStatus())) {
                c.setLikeStatus("false");
            }else{
                c.setLikeStatus("true");
            }
        });

        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "点赞评论", notes = "/comment/like")
    @PostMapping("/like")
    @UserAuthentication(pass = true, role = AuthAopEnum.USER)
    public ResultVO likeComments(@RequestBody LikeCommentVO likeCommentVO, HttpServletRequest request) throws IOException, EncodeException {


        // 1. 前端参数完整性检验
        if (likeCommentVO.getCommentId().isEmpty()){
            log.error("【点赞评论】传入的参数不正确，starVO: {}", likeCommentVO);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        // 2. 查询是否存在
        String userId = redisUtil.getUserIdByToken(request);

        // 3. 修改数据库
        likeService.likeComment(likeCommentVO, userId);

        return ResultVOUtil.success();
    }

    @ApiOperation(value = "取消点赞评论", notes = "/comment/unlike")
    @PostMapping("/unlike")
    @UserAuthentication(pass = true, role = AuthAopEnum.USER)
    public ResultVO unlikeComments(@RequestBody LikeCommentVO likeCommentVO, HttpServletRequest request){

        // 1. 前端参数完整性检验
        if (likeCommentVO.getCommentId().isEmpty()){
            log.error("【点赞评论】传入的参数不正确，starVO: {}", likeCommentVO);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        // 2. 查询是否存在
        String userId = redisUtil.getUserIdByToken(request);

        // 3. 修改数据库
        likeService.unlikeComment(likeCommentVO, userId);

        return ResultVOUtil.success();
    }
}

