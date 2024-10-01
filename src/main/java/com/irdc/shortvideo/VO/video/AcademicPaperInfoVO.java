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
 * @author chenliquan on 2021/3/13 0:19
 * Description：
 */
@Data
@ApiModel
public class AcademicPaperInfoVO {

    @ApiModelProperty(value = "论文标题", name = "paperTitle")
    private String title;

    @ApiModelProperty(value = "论文期刊", name = "periodical")
    private String periodical;

    @ApiModelProperty(value = "论文索引", name = "index")
    private String index;

    @ApiModelProperty(value = "论文内容", name = "text")
    private String text;

    @ApiModelProperty(value = "论文发表时间", name = "publishTime")
    private Date publishTime;

    @ApiModelProperty(value = "论文网址", name = "websiteUrl")
    private String websiteUrl;

    @ApiModelProperty(value = "相关论文作者", name = "paperAuthorVOList")
    private List<PaperAuthorVO> paperAuthorVOList;

    @ApiModelProperty(value = "创建时间", name = "createTime")
    private Date createTime;
}
