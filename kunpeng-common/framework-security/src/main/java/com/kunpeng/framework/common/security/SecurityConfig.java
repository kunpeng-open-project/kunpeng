package com.kunpeng.framework.common.security;

import com.kunpeng.framework.common.properties.KunPengPassConfig;
import com.kunpeng.framework.common.security.filter.AuthenticationEntryPointImpl;
import com.kunpeng.framework.common.security.filter.JwtAuthenticationTokenFilter;
import com.kunpeng.framework.common.security.filter.LogoutSuccessHandlerImpl;
import com.kunpeng.framework.common.security.filter.UserAuthAccessDeniedHandler;
import com.kunpeng.framework.common.security.filter.UserAuthenticationEntryPointHandler;
import com.kunpeng.framework.common.security.verify.UserDetailsCheck;
import com.kunpeng.framework.common.security.verify.UserPermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * @Author lipeng
 * @Description spring security配置
 * @Date 2024/11/21 12:49
 * @return
 **/
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private KunPengPassConfig kunPengPassConfig;
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

    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity.authorizeRequests();

        for (String url : kunPengPassConfig.getUrls()) registry.antMatchers(url).permitAll();

        registry.antMatchers(HttpMethod.OPTIONS).permitAll() //  允许跨域请求的OPTIONS请求
                .antMatchers("/auth/dept/page/list").permitAll()
                .anyRequest().authenticated()  //除上面外的所有请求全部需要鉴权认证
                .and().headers().frameOptions().disable() // 禁用X-Frame-Options头，允许页面嵌入到iframe中
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 设置会话管理策略为无状态，基于token认证，不需要session
                .and().cors() //开启跨域支持
                .and().csrf().disable() // CSRF禁用，因为不使用session 取消跨站请求伪造防护
                .exceptionHandling() //配置异常处理
                .accessDeniedHandler(userAuthAccessDeniedHandler) //设置访问拒绝处理器
                .authenticationEntryPoint(authenticationEntryPointImpl) //设置未认证访问处理器
                .and().httpBasic().authenticationEntryPoint(userAuthenticationEntryPointHandler) //启用HTTP基本认证，并设置未认证访问处理器
                .and().logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler) //配置注销功能，指定注销URL和注销成功处理器
                .and().addFilterBefore(jwtAuthenticationTokenFilter, LogoutFilter.class); // 添加JWT 在LogoutFilter之前添加JWT认证过滤器  如果使用UsernamePasswordAuthenticationFilter 过滤器 会导致 logoutSuccessHandler 失效
//         .and().addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class); // 添加JWT filter
    }


    /**
     * 注入自定义PermissionEvaluator
     */
    @Bean
    public DefaultWebSecurityExpressionHandler userSecurityExpressionHandler(){
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new UserPermissionEvaluator());
        return handler;
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
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }




//    @Bean
//    public CorsFilter corsFilter() {
//        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new  UrlBasedCorsConfigurationSource();
//        final CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//        return new CorsFilter((CorsConfigurationSource) urlBasedCorsConfigurationSource);
//    }




    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }


//    @Bean
//    public CorsFilter corsFilter() {
//        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        final CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowCredentials(true); /*是否允许请求带有验证信息*/
////        corsConfiguration.addAllowedOrigin("*");/*允许访问的客户端域名*/
//        corsConfiguration.addAllowedHeader("*");/*允许服务端访问的客户端请求头*/
//        corsConfiguration.addAllowedOriginPattern("*");
//        corsConfiguration.addAllowedMethod("*"); /*允许访问的方法名,GET POST等*/
//        corsConfiguration.addExposedHeader("token");/*暴露哪些头部信息 不能用*因为跨域访问默认不能获取全部头部信息*/
//        corsConfiguration.addExposedHeader("TOKEN");
//        corsConfiguration.addExposedHeader("Authorization");
//        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//        return new CorsFilter(urlBasedCorsConfigurationSource);
//    }
}
