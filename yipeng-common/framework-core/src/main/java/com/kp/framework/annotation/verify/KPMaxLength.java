package com.kp.framework.annotation.verify;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 最大长度校验。
 * @author lipeng
 * 2024/3/11
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface KPMaxLength {
    /**
     * 错误信息
     **/
    String errMeg();

    int max();
}
