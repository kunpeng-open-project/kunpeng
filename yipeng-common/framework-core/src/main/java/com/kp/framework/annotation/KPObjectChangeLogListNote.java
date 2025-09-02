package com.kp.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @Author lipeng
 * @Description 记录对象改变内容
 * @Date 2024/2/22 17:13
 * @return
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface KPObjectChangeLogListNote {
    KPObjectChangeLogNote[] value();
}
