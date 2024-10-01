package com.irdc.shortvideo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/10/7 14:39
 * Description：
 */
@Data
public class ReportTag {
    @Id
    private String id;

    /**
     * 标签内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 标签是否启用：0、当前正在使用 1、已弃用
     */
    @Column(name = "status")
    private Integer status;

}

