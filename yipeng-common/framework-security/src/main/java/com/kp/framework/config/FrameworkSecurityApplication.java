package com.kp.framework.config;

import com.kp.framework.common.util.IconUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * @Author 李鹏
 * @Description
 * @Date $ $
 * @return $
 **/
@Configuration
@Component
@Slf4j
public class FrameworkSecurityApplication {
    @PostConstruct
    public void init() {
        IconUtil.println(IconUtil.PURPLE, Arrays.asList("加载模块 [framework-security] 鉴权校验模块!", "版本      1.1.1-SNAPSHOT"));
        log.info("加载模块 [framework-security] 鉴权校验模块!");
    }
}
