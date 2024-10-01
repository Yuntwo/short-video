package com.irdc.shortvideo.rabbitmq.receive;

import com.aliyuncs.exceptions.ClientException;
import com.irdc.shortvideo.constant.RabbitMQConstant;
import com.irdc.shortvideo.utils.aliyun.SmssUtil;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Package short-video
 *
 * @author yangshu on 2020/9/30 16:19
 * Description：
 */
@Component
public class SmssReceiver {

    @Autowired
    private SmssUtil smssUtill;

    @Value("${aliyun.sms.template_code}")
    private String templateCode;

    @Value("${aliyun.sms.sign_name}")
    private String signName;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitMQConstant.QUEUE_VERIFY_CODE,durable = "true"),
            exchange = @Exchange(name = RabbitMQConstant.EXCHANGE_VERIFY_CODE, type = "topic"),
            key = RabbitMQConstant.ROUTINGKEY_VERIFY_CODE
    ))
    @RabbitHandler
    public void executeSmss(Map<String,String> map) {
        String phone = map.get("phone");
        String verifyCode = map.get("verifyCode");
        System.out.println("===============开始发送验证码==============");

        // todo : code需要根据短信的模板变化
        try{
            smssUtill.sendSms(phone,templateCode,signName,"{\"code\":\""+verifyCode+"\"}");
        }catch (ClientException e) {
            e.printStackTrace();
        }

    }
}
