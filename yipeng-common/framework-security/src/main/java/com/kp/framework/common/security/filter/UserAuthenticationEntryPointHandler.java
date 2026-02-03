package com.kp.framework.common.security.filter;

import com.kp.framework.common.enums.AuthCodeEnum;
import com.kp.framework.common.util.BackUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * 用户未登录处理类。
 * @author lipeng
 * 2024/4/21
 */
@Component
public class UserAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Autowired
    private BackUtil backUtil;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        backUtil.writeJson(AuthCodeEnum.NOT_LOGIN);
    }
}