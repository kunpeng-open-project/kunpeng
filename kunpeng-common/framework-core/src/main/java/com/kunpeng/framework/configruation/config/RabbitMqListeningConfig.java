package com.kunpeng.framework.configruation.config;
import com.kunpeng.framework.utils.kptool.KPStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * @Author lipeng
 * @Description
 * @Date 2024/1/24 15:50
 * @return
 **/
@Slf4j
@Component
public class RabbitMqListeningConfig implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof RabbitTemplate) {
            log.debug("增强 RabbitTemplate");
            RabbitTemplate rabbitTemplate = (RabbitTemplate) bean;
            // 当消息无法路由到任何队列（即交换器无法根据指定的路由键找到匹配的队列）时，RabbitMQ会将该消息返回给发布者。通过设置ReturnCallback，你可以捕获这些未被正确路由的消息并进行相应的处理。
            rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
                @Override
                public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                    log.info("消息主体: " +message);
                    log.info("错误代码: " + replyCode);
                    log.info("错误的详细描: " + replyText);
                    log.info("消息使用的交换器: " + exchange);
                    log.info("消息使用的路由键: " + routingKey);
                }
            });

            //确认消息是否发送到队列
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
