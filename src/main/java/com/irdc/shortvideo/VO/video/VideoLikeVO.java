package com.irdc.shortvideo.VO.video;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/28 22:15
 * Description：
 */

@Data
@ApiModel
public class VideoLikeVO {
    @ApiModelProperty(value = "视频id",name = "videoId",required = true)
    private String videoId;

    @ApiModelProperty(value = "视频类型",name = "type",required = true)
    private Integer type;
}
