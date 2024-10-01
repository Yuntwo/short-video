package com.irdc.shortvideo.enums;

import lombok.Getter;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/12/2 9:00
 * Description：
 */
@Getter
public enum PaperAuthorStatusEnum {

    NORMAL(0,"公开"),
    FORBID(1,"禁止显示"),
    ;
    private Integer code;
    private String message;

    PaperAuthorStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
