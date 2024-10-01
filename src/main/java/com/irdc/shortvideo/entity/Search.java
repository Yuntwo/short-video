package com.irdc.shortvideo.entity;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
public class Search {
    @Id
    private String id;

    /**
     * 搜索内容
     */
    private String content;

    /**
     * 搜索者id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}