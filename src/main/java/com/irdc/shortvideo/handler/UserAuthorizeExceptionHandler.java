package com.irdc.shortvideo.handler;

import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.exception.UserAuthorizeException;
import com.irdc.shortvideo.utils.ResultVOUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/5 16:21
 * Description：异常处理器
 * ControllerAdvice 切面通知
 * 进行了更新，定义全局的异常处理
 */
@Deprecated
@ControllerAdvice
public class UserAuthorizeExceptionHandler {


    @ExceptionHandler(value = UserAuthorizeException.class)
    @ResponseBody
    public ResultVO handlerAuthorizeException() {
        return ResultVOUtil.error(ResultEnum.USER_IS_NOT_LOGIN.getCode(),ResultEnum.USER_IS_NOT_LOGIN.getMessage());

    }
}
