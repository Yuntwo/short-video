package com.irdc.shortvideo.controller.academicVideo;

import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.VO.VideoUpdateVO;
import com.irdc.shortvideo.VO.video.*;
import com.irdc.shortvideo.annotation.UserAuthentication;
import com.irdc.shortvideo.entity.AcademicVideos;
import com.irdc.shortvideo.enums.AuthAopEnum;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.enums.VideoStatusEnum;
import com.irdc.shortvideo.service.AcademicVideosPictureService;
import com.irdc.shortvideo.service.AcademicVideosService;
import com.irdc.shortvideo.service.FileService;
import com.irdc.shortvideo.service.PaperService;
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
 * Package short-video
 *
 * @author chenliquan on 2020/11/30 14:41
 * Description：
 */
@Api(value = "学术视频修改接口", tags = {"学术视频修改"})
@RestController
@RequestMapping("/academicVideo")
@Slf4j
public class AcademicVideoModifyController {

    @Autowired
    private AcademicVideosService academicVideosService;

    @Autowired
    private AcademicVideosPictureService academicVideosPictureService;

    @Autowired
    private FileService fileService;

    @Autowired
    private PaperService paperService;


    @ApiOperation(value = "编辑学术视频",notes = "/academicVideo/modifyAcademicVideo")
    @PostMapping("/modifyAcademicVideo")
    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO modifyAcademicVideo(@Validated @RequestBody AcademicVideoUpdateVO academicVideoUpdateVO) throws Exception {

        String videoId = academicVideoUpdateVO.getVideoId();
        if(StringUtils.isEmpty(videoId)) {
            log.error("【视频修改】输入参数不完整，videoUpdateVO: {}",videoId);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        // 查询视频是否存在
        AcademicVideos academicVideos = academicVideosService.queryAcademicVideoByStatus(videoId, VideoStatusEnum.SUCCESS.getCode());
        if(academicVideos == null) {
            log.error("【视频修改】视频不存在，videoId: {}",videoId);
            return ResultVOUtil.error(ResultEnum.VIDEO_IS_NOT_EXIST.getCode(),ResultEnum.VIDEO_IS_NOT_EXIST.getMessage());
        }

        // 更新阿里云上的信息  本地测不了
        VideoUpdateVO videoUpdateVO = new VideoUpdateVO();
        BeanUtils.copyProperties(academicVideoUpdateVO, videoUpdateVO);
        VideoPlayUtil.updateVideoInfo(videoUpdateVO);

        BeanUtils.copyProperties(academicVideoUpdateVO, academicVideos);
        academicVideos.setVideoDesc(academicVideoUpdateVO.getDescription());
        // 更新数据库中的信息
        academicVideosService.updateAcademicVideo(academicVideos);

        return ResultVOUtil.success();

    }


    @ApiOperation(value = "删除学术视频",notes = "/academicVideo/deleteAcademicVideo")
    @DeleteMapping("/deleteAcademicVideo")
    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO deleteAcademicVideo(@RequestBody VideoIdVO videoIdVO) {

        String videoId = videoIdVO.getVideoId();
        if(StringUtils.isEmpty(videoId)) {
            log.error("【视频删除】输入参数不完整，deleteVideoVO: {}",videoId);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        AcademicVideos academicVideos = academicVideosService.queryAcademicVideoByStatus(videoId, VideoStatusEnum.SUCCESS.getCode());


        if(academicVideos == null) {
            log.error("【视频删除】视频不存在，videoId: {}",videoId);
            return ResultVOUtil.error(ResultEnum.VIDEO_IS_NOT_EXIST.getCode(),ResultEnum.VIDEO_IS_NOT_EXIST.getMessage());
        }

        academicVideosService.delAcademicVideos(academicVideos);

        return ResultVOUtil.success();

    }


    @ApiOperation(value = "编辑学术视频论文",notes = "/academicVideo/modifyPaper")
    @PostMapping("/modifyAcademicPaper")
    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO modifyPaper(@Validated @RequestBody AcademicPaperUpdateVO academicPaperUpdateVO) throws Exception {

        String videoId = academicPaperUpdateVO.getVideoId();
        if(StringUtils.isEmpty(videoId)) {
            log.error("【学术视频论文修改】输入参数不完整，videoUpdateVO: {}",videoId);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        // 查询视频是否存在
        AcademicVideos academicVideos = academicVideosService.queryAcademicVideoByStatus(videoId, VideoStatusEnum.SUCCESS.getCode());
        if(academicVideos == null) {
            log.error("【学术视频论文修改】视频不存在，videoId: {}",videoId);
            return ResultVOUtil.error(ResultEnum.VIDEO_IS_NOT_EXIST.getCode(),ResultEnum.VIDEO_IS_NOT_EXIST.getMessage());
        }


        paperService.updatePaperAndAuthor(academicPaperUpdateVO);


        return ResultVOUtil.success();

    }

}
