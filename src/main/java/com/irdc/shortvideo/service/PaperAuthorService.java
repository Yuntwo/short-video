package com.irdc.shortvideo.service;

import com.irdc.shortvideo.entity.PaperAuthor;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/26 21:16
 * Description：
 */
public interface PaperAuthorService {

    List<PaperAuthor> queryPaperAuthorByPaperId(String paperId);

    void updatePaperAuthor(PaperAuthor paperAuthor);

    void insertPaperAuthor(PaperAuthor paperAuthor);

    void setAuthorStatusByPaperId(String paperId, Integer status);
}
