package com.kp.framework.common.security.filter;

import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.common.enums.AuthCodeEnum;
import com.kp.framework.common.properties.KPTokenProperties;
import com.kp.framework.common.properties.RedisSecurityConstant;
import com.kp.framework.common.util.CommonUtil;
import com.kp.framework.common.util.KPJWTUtil;
import com.kp.framework.modules.user.po.customer.LoginUserTypeBO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @Author lipeng
 * @Description 退出登录
 * @Date 2024/4/20
 * @return
 **/
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private KPTokenProperties kpTokenProperties;
    @Value("${kp.project-name}")
    private String projectName;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object attachment = authentication.getPrincipal();
        if (attachment == null) {
            JSONObject body = new JSONObject()
                    .fluentPut("code", AuthCodeEnum.NOT_LOGIN.code())
                    .fluentPut("message", AuthCodeEnum.NOT_LOGIN.message());
            CommonUtil.writeJson(body, projectName);
        } else {
            String token = request.getHeader(kpTokenProperties.getHeader());
            if (StringUtils.isNotEmpty(token)) {
                if (token.startsWith(kpTokenProperties.getHead())) token = token.replace(kpTokenProperties.getHead(), "").trim();
                LoginUserTypeBO loginUserTypeBO = CommonUtil.toJavaObject(KPJWTUtil.parseToken(token).asString(), LoginUserTypeBO.class);
//                LoginUserBO loginUserBO = (LoginUserBO) attachment;
                //删除redis
                CommonUtil.remove(RedisSecurityConstant.REDIS_AUTHENTICATION_TOKEN + loginUserTypeBO.getProjectCode() + ":" + loginUserTypeBO.getIdentification());
                CommonUtil.remove(RedisSecurityConstant.REDIS_AUTHENTICATION_LOGINUSER_MESSAGE + loginUserTypeBO.getProjectCode() + ":" + loginUserTypeBO.getIdentification());
                JSONObject body = new JSONObject()
                        .fluentPut("code", AuthCodeEnum.SUCCESS.code())
                        .fluentPut("message", AuthCodeEnum.SUCCESS.message());
                CommonUtil.writeJson(body, projectName);
            } else {
                JSONObject body = new JSONObject()
                        .fluentPut("code", AuthCodeEnum.NOT_LOGIN.code())
                        .fluentPut("message", AuthCodeEnum.NOT_LOGIN.message());
                CommonUtil.writeJson(body, projectName);
            }
        }
    }
}
