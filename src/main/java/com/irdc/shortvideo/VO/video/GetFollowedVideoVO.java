package com.irdc.shortvideo.VO.video;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Package shortvideo
 * Description：前端通过分页传递的视频信息
 * @author yuntwo
 */

@Data
@ApiModel
public class GetFollowedVideoVO {

  @ApiModelProperty(value = "页数",name = "page",example = "1")
  private Integer page;

  @ApiModelProperty(value = "每页视频数量",name = "size",example = "5")
  private Integer size;

}
