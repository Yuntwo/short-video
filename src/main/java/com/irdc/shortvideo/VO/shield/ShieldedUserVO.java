package com.irdc.shortvideo.VO.shield;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/10 20:12
 * Description：
 */
@Data
@ApiModel
public class ShieldedUserVO {

    @ApiModelProperty(value = "用户id",name = "userId")
    private String userId;

    @ApiModelProperty(value = "用户名",name = "username")
    private String username;

    @ApiModelProperty(value = "用户头像",name = "faceImage")
    private String faceImage;
}
