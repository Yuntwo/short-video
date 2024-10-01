package com.irdc.shortvideo.VO.video;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/13 0:38
 * Description：
 */
@Data
@ApiModel
public class AcademicPictureInfoVO {

    @ApiModelProperty(value = "url", name = "url")
    private String url;

    @ApiModelProperty(value = "排序", name = "sort")
    private Integer sort;
}
