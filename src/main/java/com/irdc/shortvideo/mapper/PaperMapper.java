package com.irdc.shortvideo.mapper;

import com.irdc.shortvideo.entity.Paper;
import com.irdc.shortvideo.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/26 21:04
 * Description：
 */
@Mapper
public interface PaperMapper extends MyMapper<Paper> {

    Paper queryPaperByAcademicVideoId(String academicVideoId);

    boolean delPaperByAcademicVideoId(String academicVideoId);

    void updatePaper(Paper paper);

    void insertPaper(Paper paper);
}
