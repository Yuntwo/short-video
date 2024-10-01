package com.irdc.shortvideo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/12/15 17:11
 * Description：
 */

@Data
@ApiModel
public class PackageInfoVO {

    @ApiModelProperty(value = "版本号", name = "number")
    private String number;

    @ApiModelProperty(value = "版本描述", name = "description")
    private String description;

    @ApiModelProperty(value = "版本链接", name = "url")
    private String url;
}
