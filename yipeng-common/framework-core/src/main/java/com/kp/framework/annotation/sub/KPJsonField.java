package com.kp.framework.annotation.sub;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述 JSON 请求体中的一个字段。
 * @author lipeng
 * 2025/12/26
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface KPJsonField {
    /**
     * JSON 中的字段名（key）
     */
    String name();

    /**
     * 字段描述（在 Swagger/Knife4j 中显示）
     */
    String description() default "";

    /**
     * 是否必填
     */
    boolean required() default false;

    /**
     * 示例值（用于文档展示）
     */
    String example() default "";

    /**
     * 字段类型（可选，用于智能推断 Schema 类型）
     * 支持: "string", "integer", "number", "boolean"
     * 默认为 "string"
     */
    String dataType() default "string";
}
