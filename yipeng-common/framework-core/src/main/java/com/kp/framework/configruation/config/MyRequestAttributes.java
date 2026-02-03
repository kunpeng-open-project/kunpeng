package com.kp.framework.configruation.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.task.TaskDecorator;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

/**
 * 自定义RequestAttributes 进入新线程前复制 RequestAttributes 用于在新线程后能获取到RequestAttributes。
 * @author lipeng
 * 2025/9/25l
 * @return
 */
public class MyRequestAttributes implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        // 1. 在主线程中，获取原始的 ServletRequestAttributes
        RequestAttributes originalAttributes = RequestContextHolder.getRequestAttributes();

        if (originalAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest originalRequest = ((ServletRequestAttributes) originalAttributes).getRequest();

            // 2. 创建一个 MockHttpServletRequest 作为信息的载体
            MockHttpServletRequest copiedRequest = new MockHttpServletRequest();

            // 3. 将原始请求的关键信息复制到 Mock 对象中
            copiedRequest.setMethod(originalRequest.getMethod());
            copiedRequest.setRequestURI(originalRequest.getRequestURI());
            copiedRequest.setQueryString(originalRequest.getQueryString());
            copiedRequest.setPathInfo(originalRequest.getPathInfo());
            copiedRequest.setServletPath(originalRequest.getServletPath());

            // 复制请求头
            Enumeration<String> headerNames = originalRequest.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                Enumeration<String> values = originalRequest.getHeaders(name);
                while (values.hasMoreElements()) {
                    copiedRequest.addHeader(name, values.nextElement());
                }
            }

            // 复制请求参数
            copiedRequest.setParameters(originalRequest.getParameterMap());

            // (可选) 复制 Cookies
            if (originalRequest.getCookies() != null) {
                copiedRequest.setCookies(originalRequest.getCookies());
            }

            // 4. 使用 MockHttpServletRequest 创建我们的 CustomRequestAttributes
            CustomRequestAttributes newAttributes = new CustomRequestAttributes(copiedRequest);

            // 5. 返回一个新的 Runnable，它会在异步线程中设置新的上下文
            return () -> {
                try {
                    RequestContextHolder.setRequestAttributes(newAttributes);
                    runnable.run(); // 执行原始任务
                } finally {
                    // 确保在任务结束后清理上下文，防止线程复用导致的问题
                    RequestContextHolder.resetRequestAttributes();
                }
            };
        }

        // 如果当前线程没有请求上下文，则直接返回原始任务
        return runnable;
    }


    class CustomRequestAttributes extends ServletRequestAttributes {

        /**
         * 使用一个 MockHttpServletRequest 来构造父类。
         *
         * @param request 一个包含了复制信息的 MockHttpServletRequest
         */
        public CustomRequestAttributes(HttpServletRequest request) {
            super(request);
        }
    }
}