package com.irdc.shortvideo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/23 20:05
 * Description：
 */
@Data
@ApiModel
public class PromotionVO {
    @ApiModelProperty(value = "图片url",name = "pictureUrl")
    private String pictureUrl;

    @ApiModelProperty(value = "新闻url",name = "newsUrl")
    private String newsUrl;
}
