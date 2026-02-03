package com.kp.framework.exception;

import lombok.Getter;

/**
 * linener分发异常。
 * @author lipeng
 * 2020/9/7
 */
@Getter
public class KPListenerDistributeException extends RuntimeException {

    private final String message;

    public KPListenerDistributeException(String message) {
        super(message);
        this.message = message;
    }

}
