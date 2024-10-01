package com.irdc.shortvideo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yangshu
 * Description: 用户注册需要的信息
 */

@Data
@ApiModel
public class PasswordVO {
  @ApiModelProperty(value = "密码", name = "password", example = "password")
  private String password;

  @ApiModelProperty(value = "新密码", name = "newPassword", example = "newPassword")
  private String newPassword;

}
