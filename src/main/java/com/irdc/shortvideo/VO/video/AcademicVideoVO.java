package com.irdc.shortvideo.VO.video;

import com.irdc.shortvideo.VO.PaperAuthorVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/26 20:42
 * Description：
 */
@Data
@ApiModel
public class AcademicVideoVO {

    @ApiModelProperty(value = "学术视频id", name = "academicVideoId")
    private String academicVideoId;

    @ApiModelProperty(value = "用户id", name = "userId")
    private String userId;

    @ApiModelProperty(value = "视频封面", name = "video_cover_path")
    private String videoCoverPath;

    @ApiModelProperty(value = "视频标题", name = "title")
    private String title;

    @ApiModelProperty(value = "视频简介", name = "videoDesc")
    private String videoDesc;

    @ApiModelProperty(value = "视频存放路径", name = "videoPath")
    private String videoPath;

    @ApiModelProperty(value = "视频相关网站", name = "websiteUrl")
    private String websiteUrl;

    @ApiModelProperty(value = "用户昵称", name = "username")
    private String username;

    @ApiModelProperty(value = "用户头像", name = "faceImage")
    private String faceImage;

    @ApiModelProperty(value = "视频点赞数", name = "likeNum")
    private Long likeNum;

    @ApiModelProperty(value = "视频评论数", name = "commentNum")
    private Long commentNum;

    @ApiModelProperty(value = "视频转发数量", name = "forwardNum")
    private Long forwardNum;

    @ApiModelProperty(value = "视频播放数量", name = "playNum")
    private Long playNum;

    @ApiModelProperty(value = "视频状态", name = "status")
    private Integer status;

    @ApiModelProperty(value = "视频是否公开", name = "isPublic")
    private Integer isPublic;

    @ApiModelProperty(value = "视频标签", name = "tag")
    private String tag;

    @ApiModelProperty(value = "视频创建时间", name = "createTime")
    private Date createTime;

    @ApiModelProperty(value = "论文标题", name = "paperTitle")
    private String paperTitle;

    @ApiModelProperty(value = "论文期刊", name = "periodical")
    private String paperPeriodical;

    @ApiModelProperty(value = "论文索引", name = "index")
    private String paperIndex;

    @ApiModelProperty(value = "论文发表时间", name = "publishTime")
    private Date paperPublishTime;

    @ApiModelProperty(value = "论文网址", name = "paperWebsiteUrl")
    private String paperWebsiteUrl;

    @ApiModelProperty(value = "相关论文作者", name = "paperAuthorVOList")
    private List<PaperAuthorVO> paperAuthorVOList;

}
