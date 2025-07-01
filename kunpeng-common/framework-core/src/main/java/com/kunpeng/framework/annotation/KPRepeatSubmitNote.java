package com.kunpeng.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 自定义注解防止表单重复提交
 *
 * @author Chen Haidong lipeng
 *
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KPRepeatSubmitNote {

    // 间隔时间  单位毫秒
    int value() default 800;
}
