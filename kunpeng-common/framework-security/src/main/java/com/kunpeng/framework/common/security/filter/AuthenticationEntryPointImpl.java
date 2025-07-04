package com.kunpeng.framework.common.security.filter;

import com.alibaba.fastjson2.JSONObject;
import com.kunpeng.framework.common.enums.AuthCodeEnum;
import com.kunpeng.framework.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${kp.project-name}")
    private String projectName;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        JSONObject body = new JSONObject()
                .fluentPut("code", AuthCodeEnum.NOT_LOGIN.code())
                .fluentPut("message", AuthCodeEnum.NOT_LOGIN.message());
        CommonUtil.writeJson(body, projectName);
    }
}
