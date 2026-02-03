package com.kp.framework.configruation.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;


/**
 * 是否记录日志配置。
 * @author lipeng
 * 2024/1/21
 */
@Data
@ConfigurationProperties(prefix = "kp.log")
@Configuration
@RefreshScope
public class KPLogRecordProperties {

    //接口记录
    private Boolean interfaceLog = true;

    //调用第三方http记录
    private Boolean httpLog = true;
}