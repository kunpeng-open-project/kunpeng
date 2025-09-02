package com.kp.framework.exception;


/**
 * @Author lipeng
 * @Description 工具类异常
 * @Date 2025/1/17 17:19
 * @return
 **/
public class KPUtilException extends RuntimeException {

    private String message;

    public KPUtilException(String message){
        super(message);
        this.message = message;
    }

}
