package com.kunpeng.framework.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.kunpeng.framework.common.annotation.DataPermissionsInnerceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.stereotype.Component;

/**
 * @Author 李鹏
 * @Description //TODO $
 * @Date $ $
 * @return $
 **/
@Configuration
@Component
@Slf4j
public class SecurityCoreConfig {

    /**
     * @Author lipeng
     * @Description 解决spring security firewall 防火墙导致 // 链接不支持
     * @Date 2023/12/5
     * @param
     * @return org.springframework.security.web.firewall.HttpFirewall
     **/
    @Bean
    public HttpFirewall allowDoubleSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        // 允许双//
        firewall.setAllowUrlEncodedDoubleSlash(true);
        return firewall;
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor1() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        DataPermissionsInnerceptor dataPermissionsInnerceptor = new DataPermissionsInnerceptor();
        interceptor.addInnerInterceptor(dataPermissionsInnerceptor);
        return interceptor;
    }
}