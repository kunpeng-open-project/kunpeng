package com.kp.framework.common.security.filter;

import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.common.enums.AuthCodeEnum;
import com.kp.framework.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author lipeng
 * @Description 暂无权限处理类
 * @Date 2024/4/21
 * @return
 **/
@Component
public class UserAuthAccessDeniedHandler implements AccessDeniedHandler {

    @Value("${kp.project-name}")
    private String projectName;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) {
        JSONObject body = new JSONObject()
                .fluentPut("code", AuthCodeEnum.NOT_PERMISSIONS.code())
                .fluentPut("message", AuthCodeEnum.NOT_PERMISSIONS.message());
        CommonUtil.writeJson(body, projectName);
    }
}