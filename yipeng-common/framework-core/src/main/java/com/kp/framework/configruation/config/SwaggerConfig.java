package com.kp.framework.configruation.config;

import com.kp.framework.configruation.properties.KPFrameworkConfig;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * Swagger全局配置信息。
 * @author lipeng
 * 2025/12/25
 */
@Configuration
@DependsOn("KPServiceUtil")
@Slf4j
public class SwaggerConfig {

    @Autowired
    private KPFrameworkConfig kpFrameworkConfig;

    @Autowired
    private SwaggerOperationCustomizer operationCustomizer;

    @Autowired
    private SwaggerOpenApiCustomizer openApiCustomizer;

    @Value("${env:dev}")
    private String env;


    /**
     * 自定义接口文档的基础信息 + 配置JWT认证规范（按需授权，白名单接口无需令牌)
     * 参考地址  https://doc.xiaominfo.com/docs/blog/add-authorization-header
     * @author lipeng
     * 2025/12/25
     * @return io.swagger.v3.oas.models.OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("易鹏官方接口文档")
                        .contact(new Contact().name("李鹏").url("http://kpopen.cn").email("920297199@qq.com"))
                        .description(kpFrameworkConfig.getProjectName())
                        .version("v1.3.0")
                        .termsOfService("http://kpopen.cn")
                        .license(new License().name("Apache 2.0").url("http://kpopen.cn")))
                .components(new Components()
                        .addSecuritySchemes("Authorization",
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .description("JWT 认证令牌（格式：Bearer {token}）")));
        //.addSecurityItem(new SecurityRequirement().addList("Authorization"));
        // 注意：这里不再全局addSecurityItem，授权头按需添加 作用是让白名单的不需要Authorization
//         如果不加全局授权 需要每个接口加@Operation(security = { @SecurityRequirement(name = HttpHeaders.AUTHORIZATION) }) 或 @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
//        框架为了方便使用全局自动加  避免每个接口需要手动加注解
    }


    /**
     * 定义接口分组（核心：扫描接口并关联安全方案）。
     * @author lipeng
     * 2025/12/25
     * @return org.springdoc.core.models.GroupedOpenApi
     */
    @Bean
    public GroupedOpenApi apiGroup() {
        return GroupedOpenApi.builder()
                .group("default")
                .packagesToScan("com.kp.framework")
                .pathsToMatch("pro".equals(env) ? "/kp/none/**" : "/**")
                .addOpenApiCustomizer(openApiCustomizer)
                .addOperationCustomizer(operationCustomizer)
                .build();
    }
}