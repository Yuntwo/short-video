package com.irdc.shortvideo.mapper;

import com.irdc.shortvideo.entity.Search;
import com.irdc.shortvideo.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SearchMapper extends MyMapper<Search> {

    /**
     * 查询前6热搜
     * @return
     */
    List<String> queryHottest6Search();
}