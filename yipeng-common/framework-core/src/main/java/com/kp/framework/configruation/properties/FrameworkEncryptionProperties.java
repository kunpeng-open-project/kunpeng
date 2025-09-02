package com.kp.framework.configruation.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;


/**
 * @Author lipeng
 * @Description 加密配置
 * @Date 2024/1/4 17:14
 * @return
 **/
@Data
@ConfigurationProperties(prefix = "framework.encryption")
@Configuration
@RefreshScope
public class FrameworkEncryptionProperties {

    //redis 是否加密
    private Boolean redis = true;

    //redis 数据库是否加密
    private Boolean database = true;

}
