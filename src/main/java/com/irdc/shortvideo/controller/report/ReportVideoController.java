package com.irdc.shortvideo.controller.report;


import com.irdc.shortvideo.VO.ReportVideoVO;
import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.annotation.UserAuthentication;
import com.irdc.shortvideo.config.rabbitmq.MQConfig;
import com.irdc.shortvideo.enums.AuthAopEnum;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.rabbitmq.message.ReportMessage;
import com.irdc.shortvideo.service.ReportService;
import com.irdc.shortvideo.service.TagService;
import com.irdc.shortvideo.utils.RedisUtil;
import com.irdc.shortvideo.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Package short-video
 * Created by chenliquan on 2020/9/30 20:10
 * Description：
 */

@Api(value = "举报接口",tags = {"举报"})
@RestController
@RequestMapping("/report")
@Slf4j
public class ReportVideoController {
    @Autowired
    private ReportService reportService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TagService tagService;

    @Autowired
    private AmqpTemplate amqpTemplate;


    @UserAuthentication(pass = true, role = AuthAopEnum.USER)
    @ApiOperation(value = "举报视频",notes = "/report/video")
    @PostMapping("/video")
    public ResultVO reportVideo(@RequestBody ReportVideoVO reportVideoVO, HttpServletRequest request) {
        if(StringUtils.isEmpty(reportVideoVO.getTag())|| StringUtils.isEmpty(reportVideoVO.getVideoId())
             || StringUtils.isEmpty((reportVideoVO.getContent() + ""))) {
            log.error("【举报视频】传入的参数不正确，ReportVideoVO: {}",reportVideoVO);
            return ResultVOUtil.error(ResultEnum.REPORT_CONTENT_IS_INCOMPLETE.getCode(),ResultEnum.REPORT_CONTENT_IS_INCOMPLETE.getMessage());
        }
        String userId = redisUtil.getUserIdByToken(request);

        ReportMessage reportMessage = new ReportMessage();
        reportMessage.setContent(reportVideoVO.getContent());
        reportMessage.setTag(reportVideoVO.getTag());
        reportMessage.setUserId(userId);
        reportMessage.setVideoId(reportVideoVO.getVideoId());

        //发送到消息队列
        amqpTemplate.convertAndSend(MQConfig.REPORT_DIRECT_EXCHANGE,MQConfig.ROUTINGKEY1,reportMessage);
//        reportService.createReportVideo(userId,reportVideoVO);
        return ResultVOUtil.successMsg(ResultEnum.REPORT_SUCCESS.getMessage());
    }



    @UserAuthentication(pass = true, role = AuthAopEnum.USER)
    @ApiOperation(value = "获得举报标签",notes = "/report/tag")
    @GetMapping("/tag")
    public ResultVO reportTag() {
        List<String> result = tagService.queryAllReportTag();

        return ResultVOUtil.success(result);
    }


}