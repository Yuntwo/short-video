package com.irdc.shortvideo.service.impl;

import com.irdc.shortvideo.entity.Search;
import com.irdc.shortvideo.mapper.SearchMapper;
import com.irdc.shortvideo.service.SearchService;
import com.irdc.shortvideo.utils.UUIDUtil;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/10/7 8:43
 * Description：
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchMapper searchMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createSearch(String userId, String content) {
        Search search = new Search();
//        search.setId(UUIDUtil.getId());
        search.setId(Sid.next());
        search.setUserId(userId);
        search.setContent(content);
        searchMapper.insertSelective(search);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<String> queryHottest6Search() {
        return searchMapper.queryHottest6Search();
    }
}
