package com.kp.framework.utils.kptool;

import java.security.SecureRandom;

public class KPAppUtil {

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz0123456789".toUpperCase();

    //token 过期时间 单位h
    public static final Integer TOKEN_FAILURE = 12;

    public static final Integer TOKEN_GAIN_MAX_NUM = 100;

    private static SecureRandom random = new SecureRandom();



    public static String getAppId() {
        return KPUuidUtil.getSimpleUUID();
    }


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
