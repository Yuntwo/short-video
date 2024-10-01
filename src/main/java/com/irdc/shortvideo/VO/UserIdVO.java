package com.irdc.shortvideo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/20 21:04
 * Description：
 */
@ApiModel
@Data
public class UserIdVO {
    @ApiModelProperty(value = "用户id",name = "userId",required=true)
    private String userId;
}
