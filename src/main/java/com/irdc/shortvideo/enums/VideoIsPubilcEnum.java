package com.irdc.shortvideo.enums;

import lombok.Getter;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/7 11:51
 * Description：videos表中的状态信息
 */
@Getter
public enum VideoIsPubilcEnum {

    PRIVATE(0,"私有"),
    PUBLIC(1,"公开"),
    ;
    private Integer code;
    private String message;

    VideoIsPubilcEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
