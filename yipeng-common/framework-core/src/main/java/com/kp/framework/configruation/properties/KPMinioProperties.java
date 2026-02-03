package com.kp.framework.configruation.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * minio配置。
 * @author lipeng
 * 2022/5/21
 */
@Data
@ConfigurationProperties(prefix = "kp.minio")
@Configuration
@RefreshScope
public class KPMinioProperties {

    //minio 访问地址
    private String url;

    //minio 用户名
    private String userName;

    //minio 密码
    private String password;
}