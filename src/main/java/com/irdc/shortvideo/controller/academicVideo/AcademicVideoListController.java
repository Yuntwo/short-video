package com.irdc.shortvideo.controller.academicVideo;

import com.irdc.shortvideo.VO.PagedResultVO;
import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.VO.video.*;
import com.irdc.shortvideo.annotation.UserAuthentication;
import com.irdc.shortvideo.constant.VideoConstant;
import com.irdc.shortvideo.enums.AuthAopEnum;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.enums.VideoTypeEnum;
import com.irdc.shortvideo.service.AcademicVideosPictureService;
import com.irdc.shortvideo.service.AcademicVideosService;
import com.irdc.shortvideo.service.LikeService;
import com.irdc.shortvideo.service.PaperService;
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

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/27 9:48
 * Description：
 */

@Api(value = "获得学术视频播放列表接口", tags = {"获得学术视频播放列表"})
@RestController
@RequestMapping("/academicVideo")
@Slf4j
public class AcademicVideoListController {
    @Autowired
    private AcademicVideosService academicVideosService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private PaperService paperService;

    @Autowired
    private AcademicVideosPictureService academicVideosPictureService;

    @ApiOperation(value = "通过videoId获取视频", notes = "/academicVideo/getVideoByVideoId")
    @PostMapping("/getVideoByVideoId")
//    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO getAcademicVideoByVideoId(@RequestBody VideoIdVO videoIdVO) {
        // 判断是否传入了videoId
        String videoId = videoIdVO.getVideoId();
        if(StringUtils.isEmpty(videoId)) {
            log.error("【视频播放】输入参数不完整，videoUpdateVO: {}",videoId);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        // 查询视频是否存在
        AcademicVideoWithUserVO academicVideoWithUserVO = academicVideosService.queryAcademicVideoWithUserVOByVideoId(videoId);
        if(academicVideoWithUserVO == null) {
            log.error("【视频获取】视频不存在，videoId: {}",videoId);
            return ResultVOUtil.error(ResultEnum.VIDEO_IS_NOT_EXIST.getCode(),ResultEnum.VIDEO_IS_NOT_EXIST.getMessage());
        }
        academicVideoWithUserVO.setLikeNum(likeService.queryLikeNumByVideoId(videoId, VideoTypeEnum.ACADEMIC_VIDEO.getCode()));

        return ResultVOUtil.success(academicVideoWithUserVO);
    }

    @ApiOperation(value = "获得推荐学术视频列表", notes = "/academicVideo/getRecommendVideo")
    @PostMapping("/getRecommendVideo")
    public ResultVO getRecommendVideo(@RequestBody VideoPageAndSizeVO videoPageAndSizeVO) {
        Integer page = StringUtils.isEmpty(videoPageAndSizeVO.getPage()) ? 1 : videoPageAndSizeVO.getPage();
        Integer size = StringUtils.isEmpty(videoPageAndSizeVO.getSize()) ? VideoConstant.PAGE_SIZE : videoPageAndSizeVO.getSize();

        PagedResultVO result = academicVideosService.queryRecommendAcademicVideoWithUserVO(page, size);

        if(result == null){
            return ResultVOUtil.error(ResultEnum.GET_ACADEMIC_VIDEO_RECOMMEND_LIST_FAILED.getCode(), ResultEnum.GET_ACADEMIC_VIDEO_RECOMMEND_LIST_FAILED.getMessage());
        }

//        System.out.println("result: " + result);
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "通过videoId获取学术视频论文", notes = "/academicVideo/getPaperByVideoId")
    @PostMapping("/getPaperByVideoId")
//    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO getAcademicPaperByVideoId(@RequestBody VideoIdVO videoIdVO) {
        // 判断是否传入了videoId
        String videoId = videoIdVO.getVideoId();
        if(StringUtils.isEmpty(videoId)) {
            log.error("【学术视频文章获取】输入参数不完整，videoId: {}",videoId);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        // 查询视频是否存在
        AcademicPaperInfoVO academicPaperInfoVO = paperService.queryPaperAndAuthor(videoId);

        if(academicPaperInfoVO == null) {
            log.error("【学术视频文章获取】文章不存在，videoId: {}",videoId);
            return ResultVOUtil.error(ResultEnum.VIDEO_IS_NOT_EXIST.getCode(),ResultEnum.VIDEO_IS_NOT_EXIST.getMessage());
        }

        return ResultVOUtil.success(academicPaperInfoVO);
    }

    @ApiOperation(value = "通过videoId获取学术视频图片", notes = "/academicVideo/getPictureByVideoId")
    @PostMapping("/getPictureByVideoId")
//    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO getPictureByVideoId(@RequestBody VideoIdVO videoIdVO) {
        // 判断是否传入了videoId
        String videoId = videoIdVO.getVideoId();
        if(StringUtils.isEmpty(videoId)) {
            log.error("【学术视频图片获取】输入参数不完整，videoId: {}",videoId);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        // 查询视频是否存在
        List<AcademicPictureInfoVO> academicPictureInfoVOList = academicVideosPictureService.queryAcademicPictureInfoVOByVideoId(videoId);

        if(academicPictureInfoVOList == null) {
            log.error("【学术视频图片获取】图片不存在，videoId: {}",videoId);
            return ResultVOUtil.error(ResultEnum.VIDEO_IS_NOT_EXIST.getCode(),ResultEnum.VIDEO_IS_NOT_EXIST.getMessage());
        }

        return ResultVOUtil.success(academicPictureInfoVOList);
    }


//    @ApiOperation(value = "通过videoId获取视频", notes = "/academicVideo/getVideoByVideoId")
//    @PostMapping("/getVideoByVideoId")
//    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
//    public ResultVO getAcademicVideoByVideoId(@RequestBody VideoIdVO videoIdVO) {
//        // 判断是否传入了videoId
//        String videoId = videoIdVO.getVideoId();
//        if(StringUtils.isEmpty(videoId)) {
//            log.error("【视频播放】输入参数不完整，videoUpdateVO: {}",videoId);
//            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
//        }
//
//        // 查询视频是否存在
//        AcademicVideoVO video = academicVideosService.queryAcademicVideoVOById(videoId);
//        if(video == null) {
//            log.error("【视频获取】视频不存在，videoId: {}",videoId);
//            return ResultVOUtil.error(ResultEnum.VIDEO_IS_NOT_EXIST.getCode(),ResultEnum.VIDEO_IS_NOT_EXIST.getMessage());
//        }
//        video.setLikeNum(likeService.queryLikeNumByVideoId(videoId, VideoTypeEnum.ACADEMIC_VIDEO.getCode()));
//
//        return ResultVOUtil.success(video);
//    }

//    @ApiOperation(value = "获得推荐学术视频列表", notes = "/academicVideo/getRecommendVideo")
//    @PostMapping("/getRecommendVideo")
//    public ResultVO getRecommendVideo(@RequestBody VideoPageAndSizeVO videoPageAndSizeVO) {
//        Integer page = StringUtils.isEmpty(videoPageAndSizeVO.getPage()) ? 1 : videoPageAndSizeVO.getPage();
//        Integer size = StringUtils.isEmpty(videoPageAndSizeVO.getSize()) ? VideoConstant.PAGE_SIZE : videoPageAndSizeVO.getSize();
//
//        PagedResultVO result = academicVideosService.queryRecommendVideos(page,size);
//
//        if(result == null){
//            return ResultVOUtil.error(ResultEnum.GET_ACADEMIC_VIDEO_RECOMMEND_LIST_FAILED.getCode(), ResultEnum.GET_ACADEMIC_VIDEO_RECOMMEND_LIST_FAILED.getMessage());
//        }
//
////        System.out.println("result: " + result);
//        return ResultVOUtil.success(result);
//    }

}
