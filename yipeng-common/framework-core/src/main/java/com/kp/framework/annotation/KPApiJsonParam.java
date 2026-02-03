package com.kp.framework.annotation;

import com.kp.framework.annotation.sub.KPJsonField;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * swagger 注解扩展。
 * 对入参json形式 对入参进行说明
 * 重新适配OpenAPI3
 * @author lipeng
 * 2026/1/2
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface KPApiJsonParam {
    /**
     * 原生Swagger注解 用于描述具体字段
     * accessMode,extensions配置将无效
     *
     * @return value
     */
    KPJsonField[] value() default {};
}
