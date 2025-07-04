package com.kunpeng.framework.configruation.config;


import com.kunpeng.framework.configruation.interceptor.GlobalHandlerInstantiator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @Author lipeng
 * @Description web相关配置
 * @Date 2021-04-12
 * @return
 **/
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