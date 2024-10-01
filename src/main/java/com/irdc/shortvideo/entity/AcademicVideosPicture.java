package com.irdc.shortvideo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/11 14:22
 * Description：
 */

@Data
public class AcademicVideosPicture {

    @Id
    @Column(name = "picture_id")
    private String pictureId;

    /**
     * 学术视频id
     */
    @Column(name = "academic_videos_id")
    private String academicVideosId;

    /**
     * url地址
     */
    @Column(name = "url")
    private String url;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态：0、正常 1、弃用
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}
