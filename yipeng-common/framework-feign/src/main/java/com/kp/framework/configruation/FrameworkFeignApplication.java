package com.kp.framework.configruation;

import com.kp.framework.utils.kptool.KPIconUtil;
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
public class FrameworkFeignApplication {


    @PostConstruct
    public void init() {
        KPIconUtil.println(KPIconUtil.PURPLE, Arrays.asList("加载模块 [framework-feign] feign调用模块!", "版本      1.1.1-SNAPSHOT"));
        log.info("加载模块 [framework-feign] feign核心模块!");
    }

}
