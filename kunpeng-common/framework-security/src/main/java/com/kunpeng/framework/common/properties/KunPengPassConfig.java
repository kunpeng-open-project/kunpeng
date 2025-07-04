package com.kunpeng.framework.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author lipeng
 * @Description 白名单
 * @Date 2024/4/19
 * @return
 **/
@Data
@ConfigurationProperties(prefix = "kp.white")
@Component
public class KunPengPassConfig {

    private List<String> urls = new ArrayList<>();


    public List<String> getUrls() {
        List<String> urls2 = this.urls;
        if (!urls2.contains("/auth/user/login")){
            urls2.addAll(Arrays.asList(
                    "/auth/user/login", "/auth/user/exempt/login", "/auth/user/authorization/login", "/auth/user/swagger/login",
                    "/actuator/**",
                    "/*.html","/**/*.html","/**/*.css","/*.css","/**/*.js","/*.js","/*.ico",
                    "/swagger/**","/swagger-ui.html","/swagger-resources/**","/*/api-docs","/doc.html","/webjars/**",
                    "/druid/**",
                    "/open/**"
            ));
        }

        return urls2;
    }

}
