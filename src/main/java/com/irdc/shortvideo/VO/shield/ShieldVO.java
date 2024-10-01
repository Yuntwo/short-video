package com.irdc.shortvideo.VO.shield;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/10 19:34
 * Description：
 */
@Data
@ApiModel
public class ShieldVO {

    @ApiModelProperty(value = "执行屏蔽操作的用户id",name = "fromUserId")
    private String fromUserId;

    @ApiModelProperty(value = "被屏蔽的用户id",name = "toUserId")
    private String toUserId;
}
