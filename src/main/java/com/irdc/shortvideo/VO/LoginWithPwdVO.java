package com.irdc.shortvideo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author yangshu
 * Description: 用户注册需要的信息
 */

@Data
@ApiModel
public class LoginWithPwdVO {

  @Size(max = 16)
  @ApiModelProperty(value = "手机号", name = "phone", example = "123444")
  private String phone;

  @ApiModelProperty(value = "密码",name = "password",example="password")
  private String password;
}
