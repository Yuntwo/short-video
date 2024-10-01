package com.irdc.shortvideo.enums;

import lombok.Getter;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/27 20:39
 * Description：
 */
@Getter
public enum VideoTypeEnum {
    COMMON_VIDEO(0,"普通视频"),
    ACADEMIC_VIDEO(1,"学术视频"),
    ;


    private Integer code;
    private String message;

    VideoTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
