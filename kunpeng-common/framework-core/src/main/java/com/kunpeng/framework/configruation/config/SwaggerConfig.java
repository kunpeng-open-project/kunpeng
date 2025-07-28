package com.kunpeng.framework.configruation.config;


import com.beust.jcommander.internal.Lists;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.github.xiaoymin.knife4j.spring.configuration.Knife4jProperties;
import com.kunpeng.framework.configruation.properties.KunPengFrameworkConfig;
import com.kunpeng.framework.utils.kptool.KPReflectUtil;
import com.kunpeng.framework.utils.kptool.KPServiceUtil;
import com.kunpeng.framework.utils.kptool.KPStringUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static com.beust.jcommander.internal.Lists.newArrayList;

@Configuration
@EnableKnife4j
public class SwaggerConfig {

    @Autowired
    private Knife4jProperties knife4jProperties;
    @Autowired
    private KunPengFrameworkConfig kunPengFrameworkConfig;

    @Value("${env}")
    private String env;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securitySchemes(newArrayList(
                        new ApiKey("Authorization", "Authorization", In.HEADER.toValue())
                ))
                .select()
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("com.kunpeng.framework")) // 指定扫描的包
                .paths(env.equals("pro") ? PathSelectors.none() : PathSelectors.any()) // 指定扫描的路径
                .build()
                .securityContexts(createSecurityContexts());
    }

    /**
     * 创建安全上下文列表
     * @return 包含默认安全配置的SecurityContext列表
     */
    private List<SecurityContext> createSecurityContexts() {
        // 初始匹配所有路径
        Predicate pathSelector = PathSelectors.ant("/**");

        Object obj = KPReflectUtil.getMethod(KPServiceUtil.getBean("kunPengPassConfig"), "getUrls");
        if (KPStringUtil.isNotEmpty(obj)){
            for (String url : (List<String>) obj) {
                pathSelector = pathSelector.and(PathSelectors.ant(url).negate());
            }
        }

        // 返回包含安全上下文的列表
        return Lists.newArrayList(new SecurityContext(Lists.newArrayList(defaultAuth()), pathSelector));
    }

    private List<SecurityReference> defaultAuth() {
        //Authorization作用于全局所有接口
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(knife4jProperties.getOpenapi().getTitle())
//                .description(knife4jProperties.getOpenapi().getDescription())
                .description(kunPengFrameworkConfig.getProjectName())
                .termsOfServiceUrl(knife4jProperties.getOpenapi().getTermsOfServiceUrl())
                .contact(knife4jProperties.getOpenapi().getConcat())
                .version(knife4jProperties.getOpenapi().getVersion())
                .license(knife4jProperties.getOpenapi().getLicense())
                .licenseUrl(knife4jProperties.getOpenapi().getLicenseUrl())
                .build();
    }
}