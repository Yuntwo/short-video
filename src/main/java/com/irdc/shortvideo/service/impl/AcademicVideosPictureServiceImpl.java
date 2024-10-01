package com.irdc.shortvideo.service.impl;

import com.irdc.shortvideo.VO.video.AcademicPictureInfoVO;
import com.irdc.shortvideo.VO.video.AcademicPictureUploadVO;
import com.irdc.shortvideo.entity.AcademicVideosPicture;
import com.irdc.shortvideo.mapper.AcademicVideosPictureMapper;
import com.irdc.shortvideo.service.AcademicVideosPictureService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/11 16:24
 * Description：
 */

@Service
public class AcademicVideosPictureServiceImpl implements AcademicVideosPictureService {

    @Autowired
    private AcademicVideosPictureMapper academicVideosPictureMapper;


    @Override
    public void createAcademicVideosPicture(AcademicPictureUploadVO academicPictureUploadVO, String url) {
        AcademicVideosPicture academicVideosPicture = new AcademicVideosPicture();
        BeanUtils.copyProperties(academicPictureUploadVO, academicVideosPicture);
        academicVideosPicture.setAcademicVideosId(academicPictureUploadVO.getAcademicVideoId());
        academicVideosPicture.setPictureId(Sid.next());
        academicVideosPicture.setCreateTime(new Date());
        academicVideosPicture.setUrl(url);
        academicVideosPicture.setStatus(0);

        academicVideosPictureMapper.insertSelective(academicVideosPicture);

    }


    @Override
    public void delAllAcademicVideoPicturesByVideoId(String videoId) {
        academicVideosPictureMapper.updateAllPictureStatusByVideoId(videoId, 1);
    }

    @Override
    public List<AcademicPictureInfoVO> queryAcademicPictureInfoVOByVideoId(String videoId) {
        return academicVideosPictureMapper.queryAcademicPictureInfoVOByVideoId(videoId);
    }
}
