package com.kunpeng.framework.utils.kptool;

public class KPThreadUtil {

    public static void sleep(Integer sleepTime){
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {}
    }
}
