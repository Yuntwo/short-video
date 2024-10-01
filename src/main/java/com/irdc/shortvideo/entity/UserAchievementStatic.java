package com.irdc.shortvideo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/15 19:50
 * Description：
 */
@Data
public class UserAchievementStatic {
    @Id
    @Column(name = "id")
    private String id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 成就id
     */
    @Column(name = "achievement_id")
    private String achievementId;

    /**
     * 成就状态：0、正常 1、弃用
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}
