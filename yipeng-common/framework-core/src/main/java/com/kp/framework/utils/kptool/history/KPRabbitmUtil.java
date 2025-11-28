//package com.kp.framework.utils.kptool.history;
//
////import com.alibaba.fastjson2.JSONObject;
////import org.springframework.amqp.core.Message;
////import org.springframework.amqp.core.MessageProperties;
////import org.springframework.amqp.rabbit.connection.CorrelationData;
////import org.springframework.amqp.rabbit.core.RabbitTemplate;
////import org.springframework.beans.factory.annotation.Autowired;
//
//public class KPRabbitmUtil {
//
////    private static RabbitTemplate rabbitTemplate = KPServiceUtil.getBean("rabbitTemplate", RabbitTemplate.class);
////
////
////    public final static void send(String exchange, Object obj){
////        CorrelationData correlationData = new CorrelationData();
////        correlationData.setId(UUID.randomUUID().toString());
////        correlationData.setReturnedMessage(new Message(KPJSONUtil.toJsonString(obj).getBytes(), new MessageProperties()));
////        rabbitTemplate.convertAndSend(exchange, "", obj, message -> {
////            System.out.println(message);
////            return message;
////        }, correlationData);
////    }
//
//}
