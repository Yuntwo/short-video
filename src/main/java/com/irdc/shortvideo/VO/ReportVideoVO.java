package com.irdc.shortvideo.VO;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@ApiModel
public class ReportVideoVO {
    @ApiModelProperty(value = "视频id",name = "videoId")
    private String videoId;

    @Size(max = 255)
    @ApiModelProperty(value = "举报内容",name = "content")
    private String content;

    @ApiModelProperty(value = "举报标签",name = "tag")
    private String tag;

}