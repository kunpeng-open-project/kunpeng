package com.kunpeng.framework.configruation.config;


import com.kunpeng.framework.configruation.interceptor.GlobalHandlerInstantiator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>Title: WebConfigurer </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: www.jfzh.com</p>
 *
 * @author Chen Haidong
 * @version 1.0
 * @date 2020/7/28  11:28
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private GlobalHandlerInstantiator globalHandlerInstantiator;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins("*")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalHandlerInstantiator).order(9999);
    }
}