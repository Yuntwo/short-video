package com.irdc.shortvideo.VO.video;

import com.irdc.shortvideo.VO.PaperAuthorVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/12/2 12:46
 * Description：
 */
@Data
@ApiModel
public class AcademicVideoUpdateVO {

    @ApiModelProperty(value = "学术视频id", name = "academicVideoId")
    private String videoId;

    @Size(max = 256)
    @ApiModelProperty(value = "视频标题", name = "title")
    private String title;

    @Size(max = 512)
    @ApiModelProperty(value = "视频简介", name = "videoDesc")
    private String description;

    @Size(max = 256)
    @ApiModelProperty(value = "视频相关网站", name = "websiteUrl")
    private String websiteUrl;

    @ApiModelProperty(value = "视频是否公开", name = "isPublic")
    private Integer isPublic;

    @ApiModelProperty(value = "视频标签", name = "tag")
    private String tag;
}
