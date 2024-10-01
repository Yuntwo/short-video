package com.irdc.shortvideo.apiTest;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/6 11:50
 * Description：
 */
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.irdc.shortvideo.utils.aliyun.vod.VodAcsClient;

public class UploadVideo {
    /**
     * 获取视频上传地址和凭证
     * @param client 发送请求客户端
     * @return CreateUploadVideoResponse 获取视频上传地址和凭证响应数据
     * @throws Exception
     */
    public static CreateUploadVideoResponse createUploadVideo(DefaultAcsClient client) throws Exception {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setTitle("this is a sample");
        request.setFileName("filename.mp4");
        request.setDescription("this is a desc");
        //UserData，用户自定义设置参数，用户需要单独回调URL及数据透传时设置(非必须)
        //JSONObject userData = new JSONObject();
        //UserData回调部分设置
        //JSONObject messageCallback = new JSONObject();
        //messageCallback.put("CallbackURL", "http://xxxxx");
        //messageCallback.put("CallbackType", "http");
        //userData.put("MessageCallback", messageCallback.toJSONString());
        //UserData透传数据部分设置
        //JSONObject extend = new JSONObject();
        //extend.put("MyId", "user-defined-id");
        //userData.put("Extend", extend.toJSONString());
        //request.setUserData(userData.toJSONString());
        return client.getAcsResponse(request);
    }

    // 请求示例
//    VideoId = fa23afcbfc894eddafbf69a261ba1a19
//    UploadAddress = eyJFbmRwb2ludCI6Imh0dHBzOi8vb3NzLWNuLXNoYW5naGFpLmFsaXl1bmNzLmNvbSIsIkJ1Y2tldCI6Im91dGluLTZkNmZjNWIzZmJiNzExZWFhMjQwMDAxNjNlMDI0YzZhIiwiRmlsZU5hbWUiOiJjdXN0b21lclRyYW5zLzUyZWI5OWU3Yzk5MjkwYTFhNzY0M2RiYjEyOGI5MTE1LzMzMmI1MjBlLTE3NGZkMDBmMzI3LTAwMDQtMzk5NC03ZmMtMmEyMTUubXA0In0=
//    UploadAuth = eyJTZWN1cml0eVRva2VuIjoiQ0FJUzJ3UjFxNkZ0NUIyeWZTaklyNWZVRDlIVzFaY1p4Wk80TjE3MzNUTUFXY1Z1bDcvbjFEejJJSGxQZTNGaEFPb2V2L2svbVc5VTdmb2NsclVxRXM4VUh4U1ZQWlVndE1nSXJGejZKcGZadjh1ODRZQURpNUNqUWNBNHZKOWNtcDI4V2Y3d2FmK0FVQXJHQ1RtZDVNZ1lvOWJUY1RHbFFDWnVXLy90b0pWN2I5TVJjeENsWkQ1ZGZybC9MUmRqcjhsbzF4R3pVUEcyS1V6U24zYjNCa2hsc1JZZTcyUms4dmFIeGRhQXpSRGNnVmJtcUpjU3ZKK2pDNEM4WXM5Z0c1MTlYdHlwdm9weGJiR1Q4Q05aNXo5QTlxcDlrTTQ5L2l6YzdQNlFIMzViNFJpTkw4L1o3dFFOWHdoaWZmb2JIYTlZcmZIZ21OaGx2dkRTajQzdDF5dFZPZVpjWDBha1E1dTdrdTdaSFArb0x0OGphWXZqUDNQRTNyTHBNWUx1NFQ0OFpYVVNPRHREWWNaRFVIaHJFazRSVWpYZEk2T2Y4VXJXU1FDN1dzcjIxN290ZzdGeXlrM3M4TWFIQWtXTFg3U0IyRHdFQjRjNGFFb2tWVzRSeG5lelc2VUJhUkJwYmxkN0JxNmNWNWxPZEJSWm9LK0t6UXJKVFg5RXoycExtdUQ2ZS9MT3M3b0RWSjM3V1p0S3l1aDRZNDlkNFU4clZFalBRcWl5a1QwY0ZncGZUSzFSemJQbU5MS205YmFCMjUvelcrUGREZTBkc1Znb1hWTDdwaUdXRzNSTE5uK3p0Sjl4YmtlRStzS1VrcUNTL1pnOUdsQWc3Tm9BVkZpSWVOODM4d1ErdS9Mc3RCbkxxclBvREhudDlYY2o1dDdkOHBOejgwSmlaYkRtb1pmTDRHNlA3U1ROTy9aanc1K1BCak0wZTNudEpTd2xtc0wxcjJrY3VoVU1uMXV6UHhzaThGbUwzUTZ5QnBaQ2lhUFRseW9lV3ZZT3libUhGMno4b0g4Y0lOYUk4cXNOWitSdVIrbEhTYzJneGp0dXd2RDNNTDBPQ0Q0M1dYd2FnQUYxcmlYcWp3eEF4cURXSDBadlJYUVFhRUltODFyczdzZ2RNWGNPMTExVzUvL0haRVV2RTJwTWdNWGxFeFB3aUZ2THYrOVQ0cjNFQTNYTHVSR0NEZmZmdC9xelNGenNzWFFuSFBUeUVITkxyRTFmWWp4UUlpcG53KzNMdmZTYmcwc085Y3Q3TEJ2NE0vQUFKaE5WRFRWdnVsNHJ6a3cxNFYvcTNWUnZnT0g0Mmc9PSIsIkFjY2Vzc0tleUlkIjoiU1RTLk5UYURrYjhIOHJRUzV4RjkzVVVKQnh6TDYiLCJFeHBpcmVVVENUaW1lIjoiMjAyMC0xMC0wNlQwOToyMjoxMloiLCJBY2Nlc3NLZXlTZWNyZXQiOiJHOHdCdFFQZ0VId28xb1RENFVreEhpaG9rV29uYWN1eHZSSENvNDRYWHpmNCIsIkV4cGlyYXRpb24iOiIzNjAwIiwiUmVnaW9uIjoiY24tc2hhbmdoYWkifQ==
//    RequestId = CB29B05A-E207-4368-BBA0-47A7487B888B
    public static void main(String[] argv) {
        DefaultAcsClient client = VodAcsClient.getInstance();
        CreateUploadVideoResponse response = new CreateUploadVideoResponse();
        try {
            response = createUploadVideo(client);
            System.out.print("VideoId = " + response.getVideoId() + "\n");
            System.out.print("UploadAddress = " + response.getUploadAddress() + "\n");
            System.out.print("UploadAuth = " + response.getUploadAuth() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

}