package com.kp.framework.exception;

/**
 * @Author lipeng
 * @Description linener分发异常
 * @Date 2020/9/7 13:38
 * @Param
 * @return
 **/
public class KPListenerDistributeException extends RuntimeException  {

    private String message;

    public KPListenerDistributeException(String message){
        super(message);
        this.message = message;
    }

}
