package com.kunpeng.framework.entity.po;


import com.baomidou.mybatisplus.annotation.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="GeneratorPO", description="GeneratorPO")
public class GeneratorPO {

    @ApiModelProperty(value = "作者", required = true, example = "lipeng")
    private String author;

    @ApiModelProperty(value = "数据库", required = true, allowableValues = "kunpeng_auth", allowEmptyValue = true)
    private String databaseName;

    @ApiModelProperty(value = "包名 默认 com.kunpeng.framework.modules", example = "com.kunpeng.framework.modules")
    private String backageName = "com.kunpeng.framework.modules";

    @ApiModelProperty(value = "模块名", required = true)
    private String modulesName;

    @ApiModelProperty(value = "数据库表名称", required = true, example = "[\"auth_user\", \"auth_menu\"]" )
    private List<String> tableName;

    @ApiModelProperty(value = "主键策略", required = true, example = "ASSIGN_UUID")
    private IdType idType;

    @ApiModelProperty(value = "表前缀")
    private List<String> tablePrefix;

    @ApiModelProperty(value = "字前缀")
    private List<String> filesPrefix;

}
