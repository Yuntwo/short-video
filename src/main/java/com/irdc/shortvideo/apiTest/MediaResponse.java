package com.irdc.shortvideo.apiTest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.SearchMediaRequest;
import com.aliyuncs.vod.model.v20170321.SearchMediaResponse;
import com.irdc.shortvideo.utils.aliyun.vod.VodAcsClient;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/6 17:00
 * Description：
 */
public class MediaResponse {

    /**
     * 搜索媒资信息
     * @param client 发送请求客户端
     * @return SearchMediaResponse 搜索媒资信息响应数据
     * @throws Exception

     */
    public static SearchMediaResponse searchMedia(DefaultAcsClient client) throws Exception {
        SearchMediaRequest request = new SearchMediaRequest();
        request.setFields("Title,CoverURL,Status");
        // request.setMatch("Status in ('Normal','Checking') and CreationTime = ('2018-07-01T08:00:00Z','2018-08-01T08:00:00Z')");
        request.setPageNo(1);
        request.setPageSize(10);
        request.setSearchType("video");
        request.setSortBy("CreationTime:Desc");
        return client.getAcsResponse(request);
    }

    // 请求示例
    public static void main(String[] argv) {
        DefaultAcsClient client = VodAcsClient.getInstance();
        SearchMediaResponse response = new SearchMediaResponse();
        try {
            response = searchMedia(client);
            if (response.getMediaList() != null && response.getMediaList().size() > 0) {
                System.out.print("Total = " + response.getTotal() + "\n");
                System.out.print("ScrollToken = " + response.getScrollToken() + "\n");
                for (SearchMediaResponse.Media media : response.getMediaList()) {
                    System.out.print("MediaId = " + media.getMediaId() + "\n");
                    System.out.print("MediaType = " + media.getMediaType() + "\n");
                    System.out.print("CreationTime = " + media.getCreationTime() + "\n");
                    System.out.print("Title = " + media.getVideo().getTitle() + "\n");
                    System.out.print("CoverURL = " + media.getVideo().getCoverURL() + "\n");
                    System.out.print("Status = " + media.getVideo().getStatus() + "\n");
                }
            }
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

}
