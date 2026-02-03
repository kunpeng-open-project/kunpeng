package com.kp.framework.config;

import com.kp.framework.common.util.IconUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 核心配置。
 * @author lipeng
 * 2023/1/22
 */
@Configuration
@Component
@Slf4j
public class FrameworkSecurityApplication {
    @PostConstruct
    public void init() {
        IconUtil.println(IconUtil.PURPLE, Arrays.asList("加载模块 [framework-security] 鉴权校验模块!", "版本      1.3.0"));
        log.info("加载模块 [framework-security] 鉴权校验模块!");
    }
}
