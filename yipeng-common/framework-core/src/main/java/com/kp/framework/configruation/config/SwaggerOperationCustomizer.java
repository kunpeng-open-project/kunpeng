package com.kp.framework.configruation.config;

import com.kp.framework.annotation.impl.KPApiJsonParamBuilder;
import com.kp.framework.annotation.impl.KPApiJsonParamModeBuilder;
import io.swagger.v3.oas.models.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

/**
 * 接口文档自定义器。
 * @author lipeng
 * 2025/12/30
 */
@Component
@Slf4j
public class SwaggerOperationCustomizer implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        //构建swagger动态参数@KPApiJsonParam注解实现
        KPApiJsonParamBuilder.builder(operation, handlerMethod);

        KPApiJsonParamModeBuilder.builder(operation, handlerMethod);

        return operation;
    }
}