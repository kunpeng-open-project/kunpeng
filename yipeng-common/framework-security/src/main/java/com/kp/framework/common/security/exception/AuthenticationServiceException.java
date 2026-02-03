package com.kp.framework.common.security.exception;

import lombok.Data;

/**
 * 业务异常。
 * @author lipeng
 * 2021/7/8
 */
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
