package com.irdc.shortvideo.VO.video;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * @author yangshu on 2020/10/21 21:21
 * Description：
 */

@Data
@ApiModel
public class DeleteVideoVO {

    @ApiModelProperty(value = "视频id",name = "videoId",example="abcd",required = true)
    private String videoId;
}
