package com.irdc.shortvideo.entity;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "user_like_comment")
public class UserLikeComment {
    @Id
    private String id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 评论id
     */
    @Column(name = "comment_id")
    private String commentId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}