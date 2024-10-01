package com.irdc.shortvideo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Package shortvideo
 * @author yangshu on 2020/10/7 21:30
 * Description：前端通过分页传递的视频信息
 */

@Data
@ApiModel
public class GetTagVideoVO {

  @ApiModelProperty(value = "页数",name = "page",example = "1")
  private Integer page;

  @ApiModelProperty(value = "每页视频数量",name = "size",example = "5")
  private Integer size;

  @ApiModelProperty(value = "标签",name = "tag")
  private String tag;

}
