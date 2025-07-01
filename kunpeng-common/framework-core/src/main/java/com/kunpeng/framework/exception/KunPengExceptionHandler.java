package com.kunpeng.framework.exception;

import com.kunpeng.framework.entity.bo.KPResult;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @ClassName KunPengExceptionHandler
 * @Description: 异常捕捉
 * @Author cloful
 * @Date 2021/5/13
 **/
@RestControllerAdvice
@Order(Integer.MAX_VALUE)
public class KunPengExceptionHandler {


    @ExceptionHandler(KPServiceException.class)
    public KPResult serviceExceptionHandler(KPServiceException e) {
        return KPResult.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(KPUtilException.class)
    public KPResult authenticationExceptionHandler(KPUtilException e) {
        return KPResult.fail(e.getMessage());
    }

    @ExceptionHandler(KPListenerDistributeException.class)
    public KPResult listenerDistributeExceptionExceptionHandler(KPListenerDistributeException e) {
        return KPResult.fail(e.getMessage());
    }

    @ExceptionHandler(KPHttpDistributeException.class)
    public KPResult httpDistributeExceptionHandler(KPHttpDistributeException e) {
        return KPResult.fail(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public KPResult illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return KPResult.fail(e.getMessage());
    }

    // 捕获404
    @ExceptionHandler(NoHandlerFoundException.class)
    public KPResult handlerNoFoundException(NoHandlerFoundException e) {
        return KPResult.fail(e.getMessage());
//        return KPResult.failed("路径不存在，请检查路径是否正确");
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object validExceptionHandler(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return KPResult.fail(message);
    }


    // 捕获Exception
    @ExceptionHandler(Exception.class)
    public KPResult exceptionHandler(Exception e) {
        e.printStackTrace();
        return KPResult.fail(e.getMessage());
    }



}
