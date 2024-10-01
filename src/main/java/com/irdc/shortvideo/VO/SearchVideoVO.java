package com.irdc.shortvideo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * @author liuyun
 */
@Data
@ApiModel
public class SearchVideoVO {

  @Size(max = 64)
  @ApiModelProperty(value = "搜索内容", name = "searchContent", required = true)
  private String searchContent;
}
