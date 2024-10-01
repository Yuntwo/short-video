package com.irdc.shortvideo.controller.commonVideo;

import com.irdc.shortvideo.VO.*;
import com.irdc.shortvideo.annotation.UserAuthentication;
import com.irdc.shortvideo.constant.VideoConstant;
import com.irdc.shortvideo.enums.AuthAopEnum;
import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.enums.VideoTypeEnum;
import com.irdc.shortvideo.service.LikeService;
import com.irdc.shortvideo.service.VideoService;
import com.irdc.shortvideo.utils.RedisUtil;
import com.irdc.shortvideo.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

/**
 * @author yangshu
 */

@Api(value = "获得普通视频播放列表接口", tags = {"获得视频播放列表"})
@RestController
@RequestMapping("/commonVideo")
@Slf4j
public class CommonVideoListController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private LikeService likeService;

    @ApiOperation(value = "通过videoId获取普通视频", notes = "/commonVideo/getVideoByVideoId")
    @PostMapping("/getVideoByVideoId")
//    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO getVideoByVideoId(@RequestBody GetVideoByVideoIdVO getVideoByVideoIdVO) {
        // 判断是否传入了videoId
        String videoId = getVideoByVideoIdVO.getVideoId();
        if(StringUtils.isEmpty(videoId)) {
            log.error("【视频播放】输入参数不完整，videoUpdateVO: {}",videoId);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        // 查询视频是否存在
        VideoWithUserVO video = videoService.queryVideoByVideoId(videoId);
        if(video == null) {
            log.error("【视频获取】视频不存在，videoId: {}",videoId);
            return ResultVOUtil.error(ResultEnum.VIDEO_IS_NOT_EXIST.getCode(),ResultEnum.VIDEO_IS_NOT_EXIST.getMessage());
        }

        video.setLikeNum(likeService.queryLikeNumByVideoId(getVideoByVideoIdVO.getVideoId(), VideoTypeEnum.COMMON_VIDEO.getCode()));

        return ResultVOUtil.success(video);
    }


    @ApiOperation(value = "获得推荐普通视频列表", notes = "/commonVideo/getRecommendVideo")
    @PostMapping("/getRecommendVideo")
    public ResultVO getRecommendVideo(@RequestBody GetRecommendVideoVO getVideoByVideoIdVO, HttpServletRequest request) {
        Integer page = StringUtils.isEmpty(getVideoByVideoIdVO.getPage()) ? 1 : getVideoByVideoIdVO.getPage();
        Integer size = StringUtils.isEmpty(getVideoByVideoIdVO.getSize()) ? VideoConstant.PAGE_SIZE : getVideoByVideoIdVO.getSize();

        String userId = redisUtil.getUserIdByToken(request);
        PagedResultVO result;
        if (!StringUtils.isEmpty(userId)) {
            result = videoService.queryRecommendVideos(page, size, userId);
        } else {
            result = videoService.queryRecommendVideos(page, size);
        }

        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "获得某标签普通视频列表", notes = "/commonVideo/getTagVideo")
    @PostMapping("/getTagVideo")
    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO getTagVideo(@RequestBody GetTagVideoVO getTagVideoVO) {
        String tag = getTagVideoVO.getTag();
        if(StringUtils.isEmpty(tag)) {
            log.error("【视频播放】没有输入视频标签，getTagVideoVO: {}",tag);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }
        Integer page = StringUtils.isEmpty(getTagVideoVO.getPage()) ? 1 : getTagVideoVO.getPage();
        Integer size = StringUtils.isEmpty(getTagVideoVO.getSize()) ? VideoConstant.PAGE_SIZE : getTagVideoVO.getSize();

        PagedResultVO result = videoService.queryVideosByTag(tag, page, size);

        return ResultVOUtil.success(result);
    }

    //todo:video包下已经有了，功能完善后就删除
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


    //todo:目前只做普通视频的
    @ApiOperation(value = "获得10大普通视频列表",notes = "/commonVideo/hottest10Video")
    @GetMapping("/hottest10Video")
    public ResultVO hottest10Video(){
//        List<VideoWithUserVO> result = videoService.queryHottest10Video();
//        for(VideoWithUserVO v : result){
//            v.setLikeNum(likeService.queryLikeNumByVideoId(v.getVideoId(), VideoTypeEnum.COMMON_VIDEO.getCode()));
//        }
//
//        result.sort(Comparator.comparing(VideoWithUserVO::getLikeNum, (x,y) -> y.compareTo(x)));
//
//        return ResultVOUtil.success(result);

        //暂时在该接口下实现获得点赞数最高的50个标签为短视频大赛的视频
        List<VideoWithUserVO> result = videoService.mostLikeNum();

        for(VideoWithUserVO v : result){
            v.setLikeNum(likeService.queryLikeNumByVideoId(v.getVideoId(), VideoTypeEnum.COMMON_VIDEO.getCode()));
        }

        result.sort(Comparator.comparing(VideoWithUserVO::getLikeNum, (x,y) -> y.compareTo(x)));

        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "获得点赞数最高的50个标签为短视频大赛的视频",notes = "/commonVideo/mostLikeNum")
    @GetMapping("/mostLikeNum")
    public ResultVO mostLikeNum(){
        List<VideoWithUserVO> result = videoService.mostLikeNum();

        for(VideoWithUserVO v : result){
            v.setLikeNum(likeService.queryLikeNumByVideoId(v.getVideoId(), VideoTypeEnum.COMMON_VIDEO.getCode()));
        }

        result.sort(Comparator.comparing(VideoWithUserVO::getLikeNum, (x,y) -> y.compareTo(x)));

        return ResultVOUtil.success(result);
    }
}


