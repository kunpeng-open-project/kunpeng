package com.kunpeng.framework.exception;

/**
 * @Author lipeng
 * @Description HTTP请求异常
 * @Date 2020/9/7 13:38
 * @Param
 * @return
 **/
public class KPHttpDistributeException extends RuntimeException  {

    private String message;

    public KPHttpDistributeException(String message){
        super(message);
        this.message = message;
    }

}
