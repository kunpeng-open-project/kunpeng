package com.kp.framework.configruation.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 读取redis配置信息。
 * @author lipeng
 * 2023/9/20
 */
@Data
@ConfigurationProperties(prefix = "spring.redis")
@Configuration
@RefreshScope
public class RedisProperties {

    private String host;

    private Integer port;

    private Integer database;

    private String password;

}