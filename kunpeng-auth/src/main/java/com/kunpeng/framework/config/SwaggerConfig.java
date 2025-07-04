package com.kunpeng.framework.config;//package com.kunpeng.framework.config;
//
//import cn.hutool.core.collection.CollectionUtil;
//import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
//import com.github.xiaoymin.knife4j.spring.configuration.Knife4jProperties;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.OAuthBuilder;
//import springfox.documentation.builders.ParameterBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.schema.ModelRef;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.ApiKey;
//import springfox.documentation.service.AuthorizationScope;
//import springfox.documentation.service.GrantType;
//import springfox.documentation.service.OAuth;
//import springfox.documentation.service.Parameter;
//import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
//import springfox.documentation.service.SecurityReference;
//import springfox.documentation.service.SecurityScheme;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.beust.jcommander.internal.Lists.newArrayList;
//
//@Configuration
////@EnableSwagger2
//@EnableKnife4j
//public class SwaggerConfig {
//
//    @Autowired
//    private Knife4jProperties knife4jProperties;
//
//    @Value("${env}")
//    private String env;
//
//    @Bean
//    public Docket createRestApi() {
//        // 添加全局参数
//        ParameterBuilder tokenParam = new ParameterBuilder();
//        tokenParam.name("Authorization") // 参数名
//                .description("Bearer Token") // 参数描述
//                .modelRef(new ModelRef("string")) // 参数类型
//                .parameterType("header") // 参数类型为 header
//                .required(true) // 是否必填
//                .defaultValue("Bearer swagger-token") // 默认值
//                .build();
//
//        List<Parameter> parameters = new ArrayList<>();
//        parameters.add(tokenParam.build());
//
//        //密码模式
//        List<GrantType> grantTypes = new ArrayList<>();
//        String passwordTokenUrl = "http://127.0.0.1:9001/auth/user/swagger/login";
//        grantTypes.add(new ResourceOwnerPasswordCredentialsGrant(passwordTokenUrl));
//        OAuth oAuth = new OAuthBuilder().name("oauth2").grantTypes(grantTypes).build();
//
//        List<AuthorizationScope> scopes = new ArrayList<>();
//        scopes.add(new AuthorizationScope("read", "read all resources"));
//        SecurityReference securityReference = new SecurityReference("oauth2", scopes.toArray(new AuthorizationScope[]{}));
//        SecurityContext securityContext= new SecurityContext(CollectionUtil.newArrayList(securityReference),PathSelectors.ant("/api/**"));
//        //securyContext
//        List<SecurityContext> securityContexts=CollectionUtil.newArrayList(securityContext);
//        //schemas
//        List<SecurityScheme> securitySchemes = CollectionUtil.newArrayList(oAuth);
//
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
////                .securitySchemes(newArrayList(
////                        new ApiKey("Authorization", "Authorization", "header")
////                ))
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.kunpeng.framework")) // 指定扫描的包
//                .paths(env.equals("pro") ? PathSelectors.none() : PathSelectors.any()) // 指定扫描的路径
//                .build();
////                .globalOperationParameters(parameters) // 添加全局参数
////                .securityContexts(securityContexts)
////                .securitySchemes(securitySchemes);
////                .groupName(knife4jProperties.getOpenapi().getGroup().get("default11").getGroupName());
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title(knife4jProperties.getOpenapi().getTitle())
//                .description(knife4jProperties.getOpenapi().getDescription())
//                .termsOfServiceUrl(knife4jProperties.getOpenapi().getTermsOfServiceUrl())
//                .contact(knife4jProperties.getOpenapi().getConcat())
//                .version(knife4jProperties.getOpenapi().getVersion())
//                .license(knife4jProperties.getOpenapi().getLicense())
//                .licenseUrl(knife4jProperties.getOpenapi().getLicenseUrl())
//                .build();
//    }
//}