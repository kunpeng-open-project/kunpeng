package com.kp.framework.annotation.impl;


import com.kp.framework.annotation.KPApiJsonlParamMode;
import com.kp.framework.annotation.impl.util.ApiJsonlParamModeUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;

import java.util.Optional;


@Component
@Order
@Slf4j
public class KPApiJsonlParamModeBuilder implements ParameterBuilderPlugin {

    @Autowired
    private ApiJsonlParamModeUtil apiJsonlParamModeUtil;


    @Override
    public void apply(ParameterContext parameterContext) {
        // 从方法或参数上获取指定注解的Optional
        Optional<KPApiJsonlParamMode> optional = parameterContext.getOperationContext().findAnnotation(KPApiJsonlParamMode.class);
        if (!optional.isPresent()) optional = parameterContext.resolvedMethodParameter().findAnnotation(KPApiJsonlParamMode.class);
        if (!optional.isPresent()) return;


        KPApiJsonlParamMode apiAnno = optional.get();

        if (KPStringUtil.isNotEmpty(apiAnno.includes())){
            apiJsonlParamModeUtil.include(apiAnno, parameterContext);
        }else if (KPStringUtil.isNotEmpty(apiAnno.ignores())){
            apiJsonlParamModeUtil.ignores(apiAnno, parameterContext);
        }
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }


}
