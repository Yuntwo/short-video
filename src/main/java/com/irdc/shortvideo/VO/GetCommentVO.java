package com.irdc.shortvideo.VO;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuntwo
 */
@Data
@ApiModel
public class GetCommentVO {

  @ApiModelProperty(value = "视频id",name = "videoId",example="1234")
  private String videoId;

}
