package com.kp.framework.common.security.filter;

import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.common.enums.AuthCodeEnum;
import com.kp.framework.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${kp.project-name}")
    private String projectName;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        JSONObject body = new JSONObject()
                .fluentPut("code", AuthCodeEnum.NOT_LOGIN.code())
                .fluentPut("message", AuthCodeEnum.NOT_LOGIN.message());
        CommonUtil.writeJson(body, projectName);
    }
}