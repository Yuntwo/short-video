package com.irdc.shortvideo.enums;

import lombok.Getter;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/7 11:51
 * Description：videos表中的状态信息
 */
@Getter
public enum UserSexEnum {

    MEN(0,"男性"),
    WOMEN(1,"女性"),
    ;
    private Integer code;
    private String message;

    UserSexEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
