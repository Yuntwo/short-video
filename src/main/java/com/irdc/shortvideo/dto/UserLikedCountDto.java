package com.irdc.shortvideo.dto;

import lombok.Data;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/15 10:29
 * Description：
 */
@Data
public class UserLikedCountDto {
    String userId;
    Integer count;

    public UserLikedCountDto(String userId, Integer count) {
        this.userId = userId;
        this.count = count;
    }
}
