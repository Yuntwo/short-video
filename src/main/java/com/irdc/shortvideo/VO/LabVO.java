package com.irdc.shortvideo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/26 11:58
 * Description：
 */
@Data
@ApiModel
public class LabVO {
    @ApiModelProperty(value = "实验室id",name = "labId")
    private String labId;

    @ApiModelProperty(value = "实验室名称",name = "labName")
    private String labName;
}
