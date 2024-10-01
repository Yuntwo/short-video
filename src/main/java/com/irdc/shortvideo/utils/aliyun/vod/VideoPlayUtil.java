package com.irdc.shortvideo.utils.aliyun.vod;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import com.irdc.shortvideo.VO.VideoUpdateVO;
import com.irdc.shortvideo.VO.video.AcademicVideoUpdateVO;
import com.irdc.shortvideo.VO.video.AcademicVideoUploadVO;
import org.springframework.beans.BeanUtils;

/**
 * Created at 2020/10/3  15:04
 *
 * @author dcr
 * @version 1.0.0
 */
public class VideoPlayUtil {

    /**
     * 获取视频播放凭证
     * @param videoId
     * @return
     * @throws ClientException
     */
    public static GetVideoPlayAuthResponse getVideoPlayAuth(String videoId) throws ClientException {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        // 播放凭证的过期时间 1000s
        request.setAuthInfoTimeout(3000L);
        return VodAcsClient.getInstance().getAcsResponse(request);
    }

    /**
     * 获取视频上传的凭证
     * @param videoUpdateVO
     * @return
     * @throws Exception
     */
    public static CreateUploadVideoResponse createUploadVideo(VideoUpdateVO videoUpdateVO) throws Exception {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        BeanUtils.copyProperties(videoUpdateVO,request);
        request.setFileName("filename.mp4");

        return VodAcsClient.getInstance().getAcsResponse(request);
    }

    /**
     * 获取视频上传的凭证
     * @param academicVideoUploadVO
     * @return
     * @throws Exception
     */
    public static CreateUploadVideoResponse createUploadVideo(AcademicVideoUploadVO academicVideoUploadVO) throws Exception {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        BeanUtils.copyProperties(academicVideoUploadVO,request);
        request.setFileName("filename.mp4");

        return VodAcsClient.getInstance().getAcsResponse(request);
    }

    /**
     * 刷新视频上传凭证
     * @param videoId
     * @return
     * @throws Exception
     */
    public static RefreshUploadVideoResponse refreshUploadVideo(String videoId) throws Exception {
        RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
        request.setVideoId(videoId);
        return VodAcsClient.getInstance().getAcsResponse(request);
    }

    /**
     * 获取视频信息
     * @param videoId
     * @return
     * @throws Exception
     */
    public static GetVideoInfoResponse getVideoInfo(String videoId) throws Exception {
        GetVideoInfoRequest request = new GetVideoInfoRequest();
        request.setVideoId(videoId);
        return VodAcsClient.getInstance().getAcsResponse(request);
    }

    /**
     * 修改视频的信息
     * @param videoUpdateVO
     * @throws Exception
     */
    public static void updateVideoInfo(VideoUpdateVO videoUpdateVO) throws Exception {
        UpdateVideoInfoRequest request = new UpdateVideoInfoRequest();
        BeanUtils.copyProperties(videoUpdateVO,request);

        VodAcsClient.getInstance().getAcsResponse(request);
    }

}
