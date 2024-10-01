package com.irdc.shortvideo.mapper;

import com.irdc.shortvideo.VO.PaperAuthorVO;
import com.irdc.shortvideo.entity.PaperAuthor;
import com.irdc.shortvideo.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/26 21:05
 * Description：
 */
@Mapper
public interface PaperAuthorMapper extends MyMapper<PaperAuthor> {


    List<PaperAuthor> queryPaperAuthorByPaperId(String paperId);

    List<PaperAuthorVO> queryPaperAuthorVOByPaperId(String paperId);

    boolean delPaperAuthorByPaperId(String paperId);

    void updatePaperAuthor(PaperAuthor paperAuthor);

    void insertPaperAuthor(PaperAuthor paperAuthor);

    void updateStatusByPaperId(String paperId, Integer status);

}
