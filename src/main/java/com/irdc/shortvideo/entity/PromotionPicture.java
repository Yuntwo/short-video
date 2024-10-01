package com.irdc.shortvideo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/20 9:32
 * Description：
 */
@Data
public class PromotionPicture {
    @Id
    private String id;

    /**
     * 图片url地址
     */
    private String pictureUrl;

    /**
     * 新闻url
     */
    private String newsUrl;

    /**
     * 举报处理状态：0、正常 1、弃用
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}
