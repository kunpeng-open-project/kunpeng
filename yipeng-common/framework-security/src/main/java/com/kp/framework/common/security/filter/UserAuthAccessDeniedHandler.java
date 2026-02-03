package com.kp.framework.common.security.filter;

import com.kp.framework.common.enums.AuthCodeEnum;
import com.kp.framework.common.util.BackUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * 暂无权限处理类。
 * @author lipeng
 * 2024/4/21
 */
@Component
public class UserAuthAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private BackUtil backUtil;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) {
        backUtil.writeJson(AuthCodeEnum.NOT_PERMISSIONS);
    }
}