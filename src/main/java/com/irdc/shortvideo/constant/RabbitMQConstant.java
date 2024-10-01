package com.irdc.shortvideo.constant;

/**
 * Package short-video
 *
 * @author yangshu on 2020/10/2 18:00
 * Description：在RabbitMQ中会用到的 exchange，queue，key
 */
public interface RabbitMQConstant {

    public String EXCHANGE_VERIFY_CODE = "exchange-verify-code";


    public String QUEUE_VERIFY_CODE = "queue-verify-code";


    public String ROUTINGKEY_VERIFY_CODE = "routingKey-verify-code";


}
