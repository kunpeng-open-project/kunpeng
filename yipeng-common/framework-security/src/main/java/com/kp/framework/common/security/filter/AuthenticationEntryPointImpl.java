package com.kp.framework.common.security.filter;

import com.kp.framework.common.enums.AuthCodeEnum;
import com.kp.framework.common.util.BackUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



/**
 * @Author lipeng
 * @Description 认证失败处理类 返回未授权
 * @Date 2024/4/20
 * @return
 **/
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Autowired
    private BackUtil backUtil;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        backUtil.writeJson(AuthCodeEnum.NOT_LOGIN);
    }
}
