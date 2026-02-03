package com.kp.framework.common.security.filter;

import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.common.enums.AuthCodeEnum;
import com.kp.framework.common.enums.LoginUserTypeEnum;
import com.kp.framework.common.properties.KPPassConfig;
import com.kp.framework.common.properties.KPTokenProperties;
import com.kp.framework.common.properties.RedisSecurityConstant;
import com.kp.framework.common.util.BackUtil;
import com.kp.framework.common.util.CommonUtil;
import com.kp.framework.common.util.KPJWTUtil;
import com.kp.framework.modules.user.po.customer.LoginUserBO;
import com.kp.framework.modules.user.po.customer.LoginUserTypeBO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 校验token tokeng过滤器。
 * @author lipeng
 * 2024/4/19
 */
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Autowired
    private KPTokenProperties kpTokenProperties;

    @Autowired
    private KPPassConfig kpPassConfig;

    @Autowired
    private BackUtil backUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String url : kpPassConfig.getUrls()) {
            if (pathMatcher.match((url), request.getRequestURI())) {
                chain.doFilter(request, response);
                return;
            }
        }

        String token = request.getHeader(kpTokenProperties.getHeader());
        if (StringUtils.isNotEmpty(token)) {
            if (token.startsWith(kpTokenProperties.getHead())) token = token.replace(kpTokenProperties.getHead(), "").trim();

            //token 过期
            if (!KPJWTUtil.verifyToken(token)) {
                backUtil.writeJson(AuthCodeEnum.FAILURE_TOKEN);
                return;
            }
            LoginUserTypeBO loginUserTypeBO = CommonUtil.toJavaObject(KPJWTUtil.parseToken(token).asString(), LoginUserTypeBO.class);

            //token 失效
            String redisToken = CommonUtil.get(RedisSecurityConstant.REDIS_AUTHENTICATION_TOKEN + loginUserTypeBO.getProjectCode() + ":" + loginUserTypeBO.getIdentification());
            if (StringUtils.isEmpty(redisToken)) {
                backUtil.writeJson(AuthCodeEnum.OVERDUE_TOKEN);
                return;
            } else {
                if (!kpTokenProperties.getMultiAccess() && !redisToken.equals(token)) {
                    backUtil.writeJson(AuthCodeEnum.ACCOUNT_NUMBER_RAPE);
                    return;
                }
            }

            String userMessage = CommonUtil.get(RedisSecurityConstant.REDIS_AUTHENTICATION_LOGINUSER_MESSAGE + loginUserTypeBO.getProjectCode() + ":" + loginUserTypeBO.getIdentification());
            if (StringUtils.isEmpty(userMessage)) {
                backUtil.writeJson(AuthCodeEnum.INVALID_TOKEN);
                return;
            }
            LoginUserBO loginUserBO = CommonUtil.toJavaObject(userMessage, LoginUserBO.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserBO, null, loginUserBO.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            this.setUserMessage(loginUserBO, loginUserTypeBO, request);

            if (loginUserTypeBO.getLoginType().equals(LoginUserTypeEnum.AUTHORIZATION.code())) {
                //授权登录只能访问open 和 api 对外接口
                if (!(request.getRequestURI().startsWith("/open") || request.getRequestURI().startsWith("/api"))) {
                    backUtil.writeJson(AuthCodeEnum.API_AUTHORIZATION);
                    return;
                }
            } else {
                //普通登录
                //token 延迟30分钟 过期  用户只要操作 一直往后延
                CommonUtil.expire(RedisSecurityConstant.REDIS_AUTHENTICATION_TOKEN + loginUserTypeBO.getProjectCode() + ":" + loginUserTypeBO.getIdentification(), kpTokenProperties.getExpireTime());
                CommonUtil.expire(RedisSecurityConstant.REDIS_AUTHENTICATION_LOGINUSER_MESSAGE + loginUserTypeBO.getProjectCode() + ":" + loginUserTypeBO.getIdentification(), kpTokenProperties.getExpireTime());
            }
        }
        chain.doFilter(request, response);
    }


    private void setUserMessage(LoginUserBO loginUserBO, LoginUserTypeBO loginUserTypeBO, HttpServletRequest request) {
        //记录用户信息， 全局记录日志使用
        try {
            String phone = "", serial = "";
            if (loginUserBO.getUser() != null) {
                phone = loginUserBO.getUser().getPhoneNumber();
                serial = loginUserBO.getUser().getJobNumber();
            }
            JSONObject userBo = new JSONObject()
                    .fluentPut("id", loginUserBO.getIdentification())
                    .fluentPut("name", loginUserBO.getName())
                    .fluentPut("phone", phone)
                    .fluentPut("serial", serial)
                    .fluentPut("loginType", loginUserTypeBO.getLoginType());
            request.setAttribute("userMessage", userBo);
        } catch (Exception ex) {
        }
    }
}
