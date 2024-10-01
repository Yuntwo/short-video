package com.irdc.shortvideo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/15 20:30
 * Description：
 */
@Data
@ApiModel
public class AchievementVO {

    @ApiModelProperty(value = "成就标题",name = "title",example="评论")
    private String title;

    @ApiModelProperty(value = "成就种类",name = "type")
    private Integer type;

    @ApiModelProperty(value = "成就等级",name = "level")
    private Integer level;

    @ApiModelProperty(value = "成就获得时间",name = "createTime")
    private Date createTime;
}
