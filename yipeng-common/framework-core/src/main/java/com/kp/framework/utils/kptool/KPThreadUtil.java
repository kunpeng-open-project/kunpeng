package com.kp.framework.utils.kptool;

import lombok.experimental.UtilityClass;


/**
 * @Author lipeng
 * @Description 线程工具类
 * @Date 2025/11/20 14:36
 * @return
 **/
@UtilityClass
public class KPThreadUtil {


    /**
     * @Author lipeng
     * @Description 线程休眠
     * @Date 2025/11/20
     * @param sleepTime
     * @return void
     **/
    public static void sleep(Integer sleepTime){
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {}
    }
}
