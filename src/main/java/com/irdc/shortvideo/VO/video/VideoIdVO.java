package com.irdc.shortvideo.VO.video;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/30 14:27
 * Description：
 */
@Data
@ApiModel
public class VideoIdVO {
    @ApiModelProperty(value = "视频id",name = "videoId",required = true)
    private String videoId;
}
