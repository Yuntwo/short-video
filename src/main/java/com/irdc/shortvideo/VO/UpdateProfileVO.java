package com.irdc.shortvideo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.context.annotation.Primary;

import javax.validation.constraints.Size;

/**
 * Package short-video
 *
 * @author yangshu on 2020/10/2 11:38
 * Description：
 */

@Data
@ApiModel
public class UpdateProfileVO {

    @Size(max = 32)
    @ApiModelProperty(value = "用户名",name = "username")
    private String username;

    @ApiModelProperty(value = "用户性别",name = "sex")
    private Integer sex;

    @ApiModelProperty(value = "用户年龄",name = "age")
    private Integer age;

    @Size(max = 128)
    @ApiModelProperty(value = "个人简介",name = "description")
    private String description;

    @Size(max = 32)
    @ApiModelProperty(value = "邮箱",name = "email")
    private String email;


}
