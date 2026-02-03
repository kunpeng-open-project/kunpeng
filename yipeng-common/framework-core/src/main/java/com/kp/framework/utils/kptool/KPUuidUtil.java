package com.kp.framework.utils.kptool;

import lombok.experimental.UtilityClass;

import java.util.UUID;

/**
 * uuid操作工具类。
 * @author lipeng
 * 2023/12/8
 */
@UtilityClass
public final class KPUuidUtil {

    /**
     * 获取uuid。
     * @author lipeng
     * 2023/12/8
     * @return java.lang.String
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取不带下划线的uuid。
     * @author lipeng
     * 2023/12/8
     * @return java.lang.String
     */
    public static String getSimpleUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
