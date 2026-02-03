package com.kp.framework.utils.kptool;

import lombok.experimental.UtilityClass;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;

/**
 * RabbitMq 工具类。
 * @author lipeng
 * 2024/1/24
 */
@UtilityClass
public class KPRabbitMqUtil {
    private final static RabbitTemplate rabbitTemplate = KPServiceUtil.getBean("rabbitTemplate", RabbitTemplate.class);

    /**
     * 发送Dead队列消息。
     * @author lipeng
     * 2024/1/24
     * @param exchangeName 队列名称
     * @param routingKey 路由key
     * @param msg 消息
     */
    public static void sendDeadMessage(String exchangeName, String routingKey, Object msg) {
//        rabbitTemplate.convertAndSend(DeadLetterMQConfig.normalExchange, DeadLetterMQConfig.normalRoutingKey, msg, message -> {
//            // 设置消息过期时间 10秒过期
//            message.getMessageProperties().setExpiration("6000");
//            return message;
//        });

        rabbitTemplate.convertAndSend(exchangeName, routingKey, msg, new CorrelationData(KPUuidUtil.getUUID()));
//        rabbitTemplate.convertAndSend(exchangeName, routingKey, msg, message -> {
//            // 设置消息过期时间 10秒过期
//            message.getMessageProperties().setExpiration("60000");
//            message.getMessageProperties().setCorrelationId(correlationData.getId());
//            return message;
//        });
    }

    /**
     * 批量Dead发送消息。
     * @author lipeng
     * 2024/1/26
     * @param exchangeName 队列名称
     * @param routingKey 路由key
     * @param msgs 消息列表
     */
    public static void sendDeadMessageList(String exchangeName, String routingKey, List<?> msgs) {
        msgs.forEach(msg -> {
            rabbitTemplate.convertAndSend(exchangeName, routingKey, msg, new CorrelationData(KPUuidUtil.getUUID()));
        });
    }

    /**
     * 发送Fanout队列消息。
     * @author lipeng
     * 2024/4/8
     * @param exchangeName 队列名称
     * @param msg 消息
     */
    public static void sendFanoutMessage(String exchangeName, Object msg) {
        rabbitTemplate.convertAndSend(exchangeName, "", msg, new CorrelationData(KPUuidUtil.getUUID()));
    }

    /**
     * 批量发送Fanout队列消息。
     * @author lipeng
     * 2024/4/8
     * @param exchangeName 队列名称
     * @param msgs 消息列表
     */
    public static void sendFanoutMessageList(String exchangeName, List<?> msgs) {
        msgs.forEach(msg -> {
            rabbitTemplate.convertAndSend(exchangeName, "", msg, new CorrelationData(KPUuidUtil.getUUID()));
        });
    }
}
