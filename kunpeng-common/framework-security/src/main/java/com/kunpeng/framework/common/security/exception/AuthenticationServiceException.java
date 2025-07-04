package com.kunpeng.framework.common.security.exception;


import lombok.Data;

/**
 * @Author lipeng
 * @Description 业务异常
 * @Date 2021/7/8
 * @return
 **/
@Data
public class AuthenticationServiceException extends RuntimeException {

    private String message;

    private Integer code;



    @Deprecated
    public AuthenticationServiceException(Integer code, String message){
        super(message);
        this.code = code;
        this.message = message;
    }

}
