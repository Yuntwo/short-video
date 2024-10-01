package com.irdc.shortvideo.apiTest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoListRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoListResponse;
import com.irdc.shortvideo.utils.aliyun.vod.VodAcsClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/6 17:23
 * Description：获取视频列表
 * 该接口最多可获取指定筛选条件（如状态、分类等）的前5000条视频。
 * 建议限定StartTime和EndTime来分批获取数据。若需要查询更多视频，甚至遍历所有视频信息，请使用 搜索媒资信息 接口。 MediaResponse
 */
public class GetVideoList {

    // 根据Date时间生成UTC时间函数
    public static String generateUTCTime(Date time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormat.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));
        dateFormat.setLenient(false);
        return dateFormat.format(time);
    }

    /**
     * 获取视频列表
     * @param client 发送请求客户端
     * @return GetVideoListResponse 获取视频列表响应数据
     * @throws Exception
     */
    public static GetVideoListResponse getVideoList(DefaultAcsClient client) throws Exception {
        GetVideoListRequest request = new GetVideoListRequest();
        // 分别取一个月前、当前时间的UTC时间作为筛选视频列表的起止时间
        String monthAgoUTCTime = generateUTCTime(new Date(System.currentTimeMillis() - 30*86400*1000L));
        String nowUTCTime = generateUTCTime(new Date(System.currentTimeMillis()));
        // 视频创建的起始时间，为UTC格式
        request.setStartTime(monthAgoUTCTime);
        // 视频创建的结束时间，为UTC格式
        request.setEndTime(nowUTCTime);
        // 视频状态，默认获取所有状态的视频，多个用逗号分隔
        // request.setStatus("Uploading,Normal,Transcoding");
        request.setPageNo(1);
        request.setPageSize(20);
        return client.getAcsResponse(request);
    }

    // 请求示例
    public static void main(String[] argv) {
        DefaultAcsClient client = VodAcsClient.getInstance();
        GetVideoListResponse response = new GetVideoListResponse();
        try {
            response = getVideoList(client);
            // 根据指定筛选条件查询到的视频总数
            System.out.print("Total = " + response.getTotal() + "\n");
            for (GetVideoListResponse.Video video : response.getVideoList()) {
                System.out.print("Title = " + video.getTitle() + "\n");
                System.out.print("Description = " + video.getDescription() + "\n");
                System.out.print("Tags = " + video.getTags() + "\n");
                System.out.print("CreationTime = " + video.getCreationTime() + "\n");
            }
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }


}
