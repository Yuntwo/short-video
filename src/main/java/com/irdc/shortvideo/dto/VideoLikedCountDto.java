package com.irdc.shortvideo.dto;

import lombok.Data;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/13 22:31
 * Description：
 */
@Data
public class VideoLikedCountDto {
    String videoId;
    Long count;

    public VideoLikedCountDto(String videoId, Long count) {
        this.videoId = videoId;
        this.count = count;
    }
}
