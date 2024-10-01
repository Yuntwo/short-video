package com.irdc.shortvideo.enums;

import lombok.Getter;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/20 9:48
 * Description：
 */
@Getter
public enum PromotionPictureStatusEnum {
    NORMAL(0,"正常"),
    ABANDONMENT(1,"弃用"),
            ;
    private Integer code;
    private String message;

    PromotionPictureStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
