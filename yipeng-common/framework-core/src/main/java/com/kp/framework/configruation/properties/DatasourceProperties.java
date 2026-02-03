package com.kp.framework.configruation.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;



/**
 * 读取数据库配置信息。
 * @author lipeng
 * 2024/1/4
 */
@Data
@ConfigurationProperties(prefix = "spring.datasource")
@Configuration
@RefreshScope
public class DatasourceProperties {

    private String url;

    private String userName;

    private String password;

    private String driverClassName;
}
