package com.irdc.shortvideo.service.impl;

import com.irdc.shortvideo.entity.PaperAuthor;
import com.irdc.shortvideo.mapper.PaperAuthorMapper;
import com.irdc.shortvideo.service.PaperAuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/26 21:16
 * Description：
 */
@Service
@Slf4j
public class PaperAuthorServiceImpl implements PaperAuthorService {

    @Autowired
    private PaperAuthorMapper paperAuthorMapper;

    @Override
    public List<PaperAuthor> queryPaperAuthorByPaperId(String paperId) {
//        Example example = new Example(PaperAuthor.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("authorId", paperId);
//        return paperAuthorMapper.selectByExample(example);
        return paperAuthorMapper.queryPaperAuthorByPaperId(paperId);
    }

    @Override
    public void updatePaperAuthor(PaperAuthor paperAuthor) {
//        Example example = new Example(PaperAuthor.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("authorId", paperAuthor.getAuthorId());
//        paperAuthorMapper.updateByExampleSelective(paperAuthor, example);
        paperAuthorMapper.updatePaperAuthor(paperAuthor);
    }

    @Override
    public void insertPaperAuthor(PaperAuthor paperAuthor) {
//        paperAuthorMapper.insert(paperAuthor);
        paperAuthorMapper.insertPaperAuthor(paperAuthor);
    }

    @Override
    public void setAuthorStatusByPaperId(String paperId, Integer status) {
        paperAuthorMapper.updateStatusByPaperId(paperId, status);
    }
}
