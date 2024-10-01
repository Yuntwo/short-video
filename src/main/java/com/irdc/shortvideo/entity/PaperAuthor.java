package com.irdc.shortvideo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/26 20:58
 * Description：
 */
@Data
public class PaperAuthor {
    @Id
    @Column(name = "author_id")
    private String authorId;

    /**
     * 论文id
     */
    @Column(name = "paper_id")
    private String paperId;

    /**
     * 姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 公司
     */
    @Column(name = "company")
    private String company;

    /**
     * 职位
     */
    @Column(name = "position")
    private String position;

    /**
     * 联系方式
     */
    @Column(name = "contact")
    private String contact;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 实验室状态状态：0、正常 1、禁止
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}
