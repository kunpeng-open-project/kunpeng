package com.kunpeng.framework.annotation.verify;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @Author lipeng
 * @Description 非空校验
 * @Date 2024/3/11 10:39
 * @return
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface KPNotNull {
    /**
     * 错误信息
     **/
    String errMeg();
}
