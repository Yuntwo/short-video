package com.irdc.shortvideo.controller.academicVideo;

import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.VO.video.AcademicPaperUploadVO;
import com.irdc.shortvideo.VO.video.AcademicPictureUploadVO;
import com.irdc.shortvideo.VO.video.AcademicVideoUploadVO;
import com.irdc.shortvideo.VO.video.VideoIdVO;
import com.irdc.shortvideo.annotation.UserAuthentication;
import com.irdc.shortvideo.entity.AcademicVideos;
import com.irdc.shortvideo.enums.AuthAopEnum;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.enums.VideoStatusEnum;
import com.irdc.shortvideo.service.AcademicVideosPictureService;
import com.irdc.shortvideo.service.AcademicVideosService;
import com.irdc.shortvideo.service.FileService;
import com.irdc.shortvideo.service.PaperService;
import com.irdc.shortvideo.utils.FastDfsUtil;
import com.irdc.shortvideo.utils.RedisUtil;
import com.irdc.shortvideo.utils.ResultVOUtil;
import com.irdc.shortvideo.utils.aliyun.vod.VideoPlayUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;
import java.util.HashMap;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/12 15:57
 * Description：
 */

@Api(value = "学术视频上传接口", tags = {"学术视频上传"})
@RestController
@RequestMapping("/academicVideo")
@Slf4j
@Validated
public class AcademicVideoUploadController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private FastDfsUtil fastDfsUtil;

    @Autowired
    private AcademicVideosService academicVideosService;

    @Autowired
    private AcademicVideosPictureService academicVideosPictureService;

    @Autowired
    private FileService fileService;

    @Autowired
    private PaperService paperService;



//    @ApiOperation(value = "获取上传视频的凭证",notes = "/academicVideo/getVideoUploadAuth")
//    @PostMapping("/getVideoUploadAuth")
//    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
//    public ResultVO getVideoUploadAuth(@RequestBody AcademicVideoUpdateVO academicVideoUpdateVO, HttpServletRequest request) throws Exception {
//        if(StringUtils.isEmpty(academicVideoUpdateVO.getTitle()) || StringUtils.isEmpty(academicVideoUpdateVO.getTag()) || org.springframework.util.StringUtils.isEmpty((academicVideoUpdateVO.getIsPublic()))) {
//            log.error("【获取视频上传凭证错误】输入参数不完整，videoUpdateVO: {}",academicVideoUpdateVO.getTitle() + "," + academicVideoUpdateVO.getTag() + "," + academicVideoUpdateVO.getIsPublic());
//            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
//        }
//
//        CreateUploadVideoResponse response = VideoPlayUtil.createUploadVideo(academicVideoUpdateVO);
//
//        HashMap<String,String> map = new HashMap<>();
//        String videoId = response.getVideoId();
//        map.put("VideoId",videoId);
//        map.put("UploadAddress",response.getUploadAddress());
//        map.put("UploadAuth",response.getUploadAuth() );
//
//
////        Videos video = new Videos();
////        BeanUtils.copyProperties(videoUpdateVO,video);
////        video.setVideoId(response.getVideoId());
////        video.setUserId(redisUtil.getUserIdByToken(request));
////        video.setVideoDesc(videoUpdateVO.getDescription());
//
//        academicVideoUpdateVO.setVideoId(videoId);
//
//        academicVideosService.createAcademicVideos(academicVideoUpdateVO, redisUtil.getUserIdByToken(request));
//
//        return ResultVOUtil.success(map);
//    }

    @ApiOperation(value = "获取上传视频的凭证",notes = "/academicVideo/getVideoUploadAuth")
    @PostMapping("/getVideoUploadAuth")
    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO getVideoUploadAuth(@Validated @RequestBody AcademicVideoUploadVO academicVideoUploadVO, HttpServletRequest request) throws Exception {
        if(StringUtils.isEmpty(academicVideoUploadVO.getTitle()) || StringUtils.isEmpty(academicVideoUploadVO.getTag()) || org.springframework.util.StringUtils.isEmpty((academicVideoUploadVO.getIsPublic()))) {
            log.error("【获取视频上传凭证错误】输入参数不完整，academicVideoUploadVO: {}",academicVideoUploadVO.getTitle() + "," + academicVideoUploadVO.getTag() + "," + academicVideoUploadVO.getIsPublic());
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        CreateUploadVideoResponse response = VideoPlayUtil.createUploadVideo(academicVideoUploadVO);

        HashMap<String,String> map = new HashMap<>();
        String videoId = response.getVideoId();
        map.put("VideoId",videoId);
        map.put("UploadAddress",response.getUploadAddress());
        map.put("UploadAuth",response.getUploadAuth() );


//        Videos video = new Videos();
//        BeanUtils.copyProperties(videoUpdateVO,video);
//        video.setVideoId(response.getVideoId());
//        video.setUserId(redisUtil.getUserIdByToken(request));
//        video.setVideoDesc(videoUpdateVO.getDescription());

        AcademicVideos academicVideos = new AcademicVideos();
        BeanUtils.copyProperties(academicVideoUploadVO, academicVideos);
        academicVideos.setAcademicVideoId(videoId);
        academicVideos.setUserId(redisUtil.getUserIdByToken(request));
        academicVideos.setVideoDesc(academicVideoUploadVO.getDescription());
        academicVideos.setStatus(VideoStatusEnum.UPLOADING.getCode());

        academicVideosService.createAcademicVideos(academicVideos);

        return ResultVOUtil.success(map);
    }


    @ApiOperation(value = "上传学术视频封面",notes = "/academicVideo/upload")
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
        AcademicVideos academicVideos = academicVideosService.findOneAcademicVideoByVideoId(videoId);

        if(academicVideos == null) {
            log.error("【学术视频上传】视频不存在，videoId: {}",videoId);
            return ResultVOUtil.error(ResultEnum.VIDEO_IS_NOT_EXIST.getCode(),ResultEnum.VIDEO_IS_NOT_EXIST.getMessage());
        }

        try {
            String path = fastDfsUtil.uploadFile(coverFile);
            if (!org.springframework.util.StringUtils.isEmpty(coverFile)) {
                academicVideos.setVideoCoverPath(path);
            } else {
                return ResultVOUtil.error(ResultEnum.UPDATE_VIDEO_COVER_ERROR.getCode(),ResultEnum.UPDATE_VIDEO_COVER_ERROR.getMessage());
            }
        } catch (Exception e) {
            log.error("【上传视频封面】服务异常，Exception: {}", e.getMessage());
            return ResultVOUtil.error(ResultEnum.UPDATE_VIDEO_COVER_ERROR.getCode(),ResultEnum.UPDATE_VIDEO_COVER_ERROR.getMessage());
        }
        // 更新视频信息

        // video.setStatus(VideoStatusEnum.SUCCESS.getCode());

        academicVideosService.updateAcademicVideo(academicVideos);

        return ResultVOUtil.successMsg(ResultEnum.UPLOAD_VIDEO_SUCCESS.getMessage());
    }

    @ApiOperation(value = "删除原有学术视频图片",notes = "/academicVideo/delOriginPicture")
    @PostMapping("/delOriginPicture")
    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO delOriginPicture(@RequestBody VideoIdVO videoIdVO,  HttpServletRequest request){
        String videoId = videoIdVO.getVideoId();

        if(StringUtils.isEmpty(videoId)) {
            log.error("【删除原有学术视频图片】输入参数不完整，videoId: {}",videoId);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }
        // 查询视频是否存在
        AcademicVideos academicVideos = academicVideosService.queryAcademicVideoByStatus(videoId, VideoStatusEnum.SUCCESS.getCode());
        if(academicVideos == null) {
            log.error("【删除原有学术视频图片】视频不存在，videoId: {}",videoId);
            return ResultVOUtil.error(ResultEnum.VIDEO_IS_NOT_EXIST.getCode(),ResultEnum.VIDEO_IS_NOT_EXIST.getMessage());
        }
        academicVideosPictureService.delAllAcademicVideoPicturesByVideoId(videoId);

        return ResultVOUtil.success();
    }

    @ApiOperation(value = "上传学术视频图片",notes = "/academicVideo/uploadPicture")
    @PostMapping("/uploadPicture")
    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO uploadPicture(@RequestParam("file") MultipartFile file, @RequestParam("videoId") String videoId, @Size(max = 256) @RequestParam("sort") Integer sort , HttpServletRequest request){

//        String videoId = academicPictureUploadVO.getAcademicVideoId();
        if(StringUtils.isEmpty(videoId)) {
            log.error("【上传学术视频图片】输入参数不完整，videoId: {}",videoId);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        // 查询视频是否存在
        AcademicVideos academicVideos = academicVideosService.queryAcademicVideoByStatus(videoId, VideoStatusEnum.SUCCESS.getCode());
        if(academicVideos == null) {
            log.error("【上传学术视频图片】视频不存在，videoId: {}",videoId);
            return ResultVOUtil.error(ResultEnum.VIDEO_IS_NOT_EXIST.getCode(),ResultEnum.VIDEO_IS_NOT_EXIST.getMessage());
        }

        String picturePath = fileService.uploadFile(file);
        if (picturePath == null) {
            log.error("【上传学术视频图片】上传失败，videoId: {}", videoId);
            return ResultVOUtil.error(ResultEnum.UPLOAD_ACADEMIC_PICTURE_FAIL.getCode(), ResultEnum.UPLOAD_ACADEMIC_PICTURE_FAIL.getMessage());
        }

        AcademicPictureUploadVO academicPictureUploadVO = new AcademicPictureUploadVO();
        academicPictureUploadVO.setSort(sort);
        academicPictureUploadVO.setAcademicVideoId(videoId);

        academicVideosPictureService.createAcademicVideosPicture(academicPictureUploadVO, picturePath);

        return ResultVOUtil.success();
    }

    @ApiOperation(value = "上传学术视频论文",notes = "/academicVideo/uploadPaper")
    @PostMapping("/uploadPaper")
    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO uploadPaper(@Validated @RequestBody AcademicPaperUploadVO academicPaperUploadVO, HttpServletRequest request) throws Exception {

        String videoId = academicPaperUploadVO.getVideoId();
        if(StringUtils.isEmpty(videoId)) {
            log.error("【上传学术视频论文】输入参数不完整，videoId: {}",videoId);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        // 查询视频是否存在
        AcademicVideos academicVideos = academicVideosService.queryAcademicVideoByVideoId(videoId);
        if(academicVideos == null) {
            log.error("【上传学术视频论文】视频不存在，videoId: {}",videoId);
            return ResultVOUtil.error(ResultEnum.VIDEO_IS_NOT_EXIST.getCode(),ResultEnum.VIDEO_IS_NOT_EXIST.getMessage());
        }


        paperService.insertPaperAndAuthor(academicPaperUploadVO);


        return ResultVOUtil.success();

    }
}
