package com.kp.framework.utils.kptool;

import lombok.experimental.UtilityClass;

/**
 * 线程工具类。
 * @author lipeng
 * 2025/11/20
 */
@UtilityClass
public class KPThreadUtil {

    /**
     * 线程休眠。
     * @author lipeng
     * 2025/11/20
     * @param sleepTime 休眠时间
     */
    public static void sleep(Integer sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
        }
    }
}
