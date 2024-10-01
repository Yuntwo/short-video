package com.irdc.shortvideo.exception;

import com.irdc.shortvideo.enums.ResultEnum;
import lombok.Getter;

/**
 * @author yangshu on 2020/11/20 14:56
 * Description：统一的业务异常
 */

@Getter
public class BusinessException extends RuntimeException{

    private Integer code;
    private String message;

    // 表单验证中传递的一些信息
    private Object data;


    // public BusinessException(Integer code,String message,Object data) {
    //     super();
    //     this.code = code;
    //     this.message = message;
    //     this.data = data;
    // }
    //
    // public BusinessException(Integer code,String message) {
    //     super();
    //     this.code = code;
    //     this.message = message;
    // }

    public BusinessException(ResultEnum resultEnum) {
        super();
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    public BusinessException(ResultEnum resultEnum,Object data) {
        super();
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
        this.data = data;
    }
}
