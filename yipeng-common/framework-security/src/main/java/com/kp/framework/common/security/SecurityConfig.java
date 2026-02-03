package com.kp.framework.common.security;

import com.kp.framework.common.properties.KPPassConfig;
import com.kp.framework.common.security.filter.AuthenticationEntryPointImpl;
import com.kp.framework.common.security.filter.JwtAuthenticationTokenFilter;
import com.kp.framework.common.security.filter.LogoutSuccessHandlerImpl;
import com.kp.framework.common.security.filter.UserAuthAccessDeniedHandler;
import com.kp.framework.common.security.filter.UserAuthenticationEntryPointHandler;
import com.kp.framework.common.security.verify.UserDetailsCheck;
import com.kp.framework.common.security.verify.UserPermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

/**
 * spring security配置。
 * @author lipeng
 * 2024/11/21
 */
// prePostEnabled = true：启用 Pre/Post 系列注解（支持 SpEL 表达式，推荐优先使用）
//securedEnabled = true：启用 @Secured 注解（兼容旧版简单角色控制）
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private KPPassConfig kpPassConfig;
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;
    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPointImpl;
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private UserAuthenticationEntryPointHandler userAuthenticationEntryPointHandler;
    @Autowired
    private UserAuthAccessDeniedHandler userAuthAccessDeniedHandler;
    @Autowired
    private UserDetailsCheck userDetailsService;
    @Autowired
    private UserPermissionEvaluator permissionEvaluator;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // 配置需要放行的URL
        List<String> permitUrls = kpPassConfig.getUrls();
        AntPathRequestMatcher[] matchers = permitUrls.stream()
                .map(AntPathRequestMatcher::new)
                .toArray(AntPathRequestMatcher[]::new);

        httpSecurity
                .authorizeHttpRequests(authz -> authz  // 配置授权规则
                        .requestMatchers(matchers).permitAll()  // 放行配置的URL
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()  // 允许跨域请求的OPTIONS请求
                        .anyRequest().authenticated()  // 除上面外的所有请求全部需要鉴权认证
                )
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))  // 禁用X-Frame-Options头，允许页面嵌入到iframe中（如果需要安全考虑，可以移除或修改此项）
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 设置会话管理策略为无状态，基于token认证，不需要session
                .cors(cors -> {
                })  // 开启跨域支持
                .csrf(AbstractHttpConfigurer::disable)  // CSRF禁用，因为不使用session 取消跨站请求伪造防护
                .exceptionHandling(exception -> exception  // 配置异常处理
                        .accessDeniedHandler(userAuthAccessDeniedHandler)  // 设置访问拒绝处理器
                        .authenticationEntryPoint(authenticationEntryPointImpl)   // 设置未认证访问处理器
                )
//                 JWT 无状态认证 不应该启用 HTTP Basic。
//                .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(userAuthenticationEntryPointHandler)) // 启用HTTP基本认证，并设置未认证访问处理器
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler)) // 配置注销功能
                .addFilterBefore(jwtAuthenticationTokenFilter, LogoutFilter.class);  // // 添加JWT 在LogoutFilter之前添加JWT认证过滤器  如果使用UsernamePasswordAuthenticationFilter 过滤器 会导致 logoutSuccessHandler 失效

        return httpSecurity.build();
    }


    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 解决 无法直接注入 AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 配置认证管理器
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        auth.userDetailsService(userDetailsService);
    }

    /**
     * 配置自定义权限注解验证
     */
    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(permissionEvaluator);
        return handler;
    }

 /*
    // 如果需要跨域配置，可以使用以下方式
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOriginPattern("*"); // 使用AllowedOriginPattern替代addAllowedOrigin
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addExposedHeader("token");
        configuration.addExposedHeader("TOKEN");
        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    */
}
