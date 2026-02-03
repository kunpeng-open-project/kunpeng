package com.kp.framework.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.kp.framework.common.annotation.DataPermissionsInnerceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Slf4j
public class SecurityCoreConfig {

    /**
     * 解决spring security firewall 防火墙导致 // 链接不支持。
     * @author lipeng
     * 2023/12/5
     * @return org.springframework.security.web.firewall.HttpFirewall
     */
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