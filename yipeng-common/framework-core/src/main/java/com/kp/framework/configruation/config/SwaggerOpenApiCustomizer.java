package com.kp.framework.configruation.config;

import cn.hutool.core.text.AntPathMatcher;
import com.kp.framework.entity.bo.KPApiJsonParamParamBO;
import com.kp.framework.utils.kptool.KPReflectUtil;
import com.kp.framework.utils.kptool.KPServiceUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * swagger文档全局自定义器。
 * @author lipeng
 * 2025/12/30
 */
@Component
@Slf4j
public class SwaggerOpenApiCustomizer implements OpenApiCustomizer {

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Override
    public void customise(OpenAPI openApi) {
        // 构建权限校验器
        buildAuth(openApi);

        // 构建动态 schema
        buildDynamicSchema(openApi);
    }


    /**
     * 构建动态 schema。
     * @author lipeng
     * 2025/12/30
     * @param openApi openApi
     */
    private void buildDynamicSchema(OpenAPI openApi) {
        if (openApi.getComponents() == null)
            openApi.setComponents(new Components());

        // 把构建的所有动态 schema 注入到 OpenAPI 文档中
        if (KPStringUtil.isNotEmpty(openApi.getComponents().getSchemas()))
            openApi.getComponents().getSchemas().putAll(KPApiJsonParamParamBO.SCHEMAS);
    }

    /**
     * 自定义认证方案。
     * @author lipeng
     * 2025/12/25
     * @param openApi openApi
     */
    private void buildAuth(OpenAPI openApi) {
        // 获取Swagger解析的所有接口路径（key就是完整URL，比如/entrance/admin/replace/generator）
        Paths paths = openApi.getPaths();
        if (KPStringUtil.isEmpty(paths)) return;

        // 获取白名单
        List<?> excludeUrls = KPReflectUtil.getMethod(KPServiceUtil.getBean("KPPassConfig"), "getUrls", List.class);
        if (KPStringUtil.isEmpty(excludeUrls)) return;

        // 遍历所有接口路径，按需添加授权头
        for (Map.Entry<String, PathItem> entry : paths.entrySet()) {
            // 获取标准url
            String apiPath = entry.getKey().replaceAll("//+", "/").trim();
            PathItem pathItem = entry.getValue();

//                todo 单体待验证
            // 判断是否需要认证（匹配排除路径）
//                boolean needAuth = excludeUrls.stream().noneMatch(excludeUrl -> antPathMatcher.match(excludeUrl, apiPath));
            // 上面注释掉的是只适配服务器 下面的是微服务和单体springboot 都可行  但是单体待验证
            boolean needAuth = excludeUrls.stream().noneMatch(excludeUrl -> antPathMatcher.match(
                    KPStringUtil.isEmpty(String.valueOf(excludeUrl)) ? String.valueOf(excludeUrl) : contextPath + excludeUrl, apiPath
            ));

            if (needAuth) {
                // 给该路径下所有操作添加授权头
                SecurityRequirement security = new SecurityRequirement().addList("Authorization");
                pathItem.readOperations().forEach(op -> op.addSecurityItem(security));
//                    for (Operation operation : pathItem.readOperations()) {
//                        operation.addSecurityItem(new SecurityRequirement().addList("Authorization"));
//                    }
            } else {
                // 确保排除路径没有授权头
                pathItem.readOperations().forEach(op -> op.setSecurity(null));
            }
        }

    }
}
