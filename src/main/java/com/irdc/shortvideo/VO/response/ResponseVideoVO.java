package com.irdc.shortvideo.VO.response;

import com.aliyuncs.vod.model.v20170321.GetVideoInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.irdc.shortvideo.VO.ResultVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created at 2020/10/3  16:10
 *
 * @author dcr
 * @version 1.0.0
 */
@Data
@ApiModel
public class ResponseVideoVO {
    @ApiModelProperty(value = "play Auth", name = "playAuth")
    private String playAuth;

    @ApiModelProperty(value = "video id", name = "videoId")
    private String videoId;

    @ApiModelProperty(value = "video title", name = "videoTitle")
    private String videoTitle;

    public static ResponseVideoVO fromGetVideoPlayAuthResponse(GetVideoPlayAuthResponse response) {
        ResponseVideoVO responseVideoVO = new ResponseVideoVO();
        responseVideoVO.setPlayAuth(response.getPlayAuth());
        responseVideoVO.setVideoId(response.getVideoMeta().getVideoId());
        responseVideoVO.setVideoTitle(response.getVideoMeta().getVideoId());
        return responseVideoVO;
    }
}
