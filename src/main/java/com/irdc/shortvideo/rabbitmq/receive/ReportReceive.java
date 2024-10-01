package com.irdc.shortvideo.rabbitmq.receive;

import com.irdc.shortvideo.VO.ReportVideoVO;
import com.irdc.shortvideo.config.rabbitmq.MQConfig;
import com.irdc.shortvideo.rabbitmq.message.ReportMessage;
import com.irdc.shortvideo.service.ReportService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/12 15:56
 * Description：
 */
@Service
public class ReportReceive {
    @Autowired
    private ReportService reportService;

//    @UserAuthentication(pass = true, role = AuthAopEnum.USER)
    @RabbitHandler
    @RabbitListener(queues = MQConfig.REPORT_QUEUE)
    public void processReport(ReportMessage reportMessage){
        ReportVideoVO reportVideoVO = new ReportVideoVO();
        String userId = reportMessage.getUserId();
        reportVideoVO.setContent(reportMessage.getContent());
        reportVideoVO.setTag(reportMessage.getTag());
        reportVideoVO.setVideoId(reportMessage.getVideoId());
        reportService.createReportVideo(userId,reportVideoVO);
    }
}
