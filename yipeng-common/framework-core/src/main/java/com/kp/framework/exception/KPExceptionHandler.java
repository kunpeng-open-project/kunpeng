package com.kp.framework.exception;

import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.utils.kptool.KPStringUtil;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName KPExceptionHandler
 * @Description: 异常捕捉
 * @Author cloful
 * @Date 2021/5/13
 **/
@RestControllerAdvice
@Order(Integer.MAX_VALUE)
public class KPExceptionHandler {


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

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public KPResult maxUploadSizeExceededExceptionHandler(MaxUploadSizeExceededException e) {
        return KPResult.fail(KPStringUtil.format("单文件大小超过限制，服务端允许最大上传大小为 {0}MB", maxUploadSize(e.getMessage())));
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


    /**
     * @Author lipeng
     * @Description 获取当前最大运行上传的大小  可在配置文件中修改
     * spring:
     *   servlet:
     *     multipart:
     *       max-file-size: 1000MB    # 单个文件最大大小
     *       max-request-size: 1000MB  # 单次请求总大小
     * @Date 2025/11/2
     * @return double
     **/
    private static double  maxUploadSize(String exceptionMsg) {
        // 正则匹配错误信息中的字节数（针对"The field file exceeds its maximum permitted size of 10485760 bytes"格式）
        Pattern pattern = Pattern.compile("maximum permitted size of (\\d+) bytes");
        Matcher matcher = pattern.matcher(exceptionMsg);

        // 提取字节数
        long maxSizeBytes = 10485760; // 默认10MB对应的字节数
        if (matcher.find()) {
            try {
                maxSizeBytes = Long.parseLong(matcher.group(1));
            } catch (NumberFormatException e) {
                // 解析失败时使用默认值
            }
        }

        // 转换为MB（1MB = 1024 * 1024字节），保留1位小数
        return Math.round((maxSizeBytes / 1024.0 / 1024.0) * 10) / 10.0;
    }
}
