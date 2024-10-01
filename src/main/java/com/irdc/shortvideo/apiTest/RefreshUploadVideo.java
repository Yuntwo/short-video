package com.irdc.shortvideo.apiTest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;
import com.irdc.shortvideo.utils.aliyun.vod.VodAcsClient;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/6 16:17
 * Description：
 */
public class RefreshUploadVideo {

    /**
     * 刷新视频上传凭证
     * @param client 发送请求客户端
     * @return RefreshUploadVideoResponse 刷新视频上传凭证响应数据
     * @throws Exception
     */
    public static final String videoId = "fa23afcbfc894eddafbf69a261ba1a19";

    public static RefreshUploadVideoResponse refreshUploadVideo(DefaultAcsClient client) throws Exception {
        RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
        request.setVideoId(videoId);
        return client.getAcsResponse(request);
    }
    // 请求示例
    public static void main(String[] argv) {
        DefaultAcsClient client = VodAcsClient.getInstance();
        RefreshUploadVideoResponse response = new RefreshUploadVideoResponse();
        try {
            response = refreshUploadVideo(client);
            System.out.print("UploadAddress = " + response.getUploadAddress() + "\n");
            System.out.print("UploadAuth = " + response.getUploadAuth() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
}
