package com.irdc.shortvideo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.irdc.shortvideo.VO.PagedResultVO;
import com.irdc.shortvideo.VO.PaperAuthorVO;
import com.irdc.shortvideo.VO.VideoWithUserVO;
import com.irdc.shortvideo.VO.video.AcademicVideoUpdateVO;
import com.irdc.shortvideo.VO.video.AcademicVideoVO;
import com.irdc.shortvideo.VO.video.AcademicVideoWithUserVO;
import com.irdc.shortvideo.entity.*;
import com.irdc.shortvideo.enums.*;
import com.irdc.shortvideo.mapper.AcademicVideosMapper;
import com.irdc.shortvideo.mapper.PaperAuthorMapper;
import com.irdc.shortvideo.mapper.PaperMapper;
import com.irdc.shortvideo.service.*;
import lombok.extern.slf4j.Slf4j;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/26 20:12
 * Description：
 */

@Slf4j
@Service
public class AcademicVideosServiceImpl implements AcademicVideosService {

    @Autowired
    private AcademicVideosMapper academicVideosMapper;

    @Autowired
    private PaperMapper paperMapper;

    @Autowired
    private PaperAuthorMapper paperAuthorMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private PaperService paperService;

    @Autowired
    private PaperAuthorService paperAuthorService;


    @Override
    public AcademicVideoVO queryAcademicVideoVOById(String academicVideoId) {
        AcademicVideoVO academicVideoVO = new AcademicVideoVO();


        AcademicVideos academicVideos = academicVideosMapper.queryAcademicVideoByVideoIdAndStatusAndIsPublic(academicVideoId, VideoStatusEnum.SUCCESS.getCode(), VideoIsPubilcEnum.PUBLIC.getCode());
        if(academicVideos == null){
            return null;
        }

        BeanUtils.copyProperties(academicVideos, academicVideoVO);


        Paper paper = paperMapper.queryPaperByAcademicVideoId(academicVideoId);
        if(paper != null){
            academicVideoVO.setPaperTitle(paper.getTitle());
            academicVideoVO.setPaperIndex(paper.getIndex());
            academicVideoVO.setPaperPeriodical(paper.getPeriodical());
            academicVideoVO.setPaperPublishTime(paper.getPublishTime());
            academicVideoVO.setPaperWebsiteUrl(paper.getWebsiteUrl());

            String paperId = paper.getPaperId();

            List<PaperAuthorVO> paperAuthorVOList = paperAuthorMapper.queryPaperAuthorVOByPaperId(paperId);
            if(paperAuthorVOList != null && !paperAuthorVOList.isEmpty()){
                academicVideoVO.setPaperAuthorVOList(paperAuthorVOList);
            }
        }



        String userId = academicVideos.getUserId();

        if(!userService.islegal(userId)){
            return null;
        }

        Users users = userService.queryUserInfoByUserId(userId);
        if(users != null) {
            academicVideoVO.setUsername(users.getUsername());
            academicVideoVO.setFaceImage(users.getFaceImage());
        }

        return academicVideoVO;
    }

    @Override
    public AcademicVideoWithUserVO queryAcademicVideoWithUserVOByVideoId(String videoId) {
        return academicVideosMapper.queryAcademicVideoWithUserVOByVideoId(videoId);
    }

    @Override
    public PagedResultVO queryRecommendAcademicVideoWithUserVO(Integer page, Integer size) {
        PageHelper.startPage(page,size);

        List<AcademicVideoWithUserVO> academicVideoWithUserVOList = academicVideosMapper.queryRecommendAcademicVideoWithUserVO();

        return pageHelper2(page, academicVideoWithUserVOList);
    }

    @Override
    public AcademicVideos queryAcademicVideoByVideoIdAndStatusAndIsPublic(String academicVideoId, Integer status, Integer isPublic) {
        return academicVideosMapper.queryAcademicVideoByVideoIdAndStatusAndIsPublic(academicVideoId, status, isPublic);
    }

    @Override
    public AcademicVideos queryAcademicVideoByStatus(String academicVideoId, Integer status) {
        return academicVideosMapper.queryAcademicVideoByVideoIdAndStatus(academicVideoId, status);
    }

    @Override
    public AcademicVideos queryAcademicVideoByVideoId(String academicVideoId) {
        return academicVideosMapper.queryAcademicVideoByVideoId(academicVideoId);
    }

    @Override
    public PagedResultVO queryRecommendVideos(Integer page, Integer size) {
        PageHelper.startPage(page,size);

        List<AcademicVideos> academicVideosList = academicVideosMapper.getRecommendAcademicVideo();
        List<AcademicVideoVO> academicVideoVOList = new ArrayList<>();

        if(academicVideosList == null || academicVideosList.isEmpty()){
            return null;
        }

        for(AcademicVideos academicVideos : academicVideosList) {
            AcademicVideoVO academicVideoVO = new AcademicVideoVO();
            if (academicVideos == null) {
                continue;
            }
            String userId = academicVideos.getUserId();

            if (!userService.islegal(userId)) {
                continue;
            }

            BeanUtils.copyProperties(academicVideos, academicVideoVO);
            Paper paper = paperMapper.queryPaperByAcademicVideoId(academicVideos.getAcademicVideoId());
            if(paper != null){
                academicVideoVO.setPaperTitle(paper.getTitle());
                academicVideoVO.setPaperIndex(paper.getIndex());
                academicVideoVO.setPaperPeriodical(paper.getPeriodical());
                academicVideoVO.setPaperPublishTime(paper.getPublishTime());
                academicVideoVO.setPaperWebsiteUrl(paper.getWebsiteUrl());

                String paperId = paper.getPaperId();
                List<PaperAuthorVO> paperAuthorVOList = paperAuthorMapper.queryPaperAuthorVOByPaperId(paperId);
                if(paperAuthorVOList != null && !paperAuthorVOList.isEmpty()){
                    academicVideoVO.setPaperAuthorVOList(paperAuthorVOList);
                }
            }

            

            Users users = userService.queryUserInfoByUserId(userId);
            if(users != null) {
                academicVideoVO.setUsername(users.getUsername());
                academicVideoVO.setFaceImage(users.getFaceImage());
            }

            if (academicVideoVO != null) {
                academicVideoVOList.add(academicVideoVO);
            }

        }
        return pageHelper(page,academicVideoVOList);

    }

    private  PagedResultVO pageHelper(Integer page,List<AcademicVideoVO> academicVideoVOList) {

        for(AcademicVideoVO v: academicVideoVOList){
            v.setLikeNum(likeService.queryLikeNumByVideoId(v.getAcademicVideoId(), VideoTypeEnum.ACADEMIC_VIDEO.getCode()));
        }

        PageInfo<AcademicVideoVO> pageList = new PageInfo<>(academicVideoVOList);

        PagedResultVO pagedResult = new PagedResultVO();

        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRecords(pageList.getTotal());
        pagedResult.setVideos(academicVideoVOList);
        return pagedResult;
    }

    private  PagedResultVO pageHelper2(Integer page,List<AcademicVideoWithUserVO> academicVideoWithUserVOList) {

        for(AcademicVideoWithUserVO v : academicVideoWithUserVOList){
            v.setLikeNum(likeService.queryLikeNumByVideoId(v.getVideoId(), VideoTypeEnum.ACADEMIC_VIDEO.getCode()));
        }

        PageInfo<AcademicVideoWithUserVO> pageList = new PageInfo<>(academicVideoWithUserVOList);

        PagedResultVO pagedResult = new PagedResultVO();

        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRecords(pageList.getTotal());
        pagedResult.setVideos(academicVideoWithUserVOList);
        return pagedResult;
    }


    @Override
    public void updateAcademicVideo(AcademicVideos academicVideos) {
        Example example = new Example(AcademicVideos.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("academicVideoId", academicVideos.getAcademicVideoId());
        academicVideosMapper.updateByExampleSelective(academicVideos, example);
    }

    @Override
    public void delAcademicVideos(AcademicVideos academicVideos) {
        academicVideos.setStatus(VideoStatusEnum.DELETE.getCode());
        updateAcademicVideo(academicVideos);

//        删除学术视频相关的论文信息
//        Paper paper = paperService.queryPaperByAcademicVideoId(academicVideos.getAcademicVideoId());
//        if(paper != null){
//            paper.setStatus(PaperStatusEnum.FORBID.getCode());
//            paperService.updatePaper(paper);
//
//            String paperId = paper.getPaperId();
//            List<PaperAuthor> paperAuthorList = paperAuthorService.queryPaperAuthorByPaperId(paperId);
//            if(paperAuthorList != null && !paperAuthorList.isEmpty()) {
//                for (PaperAuthor paperAuthor : paperAuthorList) {
//                    paperAuthor.setStatus(PaperStatusEnum.FORBID.getCode());
//                    paperAuthorService.updatePaperAuthor(paperAuthor);
//                }
//            }
//        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createAcademicVideos(AcademicVideos academicVideos) {
        academicVideosMapper.insertSelective(academicVideos);
    }

//    @Override
//    public void updateAcademicVideosWithPaper(AcademicVideoUpdateVO academicVideoUpdateVO) {
//        String academicVideoId = academicVideoUpdateVO.getVideoId();
//
//        AcademicVideos academicVideos = queryAcademicVideoByStatus(academicVideoId, VideoStatusEnum.SUCCESS.getCode());
//        BeanUtils.copyProperties(academicVideoUpdateVO,academicVideos);
//        academicVideos.setVideoDesc(academicVideoUpdateVO.getDescription());
//        updateAcademicVideo(academicVideos);
//
//
//        //删除原paper和paper_author表中数据
//        Paper paper = paperService.queryPaperByAcademicVideoId(academicVideoId);
//        if(paper != null){
//            String paperId = paper.getPaperId();
//
//            paper.setStatus(PaperStatusEnum.FORBID.getCode());
////            System.out.println("update paper: " + paper);
//            paperService.updatePaper(paper);
//
//            List<PaperAuthor> paperAuthorList = paperAuthorService.queryPaperAuthorByPaperId(paperId);
//            if(paperAuthorList != null && !paperAuthorList.isEmpty()){
//                for(PaperAuthor paperAuthor : paperAuthorList){
//                    paperAuthor.setStatus(PaperAuthorStatusEnum.FORBID.getCode());
//                    paperAuthorService.updatePaperAuthor(paperAuthor);
//                }
//            }
//        }
//
//        //向paper和paper_author表中插入新数据
//        Paper newPaper = new Paper();
//        newPaper.setPaperId(Sid.next());
//        newPaper.setAcademicVideoId(academicVideoId);
//        newPaper.setTitle(academicVideoUpdateVO.getPaperTitle());
//        newPaper.setPeriodical(academicVideoUpdateVO.getPaperPeriodical());
//        newPaper.setIndex(academicVideoUpdateVO.getPaperIndex());
//        newPaper.setPublishTime(academicVideoUpdateVO.getPaperPublishTime());
//        newPaper.setWebsiteUrl(academicVideoUpdateVO.getPaperWebsiteUrl());
//        newPaper.setStatus(PaperStatusEnum.NORMAL.getCode());
//        paperService.insertPaper(newPaper);
//
//
//        String newPaperId = newPaper.getPaperId();
//
//        List<PaperAuthorVO> paperAuthorVOList = academicVideoUpdateVO.getPaperAuthorVOList();
//        if(paperAuthorVOList != null && !paperAuthorVOList.isEmpty()){
//            for(PaperAuthorVO paperAuthorVO : paperAuthorVOList){
//                PaperAuthor paperAuthor = new PaperAuthor();
//                BeanUtils.copyProperties(paperAuthorVO, paperAuthor);
//                paperAuthor.setAuthorId(Sid.next());
//                paperAuthor.setStatus(PaperAuthorStatusEnum.NORMAL.getCode());
//                paperAuthor.setPaperId(newPaperId);
//                paperAuthorService.insertPaperAuthor(paperAuthor);
//            }
//        }
//
//
//    }


    @Override
    public AcademicVideos findOneAcademicVideoByVideoId(String videoId) {
        Example example = new Example(AcademicVideos.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("academicVideoId",videoId);
        AcademicVideos academicVideos = academicVideosMapper.selectOneByExample(example);
        return academicVideos;
    }

    @Override
    public void computeScoreForAcademicVideos() {
        List<AcademicVideos> academicVideosList = academicVideosMapper.queryAllVideos();
        for(AcademicVideos academicVideos : academicVideosList){
            double timeScore = 0d;
            double timeParam = 0.001d;
            double likeScore = 0d;
            double likeParam = 0.001d;
            double score = 0d;
            double commonParam = 100d;

            long likeNum = academicVideos.getLikeNum();

            //计算时间加权分数
            long createTime = academicVideos.getCreateTime().getTime();
            long nowTime = System.currentTimeMillis();
            long timeInterval = (nowTime - createTime) / (1000 * 60 * 60);


            timeScore = Math.exp(- timeInterval * timeParam);
            likeScore = 2 - Math.exp(- likeNum * likeParam);

            score = commonParam * timeScore * likeScore;

//            if(timeInterval < 1440){
//                timeScore = 100d;
//            }else if(timeInterval < 1440 * 3){
//                timeScore = 80d;
//            }else if(timeInterval < 1440 * 7){
//                timeScore = 60d;
//            }else if(timeInterval < 1440 * 15){
//                timeScore = 50d;
//            }else{
//                timeScore = 40d;
//            }
//
//            //计算点赞数加权分数
//            long likeNum = academicVideos.getLikeNum();
//            if(likeNum <= 0){
//                likeScore = 0d;
//            }else if(likeNum <= 3){
//                likeScore = 20d;
//            }else if(likeNum <= 5){
//                likeScore = 30d;
//            }else if(likeNum <= 10){
//                likeScore = 40d;
//            }else if(likeNum <= 20){
//                likeScore = 50d;
//            }else if(likeNum <= 50){
//                likeScore = 60d;
//            }else if(likeNum < 100){
//                likeScore = 80d;
//            }else if(likeNum < 500){
//                likeScore = 100d;
//            }else{
//                likeScore = 150d;
//            }
//
//            score = timeScore + likeScore;

            academicVideosMapper.updateScoreByAcademicVideoId(academicVideos.getAcademicVideoId(), score);

        }
    }
}
