package com.irdc.shortvideo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/26 21:34
 * Description：
 */
@Data
@ApiModel
public class PaperAuthorVO {

    @ApiModelProperty(value = "姓名", name = "name")
    private String name;

    @ApiModelProperty(value = "公司", name = "company")
    private String company;

    @ApiModelProperty(value = "职位", name = "position")
    private String position;

    @ApiModelProperty(value = "联系方式", name = "contact")
    private String contact;

    @ApiModelProperty(value = "排序", name = "sort")
    private Integer sort;
}
