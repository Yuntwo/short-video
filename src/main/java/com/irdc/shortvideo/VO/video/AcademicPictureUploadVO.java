package com.irdc.shortvideo.VO.video;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/11 23:12
 * Description：
 */
@Data
@ApiModel
public class AcademicPictureUploadVO {

    @ApiModelProperty(value = "学术视频id", name = "academicVideoId")
    private String academicVideoId;

    @ApiModelProperty(value = "排序", name = "sort")
    private Integer sort;
}
