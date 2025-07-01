package com.kunpeng.framework.configruation.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;


/**
 * @Author lipeng
 * @Description @Scheduled 定时器配置
 * @Date 2025/6/26 17:49
 * @return
 **/
@Data
@ConfigurationProperties(prefix = "kp.task")
@Configuration
@RefreshScope
public class KunPengFrameworkTaskProperties {

    //线程池大小
    private Integer poolSize = 5;

}
