package com.irdc.shortvideo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/10/5 20:52
 * Description：
 */
@Data
@ApiModel
public class FollowingFollowerVO {

    @ApiModelProperty(value = "用户id",name = "userId")
    private String userId;

    @ApiModelProperty(value = "用户名称",name = "username")
    private String username;

    @ApiModelProperty(value = "用户头像",name = "faceImage")
    private String faceImage;

    @ApiModelProperty(value = "用户描述",name = "description")
    private String description;


}
