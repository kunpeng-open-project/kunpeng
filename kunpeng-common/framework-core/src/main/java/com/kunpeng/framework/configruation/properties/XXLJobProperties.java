package com.kunpeng.framework.configruation.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "xxl.job")
@Configuration
@RefreshScope
public class XXLJobProperties {

    // 对应 YAML 中的 "xxl.job.admin" 节点
    private Admin admin = new Admin();

    // 对应 YAML 中的 "xxl.job.executor" 节点
    private Executor executor = new Executor();
    // 内部类：映射 "xxl.job.admin" 配置

    @Data
    public static class Admin {
        private String addresses; // 对应 addresses
        private String accessToken; // 对应 accessToken
        private int timeout; // 对应 timeout
    }

    @Data
    public static class Executor {
        private String appname; // 对应 appname
        private String address; // 对应 address
        private String ip; // 对应 ip
        private int port; // 对应 port
        private String logpath; // 对应 logpath
        private int logRetentionDays; // 对应 logretentiondays
    }

}
