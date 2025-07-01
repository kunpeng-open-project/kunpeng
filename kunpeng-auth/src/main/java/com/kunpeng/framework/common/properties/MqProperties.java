package com.kunpeng.framework.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;


/**
 * @Author lipeng
 * @Description
 * @Date 2025/5/21
 * @return
 **/
@Data
@ConfigurationProperties(prefix = "kp.mq")
@Configuration
@RefreshScope
public class MqProperties {

    //#接口记录日志 消费条数（多少条入一次数据库）
    private Integer interfaceConsumeNum = 100;

    //# http记录日志 消费条数（多少条入一次数据库）
    private Integer httpConsumeNum = 20;

}