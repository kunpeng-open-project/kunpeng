package com.kp.framework.utils.kptool;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

@UtilityClass
public class KPAuthorizationUtil {

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz0123456789".toUpperCase();

    //token 过期时间 单位h
    public static final Integer TOKEN_FAILURE = 12;

    // 单个AppId允许获取Token的最大次数（根据业务场景调整注释）
    public static final Integer TOKEN_GAIN_MAX_NUM = 100;

    private static SecureRandom random = new SecureRandom();



    /**
     * @Author lipeng
     * @Description 获取授权appId
     * @Date 2025/11/4
     * @param
     * @return java.lang.String
     **/
    public static String getAppId() {
        return KPUuidUtil.getSimpleUUID();
    }


    /**
     * @Author lipeng
     * @Description 获取授权appSecret
     * @Date 2025/11/4
     * @param length
     * @return java.lang.String
     **/
    public static String getAppSecret(int length) {
        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int rndCharAt = random.nextInt(CHAR_LOWER.length());
            char rndChar = CHAR_LOWER.charAt(rndCharAt);

            sb.append(rndChar);
        }
        return sb.toString();
    }

}
