package com.kunpeng.framework.common.security.exception;


import com.alibaba.fastjson2.JSONObject;
import com.kunpeng.framework.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @Author lipeng
 * @Description 解决全局异常处理器提前处理而不进入 UserAuthAccessDeniedHandler 处理器
 * @Date 2024/4/21 14:51
 * @return
 **/
@RestControllerAdvice
@Order(0)
public class AccessDeniedExceptionHandler {

    @Value("${kp.project-name}")
    private String projectName;

    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        throw e;
    }

    @ExceptionHandler(AuthenticationServiceException.class)
    public void ServiceExceptionHandler(AuthenticationServiceException e) {
        JSONObject body = new JSONObject()
                .fluentPut("code", e.getCode())
                .fluentPut("message", e.getMessage());
        CommonUtil.writeJson(body, projectName);;
    }
}
