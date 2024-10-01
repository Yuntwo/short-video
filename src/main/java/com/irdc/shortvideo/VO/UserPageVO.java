package com.irdc.shortvideo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/12/10 20:06
 * Description：
 */

@ApiModel
@Data
public class UserPageVO {

    @ApiModelProperty(value = "用户id",name = "userId")
    private String userId;

    @ApiModelProperty(value = "页数",name = "page")
    private Integer page;

    @ApiModelProperty(value = "每页视频数量",name = "size")
    private Integer size;
}
