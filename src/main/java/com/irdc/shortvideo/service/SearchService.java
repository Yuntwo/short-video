package com.irdc.shortvideo.service;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/10/7 8:42
 * Description：
 */
public interface SearchService {
    /**
     * 创建搜索记录
     * @param userId
     * @param content
     */
    void createSearch(String userId, String content);


    /**
     * 获得10大热搜
     * @return
     */
    List<String> queryHottest6Search();
}
