package com.kp.framework.annotation.impl;

import com.kp.framework.annotation.KPApiJsonParamMode;
import com.kp.framework.entity.bo.KPApiJsonParamParamBO;
import com.kp.framework.utils.kptool.KPReflectUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.BooleanSchema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.NumberSchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import lombok.experimental.UtilityClass;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * KPApiJsonParamMode 注解实现方式。
 * @author lipeng
 * 2026/1/1
 */
@UtilityClass
public class KPApiJsonParamModeBuilder {

    //包含默认类名
    private final static String Mode_CLASS_NAME = "KPJsonParamModeBO";
    //序号 防止重名
    private static Integer i = 1;


    public Operation builder(Operation operation, HandlerMethod handlerMethod) {
        KPApiJsonParamMode kpApiJsonParamMode = handlerMethod.getMethodAnnotation(KPApiJsonParamMode.class);
        if (kpApiJsonParamMode == null || kpApiJsonParamMode.component() == null) {
            return operation;
        }

        // 生成唯一 schema 名称
        String schemaName = Mode_CLASS_NAME + i++;
        // 获取所有字段（包括父类）
        List<Field> allFields = KPReflectUtil.getAllDeclaredFields(kpApiJsonParamMode.component());

        Set<String> includeSet = Set.of();
        Set<String> ignoreSet = Set.of();

        if (KPStringUtil.isNotEmpty(kpApiJsonParamMode.includes())) {
            includeSet = Set.of(kpApiJsonParamMode.includes().split(kpApiJsonParamMode.separator()));
        } else if (KPStringUtil.isNotEmpty(kpApiJsonParamMode.ignores())) {
            ignoreSet = Set.of(kpApiJsonParamMode.ignores().split(kpApiJsonParamMode.separator()));
        }

        ObjectSchema schema = new ObjectSchema();
        Map<String, Schema> properties = new LinkedHashMap<>();
        List<String> required = new ArrayList<>();

        for (Field field : allFields) {
            String fieldName = field.getName();
            if ("serialVersionUID".equals(fieldName)) continue;

            // 判断是否包含/排除
            if (!includeSet.isEmpty() && !includeSet.contains(fieldName)) continue;
            if (!ignoreSet.isEmpty() && ignoreSet.contains(fieldName)) continue;

            // 从字段上读取 @Schema 或其他元数据
            String description = "";
            String example = "";
            boolean isRequired = false;

            io.swagger.v3.oas.annotations.media.Schema fieldSchemaAnn = field.getAnnotation(io.swagger.v3.oas.annotations.media.Schema.class);
            if (fieldSchemaAnn != null) {
                description = fieldSchemaAnn.description();
                example = fieldSchemaAnn.example();
                // 同时兼容老写法和新写法
                isRequired = fieldSchemaAnn.required() ||
                        fieldSchemaAnn.requiredMode() == io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;
                // 注意：name 属性在 JSON 序列化中通常由 Jackson 控制，Swagger 只展示 key
                // 所以我们仍用 fieldName 作为 key
            }

            // 推断类型
            Schema<?> fieldSchema = buildSchemaFromJavaType(field.getType(), field.getGenericType());

            fieldSchema.description(description);
            if (KPStringUtil.isNotEmpty(example)) {
                fieldSchema.example(example);
            }
            properties.put(fieldName, fieldSchema);
            if (isRequired) required.add(fieldName);
        }

        schema.setProperties(properties);
        if (!required.isEmpty()) schema.setRequired(required);

        // 注册到全局（和你之前一致）
        KPApiJsonParamParamBO.SCHEMAS.put(schemaName, schema);

        // 设置 RequestBody
        Schema<?> refSchema = new Schema<>();
        refSchema.set$ref("#/components/schemas/" + schemaName);

        RequestBody body = new RequestBody()
                .content(new Content()
                        .addMediaType("application/json", new MediaType().schema(refSchema)))
                .description("JSON请求参数）")
                .required(true);

        operation.setRequestBody(body);
        operation.setParameters(null); // 清除 query 参数
        return operation;
    }


    /**
     * 根据 Java 类型构建 OpenAPI Schema。
     * @author lipeng
     * 2026/1/1
     * @param type Java 类型
     * @param genericType 泛型类型
     * @return io.swagger.v3.oas.models.media.Schema<?>
     */
    private Schema<?> buildSchemaFromJavaType(Class<?> type, java.lang.reflect.Type genericType) {
        if (type == null) return new StringSchema();

        if (type == String.class) {
            return new StringSchema();
        } else if (type == Integer.class || type == int.class) {
            return new IntegerSchema();
        } else if (type == Long.class || type == long.class) {
            return new IntegerSchema().format("int64");
        } else if (type == Double.class || type == Float.class || type == double.class || type == float.class) {
            return new NumberSchema();
        } else if (type == Boolean.class || type == boolean.class) {
            return new BooleanSchema();
        } else if (type.isArray()) {
            return new ArraySchema().items(buildSchemaFromJavaType(type.getComponentType(), null));
        } else if (Collection.class.isAssignableFrom(type)) {
            // 泛型处理（简单版）
            if (genericType instanceof ParameterizedType) {
                Type[] args = ((ParameterizedType) genericType).getActualTypeArguments();
                if (args.length > 0 && args[0] instanceof Class<?>) {
                    Schema<?> item = buildSchemaFromJavaType((Class<?>) args[0], null);
                    return new ArraySchema().items(item);
                }
            }
            return new ArraySchema().items(new StringSchema());
        } else if (type == Date.class || type == LocalDateTime.class || type == LocalDate.class) {
            return new StringSchema().format("date-time");
        } else {
            // 嵌套对象？可选：递归构建，或留空让用户自己定义
            return new ObjectSchema(); // 或者返回 new StringSchema() 避免复杂嵌套
        }
    }
}
