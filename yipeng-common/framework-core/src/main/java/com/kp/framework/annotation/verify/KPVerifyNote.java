package com.kp.framework.annotation.verify;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @Author lipeng
 * @Description 开启注解校验
 * @Date 2024/3/4 8:54
 * @return
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface KPVerifyNote {
}
