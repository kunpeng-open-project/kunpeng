package com.kp.framework.configruation.config;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.github.xiaoymin.knife4j.core.conf.ExtensionsConstants;
import io.swagger.v3.core.util.AnnotationsUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.customizers.OpenApiBuilderCustomizer;
import org.springdoc.core.customizers.ServerBaseUrlCustomizer;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.providers.JavadocProvider;
import org.springdoc.core.service.OpenAPIService;
import org.springdoc.core.service.SecurityService;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 解决knife4j-openapi3中@ApiSupport注解不生效的问题。
 * @author lipeng
 * 2026/1/30
 */
@Component
public class SwaggerTagConfig extends OpenAPIService {

    private final PropertyResolverUtils propertyResolverUtils;

    public SwaggerTagConfig(Optional<OpenAPI> openAPI, SecurityService securityParser, SpringDocConfigProperties springDocConfigProperties, PropertyResolverUtils propertyResolverUtils, Optional<List<OpenApiBuilderCustomizer>> openApiBuilderCustomizers, Optional<List<ServerBaseUrlCustomizer>> serverBaseUrlCustomizers, Optional<JavadocProvider> javadocProvider, PropertyResolverUtils propertyResolverUtils1) {
        super(openAPI, securityParser, springDocConfigProperties, propertyResolverUtils, openApiBuilderCustomizers, serverBaseUrlCustomizers, javadocProvider);
        this.propertyResolverUtils = propertyResolverUtils1;
    }

    /**
     * 重写父类获取tags方法
     */
    @Override
    public void buildTagsFromClass(Class<?> beanType, Set<io.swagger.v3.oas.models.tags.Tag> tags, Set<String> tagsStr, Locale locale) {
        Set<Tags> tagsSet = AnnotatedElementUtils.findAllMergedAnnotations(beanType, Tags.class);
        Set<Tag> classTags = tagsSet.stream().flatMap((x) -> Stream.of(x.value())).collect(Collectors.toSet());
        classTags.addAll(AnnotatedElementUtils.findAllMergedAnnotations(beanType, Tag.class));
        if (!CollectionUtils.isEmpty(classTags)) {
            tagsStr.addAll(classTags.stream().map((tag) -> this.propertyResolverUtils.resolve(tag.name(), locale)).collect(Collectors.toSet()));
            List<Tag> allTags = new ArrayList<>(classTags);
            this.addTags(beanType,allTags, tags, locale);
        }
    }

    /**
     * 根据父类方法，添加了beanType参数，方便获取类上其他注解
     */
    private void addTags(Class<?> beanType,List<Tag> sourceTags, Set<io.swagger.v3.oas.models.tags.Tag> tags, Locale locale) {
        ApiSupport apiSupport = AnnotationUtils.findAnnotation(beanType,ApiSupport.class);
        Optional<Set<io.swagger.v3.oas.models.tags.Tag>> optionalTagSet = AnnotationsUtils.getTags(sourceTags.toArray(new Tag[0]), false);  //第二个参数填true会忽略不在@Tag里写descripton的接口分组
        optionalTagSet.ifPresent(tagsSet -> tagsSet.forEach(tag -> {
            tag.name(propertyResolverUtils.resolve(tag.getName(), locale));
            tag.description(propertyResolverUtils.resolve(tag.getDescription(), locale));
            if(apiSupport != null){
                tag.addExtension(ExtensionsConstants.EXTENSION_ORDER, apiSupport.order());
            }
            if (tags.stream().noneMatch(t -> t.getName().equals(tag.getName())))
                tags.add(tag);
        }));
    }
}
