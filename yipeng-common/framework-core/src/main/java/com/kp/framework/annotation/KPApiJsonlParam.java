package com.kp.framework.annotation;

import io.swagger.annotations.ApiModelProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author lipeng
 * swagger 注解扩展
 * @Description 对入参json形式 对入参进行说明
 * @Date 2022/5/19 19:25
 * @KPApiJsonlParam({
 *             @ApiModelProperty(name = "id", value = "收件人电话"),
 *             @ApiModelProperty(name = "telNumber", value = "收货地址id")
 *     })
 * @return
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface KPApiJsonlParam {
    /**
     * 原生Swagger注解 用于描述具体字段
     * accessMode,extensions配置将无效
     *
     * @return value
     */
    ApiModelProperty[] value() default {};
}
