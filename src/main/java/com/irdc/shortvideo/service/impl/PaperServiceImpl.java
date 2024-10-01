package com.irdc.shortvideo.service.impl;

import com.irdc.shortvideo.VO.PaperAuthorVO;
import com.irdc.shortvideo.VO.video.AcademicPaperInfoVO;
import com.irdc.shortvideo.VO.video.AcademicPaperUpdateVO;
import com.irdc.shortvideo.VO.video.AcademicPaperUploadVO;
import com.irdc.shortvideo.entity.Paper;
import com.irdc.shortvideo.entity.PaperAuthor;
import com.irdc.shortvideo.enums.PaperAuthorStatusEnum;
import com.irdc.shortvideo.enums.PaperStatusEnum;
import com.irdc.shortvideo.mapper.PaperAuthorMapper;
import com.irdc.shortvideo.mapper.PaperMapper;
import com.irdc.shortvideo.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/26 21:16
 * Description：
 */
@Service
@Slf4j
public class PaperServiceImpl implements PaperService {
    @Autowired
    private PaperMapper paperMapper;

    @Autowired
    private PaperAuthorMapper paperAuthorMapper;

    @Override
    public Paper queryPaperByAcademicVideoId(String academicVideoId) {
        return paperMapper.queryPaperByAcademicVideoId(academicVideoId);
    }

    @Override
    public void updatePaper(Paper paper) {
        paperMapper.updatePaper(paper);
    }

    @Override
    public void insertPaper(Paper paper) {
        paperMapper.insertPaper(paper);
    }

    @Override
    public void insertPaperAndAuthor(AcademicPaperUploadVO academicPaperUploadVO) {

        Paper paper = new Paper();
        paper.setPaperId(Sid.next());
        paper.setAcademicVideoId(academicPaperUploadVO.getVideoId());
        paper.setTitle(academicPaperUploadVO.getTitle());
        paper.setPeriodical(academicPaperUploadVO.getPeriodical());
        paper.setText(academicPaperUploadVO.getText());
        paper.setIndex(academicPaperUploadVO.getIndex());
        paper.setPublishTime(academicPaperUploadVO.getPublishTime());
        paper.setWebsiteUrl(academicPaperUploadVO.getWebsiteUrl());
        paper.setStatus(PaperStatusEnum.NORMAL.getCode());
        paper.setCreateTime(new Date());
        paperMapper.insertPaper(paper);

        String paperId = paper.getPaperId();

        List<PaperAuthorVO> paperAuthorVOList = academicPaperUploadVO.getPaperAuthorVOList();
        if(paperAuthorVOList != null && !paperAuthorVOList.isEmpty()){
            for(PaperAuthorVO paperAuthorVO : paperAuthorVOList){
                PaperAuthor paperAuthor = new PaperAuthor();
                BeanUtils.copyProperties(paperAuthorVO, paperAuthor);
                paperAuthor.setAuthorId(Sid.next());
                paperAuthor.setStatus(PaperAuthorStatusEnum.NORMAL.getCode());
                paperAuthor.setPaperId(paperId);
                paperAuthorMapper.insertPaperAuthor(paperAuthor);
            }
        }
    }


    @Override
    public void updatePaperAndAuthor(AcademicPaperUpdateVO academicPaperUpdateVO) {

        Paper paper = queryPaperByAcademicVideoId(academicPaperUpdateVO.getVideoId());
        if(paper != null) {
            String paperId = paper.getPaperId();
            paper.setTitle(academicPaperUpdateVO.getPaperTitle());
            paper.setPeriodical(academicPaperUpdateVO.getPaperPeriodical());
            paper.setText(academicPaperUpdateVO.getText());
            paper.setIndex(academicPaperUpdateVO.getPaperIndex());
            paper.setPublishTime(academicPaperUpdateVO.getPaperPublishTime());
            paper.setWebsiteUrl(academicPaperUpdateVO.getPaperWebsiteUrl());

            System.out.println("paper: " + paper);

//            Example example = new Example(Paper.class);
//            Example.Criteria criteria = example.createCriteria();
//            criteria.andEqualTo("paperId", paperId);
//            paperMapper.updateByExampleSelective(paper, example);
            paperMapper.updatePaper(paper);

            //删除原有的作者信息
            paperAuthorMapper.updateStatusByPaperId(paperId, PaperAuthorStatusEnum.FORBID.getCode());

            List<PaperAuthorVO> paperAuthorVOList = academicPaperUpdateVO.getPaperAuthorVOList();
            if(paperAuthorVOList != null && !paperAuthorVOList.isEmpty()){
                for(PaperAuthorVO paperAuthorVO : paperAuthorVOList){
                    PaperAuthor paperAuthor = new PaperAuthor();
                    BeanUtils.copyProperties(paperAuthorVO, paperAuthor);
                    paperAuthor.setAuthorId(Sid.next());
                    paperAuthor.setStatus(PaperAuthorStatusEnum.NORMAL.getCode());
                    paperAuthor.setPaperId(paperId);
                    paperAuthorMapper.insertPaperAuthor(paperAuthor);
                }
            }

        }
    }


    @Override
    public AcademicPaperInfoVO queryPaperAndAuthor(String videoId) {
        AcademicPaperInfoVO academicPaperInfoVO = new AcademicPaperInfoVO();

        Paper paper = paperMapper.queryPaperByAcademicVideoId(videoId);


        if(paper != null){
            BeanUtils.copyProperties(paper, academicPaperInfoVO);

            String paperId = paper.getPaperId();

            List<PaperAuthorVO> paperAuthorVOList= paperAuthorMapper.queryPaperAuthorVOByPaperId(paperId);

            if(paperAuthorVOList != null && !paperAuthorVOList.isEmpty()){
                academicPaperInfoVO.setPaperAuthorVOList(paperAuthorVOList);
            }
        }

        return academicPaperInfoVO;
    }
}
