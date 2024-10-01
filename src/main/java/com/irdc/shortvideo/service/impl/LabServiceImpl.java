package com.irdc.shortvideo.service.impl;

import com.irdc.shortvideo.VO.LabVO;
import com.irdc.shortvideo.mapper.LabMapper;
import com.irdc.shortvideo.service.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/26 14:34
 * Description：
 */
@Service
public class LabServiceImpl implements LabService {

    @Autowired
    private LabMapper labMapper;

    @Override
    public List<LabVO> queryAllLab() {
        return labMapper.queryAllLab();
    }
}
