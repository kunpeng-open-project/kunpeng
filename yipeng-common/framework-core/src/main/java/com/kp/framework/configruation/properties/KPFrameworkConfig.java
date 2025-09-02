package com.kp.framework.configruation.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "kp")
@Configuration
@RefreshScope
public class KPFrameworkConfig {

    //项目名称
    private String projectName = null;

    //免密登录通道
    private List<String> greenChannelLogin = new ArrayList<>();

}