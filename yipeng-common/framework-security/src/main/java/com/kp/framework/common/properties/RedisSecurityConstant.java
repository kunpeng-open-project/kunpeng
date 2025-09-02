package com.kp.framework.common.properties;

public class RedisSecurityConstant {

    public static final String AUTHENTICATION = "authentication:";

    //token
    public static final String REDIS_AUTHENTICATION_TOKEN = AUTHENTICATION.concat("accessToken:");

    //refreshToken 刷新token  目前没启用
    public static final String REDIS_AUTHENTICATION_REFRESHTOKEN = AUTHENTICATION.concat("refreshToken:");

    //管理端用户信息
    public static final String REDIS_AUTHENTICATION_LOGINUSER_MESSAGE = AUTHENTICATION.concat("userMessage:");

    //用户登录锁定记录次数
    public static final String LOGIN_ERROR_NUMBER = AUTHENTICATION.concat("login:errorNumber");

    //授权登录次数
    public static final String AUTHORIZATION_NUMBER = AUTHENTICATION.concat("appId:number");
}
