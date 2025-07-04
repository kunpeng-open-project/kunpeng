package com.kunpeng.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * @Author lipeng
 * @Description
 * @Date 2023/12/20 8:59
 * @return
 **/
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KPRepeatSubmitNote {

    // 间隔时间  单位毫秒
    int value() default 800;
}
