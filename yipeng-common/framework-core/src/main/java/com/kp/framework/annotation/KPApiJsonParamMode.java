package com.kp.framework.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * swagger 注解扩展。从已有实体类抽取或者排除指定字段 变成新的入参
 * 对入参json形式 对入参进行说明
 * 重新适配OpenAPI3
 * @author lipeng
 * 2026/1/2
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface KPApiJsonParamMode {

    //分隔符 默认是英文逗号
    String separator() default ",";

    //目标实体类
    Class<?> component();

    //忽略的字段
    String ignores() default "";

    //包含的字段
    String includes() default "";
}