package com.irdc.shortvideo.utils.aliyun;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.irdc.shortvideo.config.properties.AliyunSendSmsProperties;
import com.irdc.shortvideo.constant.RabbitMQConstant;
import com.irdc.shortvideo.constant.RedisConstant;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Package short-video
 *
 * @author yangshu on 2020/9/30 16:15
 * Description：阿里云短信工具类
 */
@Configuration
public class SmssUtil {

    /**
     * 产品名称:云通信短信API产品,开发者无需替换
     */
    static final String product = "Dysmsapi";

    /**
     * 产品域名,开发者无需替换
     */
    static final String domain = "dysmsapi.aliyuncs.com";


    private static final String regionId = "cn-hangzhou";


    @Autowired
    private AliyunSendSmsProperties aliyunSendSmsProperties;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    public SendSmsResponse sendSms(String phone,String templateCode,String signName,String verifyCode) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        String accessKeyId = aliyunSendSmsProperties.getAccessKeyId();
        String accessKeySecret = aliyunSendSmsProperties.getAccessKeySecret();

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint(regionId, regionId, product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //  {"code":verifyCode} 测试的时候只能为 code 否则会发不出短信
        request.setTemplateParam(verifyCode);

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }

    public QuerySendDetailsResponse querySendDetails(String phone, String bizId) throws ClientException {
        String accessKeyId = aliyunSendSmsProperties.getAccessKeyId();
        String accessKeySecret = aliyunSendSmsProperties.getAccessKeySecret();
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber(phone);
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);
        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);
        return querySendDetailsResponse;
    }

    public void sendSms(String phone,String redisPrefix) {

        // 1. redis中是否有用户的验证信息
        String redisVerifyCode = redisTemplate.opsForValue().get(String.format(redisPrefix, phone));

        if(StringUtils.isEmpty(redisVerifyCode)) {
            // 2. 没有，生成新的验证码
            redisVerifyCode = RandomStringUtils.randomNumeric(6);
            //存到redis中
            redisTemplate.opsForValue().set(String.format(redisPrefix, phone), redisVerifyCode, RedisConstant.EXPIRE_VERIFY_CODE, TimeUnit.SECONDS);
        }else{
            // 更新redis中的过期时间
            redisTemplate.expire(String.format(redisPrefix, phone),RedisConstant.EXPIRE_VERIFY_CODE, TimeUnit.SECONDS);
        }

        //给用户发一份
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("verifyCode", redisVerifyCode);
        amqpTemplate.convertAndSend(RabbitMQConstant.EXCHANGE_VERIFY_CODE, RabbitMQConstant.ROUTINGKEY_VERIFY_CODE, map);
    }
}
