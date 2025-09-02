package com.kp.framework.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface KPApiJsonlParamMode {

    //分隔符 默认是英文逗号
    String separator() default ",";

    //目标实体类
    Class<?> component();

    //忽略的字段
    String ignores() default "";

    //包含的字段
    String includes() default "";
}