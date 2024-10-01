package com.irdc.shortvideo.enums;

import lombok.Getter;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/12/2 8:59
 * Description：
 */
@Getter
public enum PaperStatusEnum {

    NORMAL(0,"公开"),
    FORBID(1,"禁止显示"),
    ;
    private Integer code;
    private String message;

    PaperStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
