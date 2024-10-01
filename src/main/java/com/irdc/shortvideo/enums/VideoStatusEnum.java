package com.irdc.shortvideo.enums;

import lombok.Getter;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/7 11:51
 * Description：videos表中的状态信息
 */
@Getter
public enum VideoStatusEnum {

    UPLOADING(0,"正在发布"),
    SUCCESS(1,"发布成功"),
    FORBID(2,"禁止播放"),
    DELETE(3,"删除视频"),
    ;

    private Integer code;
    private String message;

    VideoStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
