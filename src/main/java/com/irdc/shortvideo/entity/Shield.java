package com.irdc.shortvideo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/10 19:16
 * Description：
 */
@Data
public class Shield {
    @Id
    private String id;

    /**
     * 执行屏蔽操作的id
     */
    @Column(name = "from_user_id")
    private String fromUserId;

    /**
     * 被屏蔽的用户id
     */
    @Column(name = "to_user_id")
    private String toUserId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}
