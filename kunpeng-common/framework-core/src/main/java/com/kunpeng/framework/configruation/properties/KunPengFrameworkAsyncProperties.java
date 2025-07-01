package com.kunpeng.framework.configruation.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;


/**
 * @Author lipeng
 * @Description @Async 定时器配置
 * @Date 2025/6/26 17:49
 * @return
 **/
@Data
@ConfigurationProperties(prefix = "kp.async")
@Configuration
@RefreshScope
public class KunPengFrameworkAsyncProperties {

    private Integer corePoolSize = 5;
    private Integer maxPoolSize = 10;
    private Integer queueCapacity = 200;
    private Integer keepAliveSeconds = 60; // 单位 秒

}
