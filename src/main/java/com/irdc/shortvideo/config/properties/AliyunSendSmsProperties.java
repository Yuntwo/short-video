package com.irdc.shortvideo.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Package short-video
 *
 * @author yangshu on 2020/9/30 11:37
 * Description: application.yml中阿里云的配置信息
 */
@Component
@Data
public class AliyunSendSmsProperties {

    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.sms.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.sms.template_code}")
    private String templateCode;

    @Value("${aliyun.sms.sign_name}")
    private String signName;
}
