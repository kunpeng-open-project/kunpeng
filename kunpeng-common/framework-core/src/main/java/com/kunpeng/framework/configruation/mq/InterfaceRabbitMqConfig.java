package com.kunpeng.framework.configruation.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author lipeng
 * @Description 接口记录
 * @Date 2024/1/24 9:05
 * @return
 **/
@Component
public class InterfaceRabbitMqConfig {

    /**
     * @Description 正常队列
     **/
    public final static String NORMAL_EXCHANGE = "interface_exchange";//正常交换机
    public final static String NORMAL_QUEUE = "interface_queue";  //正常队列
    public final static String NORMAL_ROUTING_KEY = "interface_routingkey";//正常路由


    /**
     * @Description 死信队列
     **/
    private final String DEAD_EXCHANGE = "interface_exchange_dead";//死信交换机
    public final static String DEAD_QUEUE = "interface_queue_dead";//死信队列
    private final String DEAD_ROUTING_KEY = "interface_routingkey_dead";//死信路由

    private Integer out_date = 86400000;// 单位 毫秒 24 小时过期

    private  Integer del_date = 864000000;// 删除时间 10 天

    //死信交换机
    @Bean
    public DirectExchange interfaceDeadExchange() {
        return new DirectExchange(DEAD_EXCHANGE);
    }
    //声明死信队列
    @Bean
    public Queue interfaceDeadQueue() {
//        return new Queue(DEAD_QUEUE);
        Map<String, Object> args = new HashMap<>();
        // 设置队列过期时间，单位为毫秒 10 天不处理的 自动删除 防止消息堆积 mq挂掉
        args.put("x-expires", del_date);
        return new Queue(DEAD_QUEUE, true, false, false, args);
    }

    //绑定死信队列到死信交换机
    @Bean
    public Binding interfaceDeadbinding(Queue interfaceDeadQueue, DirectExchange interfaceDeadExchange) {
        return BindingBuilder.bind(interfaceDeadQueue).to(interfaceDeadExchange).with(DEAD_ROUTING_KEY);
    }



    //正常交换机
    @Bean
    public DirectExchange interfaceNormalExchange() {
        return new DirectExchange(NORMAL_EXCHANGE);
    }

    //声明正常队列
    @Bean
    public Queue interfaceNormalQueue() {
        //队列绑定我们的死信交换机
        Map<String, Object> arguments = new HashMap<>();
        // 设置消息过期时间为60秒
        arguments.put("x-message-ttl", out_date);
        //x-dead-letter-exchange 声明当前业务绑定的死信交换机
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        //x-dead-letter-routing-key 死信路由key
        arguments.put("x-dead-letter-routing-key", DEAD_ROUTING_KEY);
        return new Queue(NORMAL_QUEUE, true, false, false, arguments);
    }

    //绑定正常队列到正常交换机
    @Bean
    public Binding interfaceNormalBinding(Queue interfaceNormalQueue, DirectExchange interfaceNormalExchange) {
        return BindingBuilder.bind(interfaceNormalQueue).to(interfaceNormalExchange).with(NORMAL_ROUTING_KEY);
    }
}