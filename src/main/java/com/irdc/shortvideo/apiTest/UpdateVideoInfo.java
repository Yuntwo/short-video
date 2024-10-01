package com.irdc.shortvideo.apiTest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.UpdateVideoInfoRequest;
import com.aliyuncs.vod.model.v20170321.UpdateVideoInfoResponse;
import com.irdc.shortvideo.utils.aliyun.vod.VodAcsClient;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/6 17:19
 * Description：
 */
public class UpdateVideoInfo {

    /**
     * 修改视频信息
     * @param client 发送请求客户端
     * @return UpdateVideoInfoResponse 修改视频信息响应数据
     * @throws Exception
     */
    public static UpdateVideoInfoResponse updateVideoInfo(DefaultAcsClient client) throws Exception {
        UpdateVideoInfoRequest request = new UpdateVideoInfoRequest();
        request.setVideoId("VideoId");
        request.setTitle("new Title");
        request.setDescription("new Description");
        request.setTags("new Tag1,new Tag2");
        return client.getAcsResponse(request);
    }

    // 请求示例
    public static void main(String[] argv) {
        DefaultAcsClient client = VodAcsClient.getInstance();
        UpdateVideoInfoResponse response = new UpdateVideoInfoResponse();
        try {
            response = updateVideoInfo(client);
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

}
