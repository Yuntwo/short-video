package com.irdc.shortvideo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/26 11:17
 * Description：
 */

@Data
public class Lab {
    @Id
    @Column(name = "lab_id")
    private String labId;

    /**
     * 实验室名称
     */
    @Column(name = "lab_name")
    private String labName;

    /**
     * 实验室状态状态：0、正常 1、禁用
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}
