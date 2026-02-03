package com.kp.framework.common.rabbitmq.listener;

import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.common.properties.MqProperties;
import com.kp.framework.common.rabbitmq.po.HttpLogMqCustomerPO;
import com.kp.framework.configruation.mq.HttpRabbitMqConfig;
import com.kp.framework.entity.bo.OperationUserMessageBO;
import com.kp.framework.enums.DeleteFalgEnum;
import com.kp.framework.modules.logRecord.mapper.HttpLogMapper;
import com.kp.framework.modules.logRecord.po.HttpLogPO;
import com.kp.framework.utils.kptool.KPCollectionUtil;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPLocalDateTimeUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * http接口调用记录。
 * @author lipeng
 * 2024/2/1
 */
@Component
@Slf4j
public class HttpListener {

    public List<HttpLogMqCustomerPO> rows = new CopyOnWriteArrayList<HttpLogMqCustomerPO>();
    @Autowired
    private HttpLogMapper httpLogMapper;
    @Autowired
    private MqProperties mqProperties;

    /**
     * 正常队列消费
     * @author lipeng
     * 2025/5/21
     */
    @RabbitListener(queues = HttpRabbitMqConfig.NORMAL_QUEUE)
    public void interfaceQueues(List<Message> messages, Channel channel) throws IOException {
        log.info(KPStringUtil.format("【mq开始消费Http请求记录】 消费条数{0}, 入库条数：{1} 目前已有条数：{2}", messages.size(), mqProperties.getHttpConsumeNum(), rows.size() + messages.size()));
        // 批量确认消息
        messages.forEach(message -> {
            HttpLogMqCustomerPO body = KPJsonUtil.toJavaObject(new String(message.getBody(), StandardCharsets.UTF_8), HttpLogMqCustomerPO.class);
            createCommon(body);
            body.setDisposeTimeExplain(KPLocalDateTimeUtil.formatDuration(body.getDisposeTime()));
            body.setDeliveryTag(message.getMessageProperties().getDeliveryTag());
            rows.add(body);
        });

        if (rows.size() < mqProperties.getHttpConsumeNum()) return;

        //入库
        try {
            if (!KPCollectionUtil.insertBatch(httpLogMapper, KPJsonUtil.toJavaObjectList(rows, HttpLogPO.class), 100))
                this.disposeFail(channel);

            for (HttpLogMqCustomerPO row : rows) {
                channel.basicAck(row.getDeliveryTag(), false);
            }
            rows.clear();
        } catch (Exception ex) {
            this.disposeFail(channel);
        }
    }


    /**
     * 死信队列消费异常信息。
     * @author lipeng
     * 2025/5/21
     */
    @RabbitListener(queues = HttpRabbitMqConfig.DEAD_QUEUE)
    public void interfaceDeadQueues(List<Message> messages, Channel channel) {
        log.info(KPStringUtil.format("【mq开始消费死信队列中的Http记录】 消费条数{0}", messages.size()));

        messages.forEach(message -> {
            HttpLogMqCustomerPO body = KPJsonUtil.toJavaObject(new String(message.getBody(), StandardCharsets.UTF_8), HttpLogMqCustomerPO.class);
            createCommon(body);
            body.setDisposeTimeExplain(KPLocalDateTimeUtil.formatDuration(body.getDisposeTime()));
            body.setDeliveryTag(message.getMessageProperties().getDeliveryTag());
            body.setResult(StringUtils.abbreviate(body.getResult(), 16370));
            body.setParameters(StringUtils.abbreviate(body.getParameters(), 16370));
            if (httpLogMapper.insert(body) == 1) {
                try {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } catch (IOException e) {
                    log.error("【mq死信队列异常】 死信队列异常：{}", e.getMessage());
                }
            }
        });
    }


    private void createCommon(HttpLogMqCustomerPO body) {
        OperationUserMessageBO userMessageBO = body.getUserMessage();
        if (userMessageBO != null) {
            body.setCreateUserId(userMessageBO.getId());
            body.setCreateUserName(userMessageBO.getName());
            body.setUpdateUserId(userMessageBO.getId());
            body.setUpdateUserName(userMessageBO.getName());
            body.setPhone(userMessageBO.getPhone());
            body.setSerial(userMessageBO.getSerial());
            body.setIdentification(userMessageBO.getId());
            body.setIdentificationName(userMessageBO.getName());
        }

        body.setCreateDate(LocalDateTime.now());
        body.setUpdateDate(LocalDateTime.now());
        body.setDeleteFlag(DeleteFalgEnum.NORMAL.code());

        try {
            JSONObject result = KPJsonUtil.toJson(body.getResult());
            body.setStatus(result.getInteger("code"));
            body.setMessage(StringUtils.abbreviate(result.getString("message"), 990));
        } catch (Exception ex) {
            log.error("【mq解析结果失败】{}", ex.getMessage());
        }
    }

    /**
     * 移动到死信队列。
     * @author lipeng
     * 2024/2/1
     */
    private void disposeFail(Channel channel) {
        rows.forEach(row -> {
            try {
                channel.basicReject(row.getDeliveryTag(), false);
            } catch (IOException e) {
                log.error("【mq移动到死信队列失败】{}", e.getMessage());
            }
        });
        rows.clear();
    }
}