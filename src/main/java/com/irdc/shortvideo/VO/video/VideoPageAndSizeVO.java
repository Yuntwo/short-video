package com.irdc.shortvideo.VO.video;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/30 14:29
 * Description：
 */
@Data
@ApiModel
public class VideoPageAndSizeVO {
    @ApiModelProperty(value = "页数",name = "page",example = "1")
    private Integer page;

    @ApiModelProperty(value = "每页视频数量",name = "size",example = "5")
    private Integer size;
}
