package com.kp.framework.exception;

import lombok.Getter;

/**
 * HTTP请求异常。
 * @author lipeng
 * 2020/9/7
 */
@Getter
public class KPHttpDistributeException extends RuntimeException {

    private final String message;

    public KPHttpDistributeException(String message) {
        super(message);
        this.message = message;
    }

}
