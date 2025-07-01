package com.kunpeng.framework.common.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;


@Data
@ConfigurationProperties(prefix = "kp.user")
@Configuration
@RefreshScope
public class KunPengUserProperties {

    //登录失败错误次数上限
    private Integer errorNumber = 5;

    // 锁定时间 单位分
    private Integer lockTime = 1440;

    // 初始化默认密码
    private String defaultPassword = "123456";
}
