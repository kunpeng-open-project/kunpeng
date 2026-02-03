package com.kp.framework.configruation.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * Scheduled注解定时器配置。
 * @author lipeng
 * 2025/6/26
 */
@Data
@ConfigurationProperties(prefix = "kp.task")
@Configuration
@RefreshScope
public class KPFrameworkTaskProperties {

    //线程池大小
    private Integer poolSize = 5;

}
