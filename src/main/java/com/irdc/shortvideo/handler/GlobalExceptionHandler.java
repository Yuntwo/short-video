package com.irdc.shortvideo.handler;

import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.exception.BusinessException;
import com.irdc.shortvideo.exception.UserAuthorizeException;
import com.irdc.shortvideo.utils.ResultVOUtil;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangshu on 2020/11/20 14:58
 * Description：全局异常处理，包括处理用户权限鉴定
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultVO handlerGlobalException(HttpServletRequest servletRequest, HttpServletResponse httpServletResponse, Exception ex){
        if(ex instanceof BusinessException){
            return ResultVOUtil.error(((BusinessException) ex).getCode(),ex.getMessage(),((BusinessException) ex).getData());
        }else if(ex instanceof UserAuthorizeException) {
            return ResultVOUtil.error(ResultEnum.USER_IS_NOT_LOGIN.getCode(),ResultEnum.USER_IS_NOT_LOGIN.getMessage());
        } else if(ex instanceof MethodArgumentNotValidException){
            return ResultVOUtil.error(ResultEnum.METHOD_ARGUMENT_VALID_ERROR.getCode(),ResultEnum.METHOD_ARGUMENT_VALID_ERROR.getMessage());
        } else {
            return ResultVOUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getMessage());
        }

    }
}
