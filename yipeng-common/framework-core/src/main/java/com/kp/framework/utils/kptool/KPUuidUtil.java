package com.kp.framework.utils.kptool;

import java.util.UUID;

public final class KPUuidUtil {


    /**
     * @Author lipeng
     * @Description 获取uuid
     * @Date 2023/12/8 16:10
     * @param
     * @return java.lang.String
     **/
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }


    /**
     * @Author lipeng
     * @Description 获取不带下划线的uuid
     * @Date 2023/12/8 16:10
     * @param
     * @return java.lang.String
     **/
    public static String getSimpleUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
