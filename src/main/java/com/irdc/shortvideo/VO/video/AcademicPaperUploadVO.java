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
 * @author chenliquan on 2021/3/12 20:30
 * Description：
 */
@Data
@ApiModel
public class AcademicPaperUploadVO {

    @ApiModelProperty(value = "学术视频id", name = "videoId")
    private String videoId;

    @Size(max = 256)
    @ApiModelProperty(value = "论文标题", name = "paperTitle")
    private String title;

    @Size(max = 256)
    @ApiModelProperty(value = "论文期刊", name = "periodical")
    private String periodical;

    @Size(max = 256)
    @ApiModelProperty(value = "论文索引", name = "index")
    private String index;

    @Size(max = 4096)
    @ApiModelProperty(value = "论文内容", name = "text")
    private String text;

    @ApiModelProperty(value = "论文发表时间", name = "publishTime")
    private Date publishTime;

    @Size(max = 256)
    @ApiModelProperty(value = "论文网址", name = "paperWebsiteUrl")
    private String websiteUrl;

    @ApiModelProperty(value = "相关论文作者", name = "paperAuthorVOList")
    private List<PaperAuthorVO> paperAuthorVOList;
}
