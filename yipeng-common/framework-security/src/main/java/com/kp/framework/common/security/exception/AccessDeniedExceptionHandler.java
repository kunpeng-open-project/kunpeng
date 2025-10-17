package com.kp.framework.common.security.exception;


import com.kp.framework.common.util.BackUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @Author lipeng
 * @Description 解决全局异常处理器提前处理而不进入 UserAuthAccessDeniedHandler 处理器
 * @Date 2024/4/21
 * @return
 **/
@RestControllerAdvice
@Order(0)
public class AccessDeniedExceptionHandler {

    @Autowired
    private BackUtil backUtil;

    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        throw e;
    }

    @ExceptionHandler(AuthenticationServiceException.class)
    public void ServiceExceptionHandler(AuthenticationServiceException e) {
        backUtil.writeJson(e.getCode(), e.getMessage());
    }
}
