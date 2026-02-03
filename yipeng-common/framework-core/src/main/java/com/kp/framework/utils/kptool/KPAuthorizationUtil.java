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
     * 获取授权appId。
     * @author lipeng
     * 2025/11/4
     * @return java.lang.String
     */
    public static String getAppId() {
        return KPUuidUtil.getSimpleUUID();
    }

    /**
     * 获取授权appSecret。
     * @author lipeng
     * 2025/11/4
     * @param length 随机字符串长度
     * @return java.lang.String
     */
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
