package com.kp.framework.entity.po;


import com.baomidou.mybatisplus.annotation.IdType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "GeneratorPO", description = "代码生成器参数")
public class GeneratorPO {

    @Schema(description = "作者", requiredMode = Schema.RequiredMode.REQUIRED, example = "lipeng")
    private String author;

    @Schema(description = "数据库", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"yipeng_auth", "yipeng_week_flow"})
    private String databaseName;

    @Schema(description = "包名 默认 com.kp.framework.modules", example = "com.kp.framework.modules")
    private String backageName = "com.kp.framework.modules";

    @Schema(description = "模块名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String modulesName;

    @Schema(description = "数据库表名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "表名称，多个表用英文逗号分隔")
    private List<String> tableName;

    @Schema(description = "主键策略", requiredMode = Schema.RequiredMode.REQUIRED, example = "ASSIGN_UUID", allowableValues = {"AUTO", "ASSIGN_UUID", "NONE", "INPUT", "ASSIGN_ID"})
    private IdType idType;

    @Schema(description = "表前缀")
    private List<String> tablePrefix;

    @Schema(description = "字前缀")
    private List<String> filesPrefix;

}
