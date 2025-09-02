package com.kp.framework.exception;

import com.kp.framework.entity.internal.ResultCode;
import com.kp.framework.enums.IErrorCodeEnum;
import lombok.Data;

/**
 * @Author lipeng
 * @Description 业务异常
 * @Date 2021/7/8 22:30
 * @return
 **/
@Data
public class KPServiceException extends RuntimeException {

    private String message;

    private Integer code;

    public KPServiceException(String message){
        super(message);
        this.code = ResultCode.FAILED.code();
        this.message = message;
    }


    @Deprecated
    public KPServiceException(Integer code, String message){
        super(message);
        this.code = code;
        this.message = message;
    }

    public KPServiceException(IErrorCodeEnum iErrorCodeEnum){
        super(iErrorCodeEnum.message());
        this.code = iErrorCodeEnum.code();
        this.message = iErrorCodeEnum.message();
    }

    public KPServiceException(IErrorCodeEnum iErrorCodeEnum, String message){
        super(message);
        this.code = iErrorCodeEnum.code();
        this.message = message;
    }

}
