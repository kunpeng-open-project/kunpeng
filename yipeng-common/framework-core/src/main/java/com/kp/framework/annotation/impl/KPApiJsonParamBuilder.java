package com.kp.framework.annotation.impl;

import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.entity.bo.KPApiJsonParamParamBO;
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * KPApiJsonParam 注解实现方式。
 * @author lipeng
 * 2025/12/30
 */
@UtilityClass
public class KPApiJsonParamBuilder {

    //默认类名
    private final static String DEFAULT_CLASS_NAME = "KPJsonParamBO";
    //序号 防止重名
    private static Integer i = 1;

    public Operation builder(Operation operation, HandlerMethod handlerMethod) {
        KPApiJsonParam kpApiJsonParam = handlerMethod.getMethodAnnotation(KPApiJsonParam.class);
        if (kpApiJsonParam == null || kpApiJsonParam.value().length == 0) {
            return operation;
        }

        // 生成唯一 schema 名称（必须全局唯一）
        String schemaName = DEFAULT_CLASS_NAME + i++;

        // 构建 schema 内容
        ObjectSchema schema = new ObjectSchema();
        Map<String, Schema> properties = new LinkedHashMap<>();
        List<String> required = new ArrayList<>();

        for (KPJsonField kpJsonField : kpApiJsonParam.value()) {
            Schema<?> fieldSchema = buildSchemaFromType(kpJsonField.dataType());
            if (fieldSchema == null) continue;

            fieldSchema.description(kpJsonField.description());
            if (KPStringUtil.isNotEmpty(kpJsonField.example()))
                fieldSchema.example(kpJsonField.example());
            if (kpJsonField.required())
                required.add(kpJsonField.name());

            properties.put(kpJsonField.name(), fieldSchema);
        }

        schema.setProperties(properties);
        if (!required.isEmpty())
            schema.setRequired(required);

        // 注册到全局 registry
        KPApiJsonParamParamBO.SCHEMAS.put(schemaName, schema);

        // 使用 $ref 引用，而不是 inline schema
        Schema<?> refSchema = new Schema<>();
        //使用这个临时的实体类
        refSchema.set$ref("#/components/schemas/" + schemaName);

        RequestBody body = new RequestBody()
                .content(new Content()
                        .addMediaType("application/json", new MediaType().schema(refSchema)))
                .description("JSON请求参数")
                .required(true);

        operation.setRequestBody(body);
        // 清除 query 参数
        operation.setParameters(null);
        return operation;
    }

    /**
     * 根据类型构建 schema。
     * @author lipeng
     * 2025/12/30
     * @param dataType 数据类型
     * @return io.swagger.v3.oas.models.media.Schema<?>
     */
    private Schema<?> buildSchemaFromType(String dataType) {
        if (KPStringUtil.isEmpty(dataType)) dataType = "string";

        dataType = dataType.toLowerCase().trim();

        return switch (dataType) {
            case "string" -> new StringSchema();
            case "integer","int" -> new IntegerSchema();
            case "long", "int64" -> new IntegerSchema().format("int64");
            case "number", "double", "float" -> new NumberSchema();
            case "boolean" -> new BooleanSchema();
            case "array", "list" -> new ArraySchema().items(new StringSchema());
            default -> {
                if (dataType.startsWith("array<") && dataType.endsWith(">")) {
                    String inner = dataType.substring(6, dataType.length() - 1).trim();
                    Schema<?> item = buildSchemaFromType(inner);
                    yield new ArraySchema().items(item != null ? item : new StringSchema());
                } else if (dataType.startsWith("list<") && dataType.endsWith(">")) {
                    String inner = dataType.substring(5, dataType.length() - 1).trim();
                    Schema<?> item = buildSchemaFromType(inner);
                    yield new ArraySchema().items(item != null ? item : new StringSchema());
                } else {
                    // 其他类型默认返回 StringSchema
                    yield new StringSchema();
                }
            }
        };
    }
}
