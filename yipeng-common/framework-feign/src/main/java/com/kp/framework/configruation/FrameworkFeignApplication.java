package com.kp.framework.configruation;

import com.kp.framework.utils.kptool.KPIconUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 核心配置。
 * @author lipeng
 * 2025/8/8
 */
@Configuration
@Component
@Slf4j
public class FrameworkFeignApplication {

    @PostConstruct
    public void init() {
        KPIconUtil.println(KPIconUtil.PURPLE, Arrays.asList("加载模块 [framework-feign] feign调用模块!", "版本      1.3.0"));
        log.info("加载模块 [framework-feign] feign核心模块!");
    }
}
