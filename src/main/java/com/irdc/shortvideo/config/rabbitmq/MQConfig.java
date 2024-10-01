package com.irdc.shortvideo.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/12 15:10
 * Description：
 */
@Configuration
public class MQConfig {

    public final static String REPORT_QUEUE = "reportQueue";
    public final static String LIKE_QUEUE = "likeQueue";

    public final static String REPORT_DIRECT_EXCHANGE = "reportDirectExchange";
    public final static String LIKE_DIRECT_EXCHANGE = "likeDirectExchange";

    public final static String ROUTINGKEY1 = "reportDirectRouting";
    public final static String ROUTINGKEY2 = "likeDirectRouting";


    @Bean
    public Queue reportQueue(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-max-length", 1000);  //队列可以容纳的消息的最大条数
        args.put("x-max-length-bytes", 134217728);  //队列可以容纳的消息的最大字节数 512M
        return new Queue(REPORT_QUEUE,true, false, false, args);
    }

    @Bean
    public Queue likeQueue(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-max-length", 5000);  //队列可以容纳的消息的最大条数
        args.put("x-max-length-bytes", 536870912);  //队列可以容纳的消息的最大字节数 128M
        return new Queue(LIKE_QUEUE,true, false, false, args);
    }


    @Bean
    DirectExchange reportDirectExchange() {
        return new DirectExchange(REPORT_DIRECT_EXCHANGE,true,false);
    }

    @Bean
    DirectExchange likeDirectExchange() {
        return new DirectExchange(LIKE_DIRECT_EXCHANGE,true,false);
    }

    @Bean
    Binding bindingReportDirect(){
        return BindingBuilder.bind(reportQueue()).to(reportDirectExchange()).with(ROUTINGKEY1);
    }

    @Bean
    Binding bindingLikeDirect(){
        return BindingBuilder.bind(likeQueue()).to(likeDirectExchange()).with(ROUTINGKEY2);
    }

}
