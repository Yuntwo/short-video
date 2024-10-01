package com.irdc.shortvideo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * Package shortvideo
 * @author yangshu on 2020/10/7 21:30
 * Description：前端通过分页传递的视频信息
 */

@Data
@ApiModel
public class GetSearchVideoVO {

  @ApiModelProperty(value = "页数",name = "page",example = "1")
  private Integer page;

  @ApiModelProperty(value = "每页视频数量",name = "size",example = "5")
  private Integer size;

  @Size(max = 64)
  @ApiModelProperty(value = "用户搜索内容",name = "searchContent")
  private String searchContent;

}
