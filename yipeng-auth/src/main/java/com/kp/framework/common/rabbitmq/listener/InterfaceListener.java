package com.kp.framework.common.rabbitmq.listener;

import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.common.properties.MqProperties;
import com.kp.framework.common.rabbitmq.po.InterfaceLogMqCustomerPO;
import com.kp.framework.configruation.mq.InterfaceRabbitMqConfig;
import com.kp.framework.entity.bo.OperationUserMessageBO;
import com.kp.framework.enums.DeleteFalgEnum;
import com.kp.framework.modules.logRecord.mapper.InterfaceLogMapper;
import com.kp.framework.modules.logRecord.po.InterfaceLogPO;
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
 * @Author lipeng
 * @Description 记录接口访问日志
 * @Date 2024/1/25
 * @return
 **/
@Component
@Slf4j
public class InterfaceListener {

    public List<InterfaceLogMqCustomerPO> rows = new CopyOnWriteArrayList();
    @Autowired
    private InterfaceLogMapper interfaceLogMapper;
    @Autowired
    private MqProperties mqProperties;


    /**
     * @Author lipeng
     * @Description 正常消费
     * @Date 2025/5/21
     * @param messages
     * @param channel
     * @return void
     **/
    @RabbitListener(queues = InterfaceRabbitMqConfig.NORMAL_QUEUE)
    public void interfaceQueues(List<Message> messages, Channel channel) {
        log.info(KPStringUtil.format("【mq开始消费接口记录】 消费条数{0}, 入库条数：{1} 目前已有条数：{2}", messages.size(), mqProperties.getInterfaceConsumeNum(), rows.size() + messages.size()));

        // 批量确认消息
        messages.forEach(message -> {
            InterfaceLogMqCustomerPO body = KPJsonUtil.toJavaObject(new String(message.getBody(), StandardCharsets.UTF_8), InterfaceLogMqCustomerPO.class);
            createCommon(body);
            body.setDisposeTimeExplain(KPLocalDateTimeUtil.formatDuration(body.getDisposeTime()));
            body.setDeliveryTag(message.getMessageProperties().getDeliveryTag());
            rows.add(body);
        });

        if (rows.size() < mqProperties.getInterfaceConsumeNum()) return;

        //入库
        try {
            if (!KPCollectionUtil.insertBatch(interfaceLogMapper, KPJsonUtil.toJavaObjectList(rows, InterfaceLogPO.class), 100))
                this.disposeFail(channel);
            for (InterfaceLogMqCustomerPO row : rows) {
                channel.basicAck(row.getDeliveryTag(), false);
            }
            rows.clear();
        } catch (Exception ex) {
            this.disposeFail(channel);
        }
    }



    /**
     * @Author lipeng
     * @Description 死信队列 消费异常信息
     * @Date 2025/5/21
     * @param messages
     * @param channel
     * @return void
     **/
    @RabbitListener(queues = InterfaceRabbitMqConfig.DEAD_QUEUE)
    public void interfaceDeadQueues(List<Message> messages, Channel channel) {
        log.info(KPStringUtil.format("【mq开始消费死信队列中的接口记录】 消费条数{0}", messages.size()));
        messages.forEach(message -> {
            InterfaceLogMqCustomerPO body = KPJsonUtil.toJavaObject(new String(message.getBody(), StandardCharsets.UTF_8), InterfaceLogMqCustomerPO.class);
            createCommon(body);
            body.setDisposeTimeExplain(KPLocalDateTimeUtil.formatDuration(body.getDisposeTime()));
            body.setDeliveryTag(message.getMessageProperties().getDeliveryTag());
            body.setResult(StringUtils.abbreviate(body.getResult(), 16370));
            body.setParameters(StringUtils.abbreviate(body.getParameters(), 16370));
            if (interfaceLogMapper.insert(body) == 1) {
                try {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } catch (IOException e) {
                }
            }
        });
    }


    private void createCommon(InterfaceLogMqCustomerPO body) {
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
        }catch (Exception ex){}
    }

    /**
     * @Author lipeng
     * @Description 移动到死信队列
     * @Date 2024/2/1
     * @param channel
     * @return void
     **/
    private void disposeFail(Channel channel) {
        rows.forEach(row -> {
            try {
                channel.basicReject(row.getDeliveryTag(), false);
            } catch (IOException e) {
            }
        });
        rows.clear();
    }
}