package com.irdc.shortvideo.VO.video;

import com.irdc.shortvideo.VO.PaperAuthorVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/12 21:56
 * Description：
 */

@Data
@ApiModel
public class AcademicPaperUpdateVO {

    @Size(max = 64)
    @ApiModelProperty(value = "学术视频id", name = "videoId")
    private String videoId;

    @Size(max = 256)
    @ApiModelProperty(value = "论文标题", name = "paperTitle")
    private String paperTitle;

    @Size(max = 256)
    @ApiModelProperty(value = "论文期刊", name = "periodical")
    private String paperPeriodical;

    @Size(max = 256)
    @ApiModelProperty(value = "论文索引", name = "index")
    private String paperIndex;

    @Size(max = 4096)
    @ApiModelProperty(value = "论文内容", name = "text")
    private String text;

    @ApiModelProperty(value = "论文发表时间", name = "publishTime")
    private Date paperPublishTime;

    @Size(max = 256)
    @ApiModelProperty(value = "论文网址", name = "paperWebsiteUrl")
    private String paperWebsiteUrl;

    @ApiModelProperty(value = "相关论文作者", name = "paperAuthorVOList")
    private List<PaperAuthorVO> paperAuthorVOList;
}
