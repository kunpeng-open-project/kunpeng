package com.kp.framework.configruation;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Enumeration;

/**
 * Feign 内部调用传递用户凭证信息和所有请求头信息。
 * @author lipeng
 * 2025/8/8
 */
@Configuration
public class FeignUserMessageConfigruation implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        // 1. 从当前请求上下文获取Token（适用于单体服务或网关转发场景）
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //如果是在线程里面调用feign 是获取不到attributes  导致调用的接口如果需要token验证 会失败
        // 解决方法 在进入线程前先调用 KPRequsetUtil.setRequest();
        if (attributes == null) return;


        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames == null) return;

        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String values = request.getHeader(name);
            if (Arrays.asList("content-length").contains(name)) continue;

            template.header(name, values);
//            if (!"content-type".equalsIgnoreCase(name)) {
////                requestTemplate.header(name, values);
//                template.header(name, values);
//            }
        }
    }
}
