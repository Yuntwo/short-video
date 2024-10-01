package com.irdc.shortvideo.VO;

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
public class GetVideoByVideoIdVO {

  @ApiModelProperty(value = "视频id", name = "videoId")
  private String videoId;

}
