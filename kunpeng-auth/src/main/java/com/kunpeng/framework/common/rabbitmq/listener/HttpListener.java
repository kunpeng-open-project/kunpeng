package com.kunpeng.framework.common.rabbitmq.listener;

import com.alibaba.fastjson2.JSONObject;
import com.kunpeng.framework.common.properties.MqProperties;
import com.kunpeng.framework.common.rabbitmq.po.HttpLogMqCustomerPO;
import com.kunpeng.framework.configruation.mq.HttpRabbitMqConfig;
import com.kunpeng.framework.entity.bo.OperationUserMessageBO;
import com.kunpeng.framework.enums.DeleteFalgEnum;
import com.kunpeng.framework.modules.logRecord.mapper.HttpLogMapper;
import com.kunpeng.framework.modules.logRecord.po.HttpLogPO;
import com.kunpeng.framework.utils.kptool.KPCollectionUtil;
import com.kunpeng.framework.utils.kptool.KPJsonUtil;
import com.kunpeng.framework.utils.kptool.KPLocalDateTimeUtil;
import com.kunpeng.framework.utils.kptool.KPStringUtil;
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
 * @Description http 接口调用记录
 * @Date 2024/2/1
 * @return
 **/
@Component
@Slf4j
public class HttpListener {

    public List<HttpLogMqCustomerPO> rows = new CopyOnWriteArrayList();
    @Autowired
    private HttpLogMapper httpLogMapper;
    @Autowired
    private MqProperties mqProperties;


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

            for (HttpLogMqCustomerPO row : rows){
                channel.basicAck(row.getDeliveryTag(), false);
            }
            rows.clear();
        }catch (Exception ex){
            this.disposeFail(channel);
        }
    }


    @RabbitListener(queues = HttpRabbitMqConfig.DEAD_QUEUE)
    public void interfaceDeadQueues(List<Message> messages, Channel channel) throws InterruptedException, IOException {
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
                } catch (IOException e) {}
            }
        });
    }


    private void createCommon(HttpLogMqCustomerPO body) {
        OperationUserMessageBO userMessageBO = body.getUserMessage();
        if (userMessageBO != null){
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
        rows.forEach(row->{
            try {
                channel.basicReject(row.getDeliveryTag(), false);
            } catch (IOException e) {}
        });
        rows.clear();
    }
}