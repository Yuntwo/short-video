package com.irdc.shortvideo.service;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/10/7 16:30
 * Description：
 */
public interface TagService {

    /**
     * 查询所有的视频分类的标签
     * @return
     */
    List<String> queryAllVideoTag();

    /**
     * 查询所有的举报的标签
     * @return
     */
    List<String> queryAllReportTag();
}
