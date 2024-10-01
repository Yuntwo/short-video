package com.irdc.shortvideo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/26 20:53
 * Description：
 */
@Data
public class Paper {
    @Id
    @Column(name = "paper_id")
    private String paperId;

    /**
     * 学术视频id
     */
    @Column(name = "academic_video_id")
    private String academicVideoId;

    /**
     * 论文标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 论文期刊
     */
    @Column(name = "periodical")
    private String periodical;

    /**
     * 论文索引
     */
    @Column(name = "index")
    private String index;

    /**
     * 论文内容
     */
    @Column(name = "text")
    private String text;

    /**
     * 发表时间
     */
    @Column(name = "publish_time")
    private Date publishTime;

    /**
     * 网站url
     */
    @Column(name = "website_url")
    private String websiteUrl;

    /**
     * 论文状态状态：0、正常 1、禁止
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}
