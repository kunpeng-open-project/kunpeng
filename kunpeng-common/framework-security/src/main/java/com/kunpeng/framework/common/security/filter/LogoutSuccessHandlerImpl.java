package com.kunpeng.framework.common.security.filter;

import com.alibaba.fastjson2.JSONObject;
import com.kunpeng.framework.common.enums.AuthCodeEnum;
import com.kunpeng.framework.common.properties.KunPengTokenProperties;
import com.kunpeng.framework.common.properties.RedisSecurityConstant;
import com.kunpeng.framework.common.util.CommonUtil;
import com.kunpeng.framework.common.util.KPJWTUtil;
import com.kunpeng.framework.modules.user.po.customer.LoginUserTypeBO;
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
    private KunPengTokenProperties kunPengTokenProperties;
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
            String token = request.getHeader(kunPengTokenProperties.getHeader());
            if (StringUtils.isNotEmpty(token)) {
                if (token.startsWith(kunPengTokenProperties.getHead())) token = token.replace(kunPengTokenProperties.getHead(), "").trim();
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
