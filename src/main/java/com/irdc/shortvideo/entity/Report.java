package com.irdc.shortvideo.entity;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
public class Report {
    @Id
    private String id;

    /**
     * 举报者id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 视频id
     */
    @Column(name = "video_id")
    private String videoId;

    /**
     * 举报内容
     */
    private String content;

    /**
     * 举报标签，枚举类型，可选
     */
    private String tag;

    /**
     * 举报处理状态：0、未处理 1、已处理
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}