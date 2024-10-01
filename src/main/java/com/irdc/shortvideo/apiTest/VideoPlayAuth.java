package com.irdc.shortvideo.apiTest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.irdc.shortvideo.utils.aliyun.vod.VodAcsClient;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/6 16:29
 * Description：
 */
public class VideoPlayAuth {

    /*获取播放凭证函数*/
    public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client) throws Exception {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("98fd29aadfec48a5a25d69346a74ee13");
        return client.getAcsResponse(request);
    }
    /*以下为调用示例*/
    public static void main(String[] argv) {
        DefaultAcsClient client = VodAcsClient.getInstance();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            response = getVideoPlayAuth(client);
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            //VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
}
