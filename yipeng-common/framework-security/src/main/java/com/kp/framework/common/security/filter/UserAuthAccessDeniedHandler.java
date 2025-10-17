package com.kp.framework.common.security.filter;

import com.kp.framework.common.enums.AuthCodeEnum;
import com.kp.framework.common.util.BackUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private BackUtil backUtil;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) {
        backUtil.writeJson(AuthCodeEnum.NOT_PERMISSIONS);
    }
}