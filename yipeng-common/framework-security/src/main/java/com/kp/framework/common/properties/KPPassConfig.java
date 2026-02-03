package com.kp.framework.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 白名单。
 * @author lipeng
 * 2024/4/19
 */
@Data
@ConfigurationProperties(prefix = "kp.white")
@Component
public class KPPassConfig {

    private List<String> urls = new ArrayList<>();

    public List<String> getUrls() {
        List<String> urls2 = this.urls;
        if (!urls2.contains("/auth/user/login")) {
            urls2.addAll(Arrays.asList(
                    "/auth/user/login", "/auth/user/exempt/login", "/auth/user/sso/login", "/auth/user/authorization/login", "/auth/user/swagger/login",
                    "/entrance/admin/**", "/minio/file/**",
                    "/actuator/**",
                    "/*.html", "/**/*.html", "/**/*.css", "/*.css", "/**/*.js", "/*.js", "/*.ico",
                    "/swagger/**", "/swagger-resources/**", "/*/api-docs", "/doc.html", "/webjars/**", "/v3/api-docs/**", "/swagger-ui/**",
                    "/druid/**",
                    "/open/**"
            ));
        }

        return urls2;
    }
}
