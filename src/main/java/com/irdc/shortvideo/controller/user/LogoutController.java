package com.irdc.shortvideo.controller.user;

import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.annotation.UserAuthentication;
import com.irdc.shortvideo.enums.AuthAopEnum;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.utils.RedisUtil;
import com.irdc.shortvideo.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户注销接口
 */
@Api(value = "用户注销接口",tags = {"用户注销"})
@RestController
@RequestMapping("/user")
@Slf4j
public class LogoutController {

    @Autowired
    private RedisUtil redisUtil;


    @ApiOperation(value = "用户注销",notes = "/user/logout")
    @PostMapping("/logout")
    public ResultVO logout(HttpServletRequest request) {

        boolean res = redisUtil.userLogoutRedis(request);
        if(!res) {
            log.error("【用户注销失败】用户没有登录");
            return ResultVOUtil.error(ResultEnum.USER_IS_NOT_LOGIN.getCode(),ResultEnum.USER_IS_NOT_LOGIN.getMessage());
        }
        return ResultVOUtil.successMsg(ResultEnum.USER_LOGOUT_SUCCESS.getMessage());
    }
}
