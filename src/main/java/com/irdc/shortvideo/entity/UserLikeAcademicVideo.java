package com.irdc.shortvideo.entity;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_like_academic_video")
@Data
public class UserLikeAcademicVideo {
    @Id
    private String id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 学术视频id
     */
    @Column(name = "academic_video_id")
    private String academicVideoId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}