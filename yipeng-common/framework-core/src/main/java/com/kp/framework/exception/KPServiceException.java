package com.kp.framework.exception;

import com.kp.framework.entity.internal.ResultCode;
import com.kp.framework.enums.IErrorCodeEnum;
import lombok.Getter;


/**
 * 业务异常。
 * @author lipeng
 * 2021/7/8
 */
@Getter
public class KPServiceException extends RuntimeException {

    private final String message;

    private final Integer code;

    public KPServiceException(String message) {
        super(message);
        this.code = ResultCode.FAILED.code();
        this.message = message;
    }


    //    @Deprecated
    public KPServiceException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public KPServiceException(IErrorCodeEnum iErrorCodeEnum) {
        super(iErrorCodeEnum.message());
        this.code = iErrorCodeEnum.code();
        this.message = iErrorCodeEnum.message();
    }

    public KPServiceException(IErrorCodeEnum iErrorCodeEnum, String message) {
        super(message);
        this.code = iErrorCodeEnum.code();
        this.message = message;
    }

}
