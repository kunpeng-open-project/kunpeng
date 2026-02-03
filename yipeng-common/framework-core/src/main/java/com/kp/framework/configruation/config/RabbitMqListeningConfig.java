package com.kp.framework.configruation.config;

import com.kp.framework.utils.kptool.KPStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ 消息可靠性增强配置（适配 Spring Boot 3.x）。
 * 增强 RabbitTemplate：添加消息发送确认（Confirm）和未路由返回（Return）回调
 * 配置批量消费监听容器工厂
 * @author lipeng
 * 2024/1/24
 */
@Slf4j
@Component
public class RabbitMqListeningConfig implements BeanPostProcessor  {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof RabbitTemplate rabbitTemplate) {
            log.debug("增强 RabbitTemplate");

            // 当消息无法路由到任何队列（即交换器无法根据指定的路由键找到匹配的队列）时，RabbitMQ会将该消息返回给发布者。 此回调用于捕获这些“被退回”的消息，便于排查路由问题。
            rabbitTemplate.setReturnsCallback(returned -> {
                log.info("消息主体: {}", returned.getMessage());
                log.info("错误代码: {}", returned.getReplyCode());
                log.info("错误的详细描述: {}", returned.getReplyText());
                log.info("消息使用的交换器: {}", returned.getExchange());
                log.info("消息使用的路由键: {}", returned.getRoutingKey());
            });

            // 作用：确认消息是否成功到达 RabbitMQ 服务器的交换器并被正确路由到至少一个队列。 ack = true 表示成功；ack = false 表示失败（如交换器不存在、网络中断等）。
            rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
                @Override
                public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                    if (ack) {
                        log.info(KPStringUtil.format("RabbitMQ 发送成功: 消息主键-{0}", correlationData.getId()));
                    } else {
                        log.info(KPStringUtil.format("RabbitMQ 发送失败: 消息主键-{0}, 错误原因-{1}", correlationData.getId(), cause));
                    }
                }
            });
            return rabbitTemplate;
        }
        return bean;
    }


    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(SimpleRabbitListenerContainerFactory containerFactory) {
        containerFactory.setBatchListener(true);
        //设置接收超时时间为3秒 10秒还没有达到队列设置大小 也直接推给消费者
        containerFactory.setReceiveTimeout(1000l * 3);
        containerFactory.setBatchSize(10);
        containerFactory.setConsumerBatchEnabled(true);
        return containerFactory;
    }
}
