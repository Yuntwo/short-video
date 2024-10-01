package com.irdc.shortvideo.entity;

import com.irdc.shortvideo.enums.VideoIsPubilcEnum;
import com.irdc.shortvideo.enums.VideoStatusEnum;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/26 17:00
 * Description：
 */
@Data
public class AcademicVideos {
    @Id
    @Column(name = "academic_video_id")
    private String academicVideoId;

    /**
     * 发布者id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 网站url
     */
    @Column(name = "website_url")
    private String websiteUrl;

    /**
     * 视频封面路径
     */
    @Column(name = "video_cover_path")
    private String videoCoverPath;

    /**
     * 视频题目
     */
    @Column(name = "title")
    private String title;

    /**
     * 视频描述
     */
    @Column(name = "video_desc")
    private String videoDesc;

    /**
     * 视频存放的路径
     */
    @Column(name = "video_path")
    private String videoPath;

    /**
     * 喜欢/赞美的数量
     */
    @Column(name = "like_num")
    private Long likeNum;

    /**
     * 评论的数量
     */
    @Column(name = "comment_num")
    private Long commentNum;

    /**
     * 转发的数量
     */
    @Column(name = "forward_num")
    private Long forwardNum;

    /**
     * 播放的数量
     */
    @Column(name = "play_num")
    private Long playNum;

    /**
     * 视频得分
     */
    @Column(name = "score")
    private Double score;

    /**
     * 视频状态：0、正在发布 1、发布成功 2、禁止播放(管理员操作)
     */
    private Integer status = VideoStatusEnum.UPLOADING.getCode();

    /**
     * 是否公开：0、私有、不公开 1、公开
     */
    @Column(name = "is_public")
    private Integer isPublic = VideoIsPubilcEnum.PRIVATE.getCode();

    /**
     * 标签
     */
    private String tag;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}
