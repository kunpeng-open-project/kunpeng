package com.kunpeng.framework.configruation.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;


/**
 * @Author lipeng
 * @Description 线程池配置
 * https://www.cnblogs.com/pansw/p/10639055.html
 * https://blog.csdn.net/qq_34533703/article/details/131053122
 * @Date 2024/1/4 17:15
 * @return
 **/
@Data
@ConfigurationProperties(prefix = "kp.task-executor")
@Configuration
@RefreshScope
public class KunPengFrameworkTaskExecutorProperties {

    //核心线程池大小
    private Integer corePoolSize = 6;

    //最大可创建的线程数
    private Integer maxPoolSize = 200;

    //队列最大长度
    private Integer queueCapacity = 200;

    //线程池维护线程所允许的空闲时间 单位秒
    private Integer keepAliveSeconds = 300;

}
