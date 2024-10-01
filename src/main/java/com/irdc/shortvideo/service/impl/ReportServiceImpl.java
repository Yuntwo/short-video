package com.irdc.shortvideo.service.impl;

import com.irdc.shortvideo.VO.ReportVideoVO;
import com.irdc.shortvideo.entity.Report;
import com.irdc.shortvideo.mapper.ReportMapper;
import com.irdc.shortvideo.service.ReportService;
import com.irdc.shortvideo.utils.UUIDUtil;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/10/7 8:57
 * Description：
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createReportVideo(String userId, ReportVideoVO reportVideoVO) {
        Report report = new Report();
        BeanUtils.copyProperties(reportVideoVO,report);
        report.setId(Sid.next());
        report.setUserId(userId);

        System.out.println(report);
        reportMapper.insertSelective(report);
    }
}
