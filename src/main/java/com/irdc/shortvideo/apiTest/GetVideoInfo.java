package com.irdc.shortvideo.apiTest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoResponse;
import com.irdc.shortvideo.utils.aliyun.vod.VodAcsClient;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/6 17:06
 * Description：
 */
public class GetVideoInfo {

    /**
     * 获取视频信息
     * @param client 发送请求客户端
     * @return GetVideoInfoResponse 获取视频信息响应数据
     * @throws Exception
     */
    public static GetVideoInfoResponse getVideoInfo(DefaultAcsClient client) throws Exception {
        GetVideoInfoRequest request = new GetVideoInfoRequest();
        request.setVideoId("98fd29aadfec48a5a25d69346a74ee13");
        return client.getAcsResponse(request);
    }

    // 请求示例
    public static void main(String[] argv) {
        DefaultAcsClient client = VodAcsClient.getInstance();
        GetVideoInfoResponse response = new GetVideoInfoResponse();
        try {
            response = getVideoInfo(client);
            System.out.print("Title = " + response.getVideo().getTitle() + "\n");
            System.out.print("Description = " + response.getVideo().getDescription() + "\n");
            // 获取视频状态

            System.out.println("status： " + response.getVideo().getStatus());
            System.out.println(response.getVideo().getCoverURL());
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

}
