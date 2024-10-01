package com.irdc.shortvideo.mapper;

import com.irdc.shortvideo.entity.ReportTag;
import com.irdc.shortvideo.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/10/7 16:23
 * Description：
 */
@Mapper
public interface ReportTagMapper extends MyMapper<ReportTag> {

    /**
     * 查找所有举报的tag
     * @return
     */
    List<String> queryAllReportTag();
}
