package com.irdc.shortvideo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/6 21:01
 * Description：视频上传信息
 */
@Data
@ApiModel
public class VideoUpdateVO {

    @ApiModelProperty(value = "视频id",name = "videoId")
    private String videoId;

    @Size(max = 256)
    @ApiModelProperty(value = "视频标题",name = "title")
    private String title;

    @Size(max = 512)
    @ApiModelProperty(value = "视频描述",name = "description")
    private String description;

    @ApiModelProperty(value = "视频标签",name = "tag")
    private String tag;

    @ApiModelProperty(value = "视频是否公开，1公开，0私有",name = "isPublic")
    private Integer isPublic;
}
