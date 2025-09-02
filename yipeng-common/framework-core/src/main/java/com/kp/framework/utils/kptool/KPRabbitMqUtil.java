package com.kp.framework.utils.kptool;


import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;

/**
 * @Author lipeng
 * @Description RabbitMq 工具类
 * @Date 2024/1/24 14:23
 * @return
 **/
public class KPRabbitMqUtil {
    private final static RabbitTemplate rabbitTemplate = KPServiceUtil.getBean("rabbitTemplate", RabbitTemplate.class);;


    /**
     * @Author lipeng
     * @Description 发送Dead队列消息
     * @Date 2024/1/24 14:26
     * @param exchangeName
     * @param routingKey
     * @param msg
     * @return void
     **/
    public static void sendDeadMessage(String exchangeName, String routingKey, Object msg){
//        rabbitTemplate.convertAndSend(DeadLetterMQConfig.normalExchange, DeadLetterMQConfig.normalRoutingKey, msg, message -> {
//            // 设置消息过期时间 10秒过期
//            message.getMessageProperties().setExpiration("6000");
//            return message;
//        });

        rabbitTemplate.convertAndSend(exchangeName, routingKey, msg,  new CorrelationData(KPUuidUtil.getUUID()));
//        rabbitTemplate.convertAndSend(exchangeName, routingKey, msg, message -> {
//            // 设置消息过期时间 10秒过期
//            message.getMessageProperties().setExpiration("60000");
//            message.getMessageProperties().setCorrelationId(correlationData.getId());
//            return message;
//        });
    }



    /**
     * @Author lipeng
     * @Description 批量Dead发送消息
     * @Date 2024/1/26 16:02
     * @param exchangeName
     * @param routingKey
     * @param msgs
     * @return void
     **/
    public static void sendDeadMessageList(String exchangeName, String routingKey, List<?> msgs){
        msgs.forEach(msg->{
            rabbitTemplate.convertAndSend(exchangeName, routingKey, msg,  new CorrelationData(KPUuidUtil.getUUID()));
        });
    }



    /**
     * @Author lipeng
     * @Description 发送Fanout队列消息
     * @Date 2024/4/8 11:47
     * @param exchangeName
     * @param msg
     * @return void
     **/
    public static void sendFanoutMessage(String exchangeName, Object msg){
        rabbitTemplate.convertAndSend(exchangeName, "", msg,  new CorrelationData(KPUuidUtil.getUUID()));
    }


    /**
     * @Author lipeng
     * @Description 批量发送Fanout队列消息
     * @Date 2024/4/8 11:48
     * @param exchangeName
     * @param msgs
     * @return void
     **/
    public static void sendFanoutMessageList(String exchangeName, List<?> msgs){
        msgs.forEach(msg->{
            rabbitTemplate.convertAndSend(exchangeName, "", msg,  new CorrelationData(KPUuidUtil.getUUID()));
        });
    }
}
