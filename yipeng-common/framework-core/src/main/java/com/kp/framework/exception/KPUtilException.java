package com.kp.framework.exception;

import lombok.Getter;

/**
 * 工具类异常。
 * @author lipeng
 * 2025/1/17
 */
@Getter
public class KPUtilException extends RuntimeException {

    private final String message;

    public KPUtilException(String message) {
        super(message);
        this.message = message;
    }

}
