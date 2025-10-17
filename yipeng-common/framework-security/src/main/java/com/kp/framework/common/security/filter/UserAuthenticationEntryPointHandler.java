package com.kp.framework.common.security.filter;

import com.kp.framework.common.enums.AuthCodeEnum;
import com.kp.framework.common.util.BackUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Author lipeng
 * @Description 用户未登录处理类
 * @Date 2024/4/21
 * @return
 **/
@Component
public class UserAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Autowired
    private BackUtil backUtil;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        backUtil.writeJson(AuthCodeEnum.NOT_LOGIN);
    }
}