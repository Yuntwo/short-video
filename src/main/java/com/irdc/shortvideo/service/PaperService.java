package com.irdc.shortvideo.service;

import com.irdc.shortvideo.VO.video.AcademicPaperInfoVO;
import com.irdc.shortvideo.VO.video.AcademicPaperUpdateVO;
import com.irdc.shortvideo.VO.video.AcademicPaperUploadVO;
import com.irdc.shortvideo.VO.video.AcademicVideoUpdateVO;
import com.irdc.shortvideo.entity.Paper;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/26 21:15
 * Description：
 */
public interface PaperService {

    /**
     * 根据academicVideoId查询Paper
     * @param academicVideoId
     * @return
     */
    Paper queryPaperByAcademicVideoId(String academicVideoId);

    /**
     * 更新paper表
     * @param paper
     */
    void updatePaper(Paper paper);

    /**
     * 插入paper表
     * @param paper
     */
    void insertPaper(Paper paper);

    /**
     * 插入paper和paper_author表
     */
    void insertPaperAndAuthor(AcademicPaperUploadVO academicPaperUploadVO);

    /**
     * 更新paper和paper_author表
     */
    void updatePaperAndAuthor(AcademicPaperUpdateVO academicPaperUpdateVO);

    /**
     * 查询论文和作者
     * @param videoId
     * @return
     */
    AcademicPaperInfoVO queryPaperAndAuthor(String videoId);


}
