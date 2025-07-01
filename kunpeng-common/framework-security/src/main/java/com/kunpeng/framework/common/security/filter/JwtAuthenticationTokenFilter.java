package com.kunpeng.framework.common.security.filter;

import com.alibaba.fastjson2.JSONObject;
import com.kunpeng.framework.common.enums.AuthCodeEnum;
import com.kunpeng.framework.common.enums.LoginUserTypeEnum;
import com.kunpeng.framework.common.properties.KunPengPassConfig;
import com.kunpeng.framework.common.properties.KunPengTokenProperties;
import com.kunpeng.framework.common.properties.RedisSecurityConstant;
import com.kunpeng.framework.common.util.CommonUtil;
import com.kunpeng.framework.common.util.KPJWTUtil;
import com.kunpeng.framework.modules.user.po.customer.LoginUserBO;
import com.kunpeng.framework.modules.user.po.customer.LoginUserTypeBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author lipeng
 * @Description 校验token tokeng过滤器
 * @Date 2024/4/19 23:32
 * @return
 **/
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Autowired
    private KunPengTokenProperties kunPengTokenProperties;

    @Autowired
    private KunPengPassConfig kunPengPassConfig;

    @Value("${kp.project-name}")
    private String projectName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String url : kunPengPassConfig.getUrls()) {
            if (pathMatcher.match((url), request.getRequestURI())) {
                chain.doFilter(request, response);
                return;
            }
        }

        String token = request.getHeader(kunPengTokenProperties.getHeader());
        if (StringUtils.isNotEmpty(token)) {
            if (token.startsWith(kunPengTokenProperties.getHead())) token = token.replace(kunPengTokenProperties.getHead(), "").trim();

            //token 过期
            if (!KPJWTUtil.verifyToken(token)) {
                JSONObject body = new JSONObject()
                        .fluentPut("code", AuthCodeEnum.FAILURE_TOKEN.code())
                        .fluentPut("message", AuthCodeEnum.FAILURE_TOKEN.message());
                CommonUtil.writeJson(body, projectName);
                return;
            }
            LoginUserTypeBO loginUserTypeBO = CommonUtil.toJavaObject(KPJWTUtil.parseToken(token).asString(), LoginUserTypeBO.class);

            //token 失效
            String redisToken = CommonUtil.get(RedisSecurityConstant.REDIS_AUTHENTICATION_TOKEN + loginUserTypeBO.getProjectCode() + ":" + loginUserTypeBO.getIdentification());
            if (StringUtils.isEmpty(redisToken)) {
                JSONObject body = new JSONObject()
                        .fluentPut("code", AuthCodeEnum.OVERDUE_TOKEN.code())
                        .fluentPut("message", AuthCodeEnum.OVERDUE_TOKEN.message());
                CommonUtil.writeJson(body, projectName);
                return;
            } else {
                if (!kunPengTokenProperties.getMultiAccess() && !redisToken.equals(token)) {
                    JSONObject body = new JSONObject()
                            .fluentPut("code", AuthCodeEnum.ACCOUNT_NUMBER_RAPE.code())
                            .fluentPut("message", AuthCodeEnum.ACCOUNT_NUMBER_RAPE.message());
                    CommonUtil.writeJson(body, projectName);
                    return;
                }
            }

            String userMessage = CommonUtil.get(RedisSecurityConstant.REDIS_AUTHENTICATION_LOGINUSER_MESSAGE + loginUserTypeBO.getProjectCode() + ":" + loginUserTypeBO.getIdentification());
            if (StringUtils.isEmpty(userMessage)) {
                JSONObject body = new JSONObject()
                        .fluentPut("code", AuthCodeEnum.INVALID_TOKEN.code())
                        .fluentPut("message", AuthCodeEnum.INVALID_TOKEN.message());
                CommonUtil.writeJson(body, projectName);
                return;
            }
            LoginUserBO loginUserBO = CommonUtil.toJavaObject(userMessage, LoginUserBO.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserBO, null, loginUserBO.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // TODO: 2025/4/21 qeqww
            this.setUserMessage(loginUserBO, loginUserTypeBO, request);

            //普通登录
            if (loginUserTypeBO.getLoginType().equals(LoginUserTypeEnum.COMMON.code())) {
                //token 延迟30分钟 过期  用户只要操作 一直往后延
                CommonUtil.expire(RedisSecurityConstant.REDIS_AUTHENTICATION_TOKEN + loginUserTypeBO.getProjectCode() + ":" + loginUserTypeBO.getIdentification(), kunPengTokenProperties.getExpireTime());
                CommonUtil.expire(RedisSecurityConstant.REDIS_AUTHENTICATION_LOGINUSER_MESSAGE + loginUserTypeBO.getProjectCode() + ":" + loginUserTypeBO.getIdentification(), kunPengTokenProperties.getExpireTime());
            } else {
                //授权登录只能访问open 和 api 对外接口
                if (!(request.getRequestURI().startsWith("/open") || request.getRequestURI().startsWith("/api"))) {
                    JSONObject body = new JSONObject()
                            .fluentPut("code", AuthCodeEnum.API_AUTHORIZATION.code())
                            .fluentPut("message", AuthCodeEnum.API_AUTHORIZATION.message());
                    CommonUtil.writeJson(body, projectName);
                    return;
                }
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
