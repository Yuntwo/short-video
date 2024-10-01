package com.irdc.shortvideo.exception;

import com.irdc.shortvideo.enums.ResultEnum;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/3 12:23
 * Description：
 */
public class UserException extends RuntimeException{
    private Integer code;

    public UserException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public UserException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
