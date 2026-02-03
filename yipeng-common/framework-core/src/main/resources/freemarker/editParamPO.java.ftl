package ${package.Entity}.param;

<#-- 定义需要排除的包列表 -->
<#assign excludePackages = [
"com.kp.framework.entity.bo.ParentBO", "java.io.Serializable","com.baomidou.mybatisplus.annotation.TableName"
]>
<#list table.importPackages as pkg>
    <#if !excludePackages?seq_contains(pkg)>
import ${pkg};
    </#if>
</#list>
import com.kp.framework.annotation.verify.KPMaxLength;
import com.kp.framework.annotation.verify.KPNotNull;
<#if springdoc>
import io.swagger.v3.oas.annotations.media.Schema;
<#elseif swagger>
import io.swagger.v3.oas.annotations.media.Schema;
</#if>
import java.io.Serial;
import java.io.Serializable;
<#if entityLombokModel>
import lombok.Data;
import lombok.EqualsAndHashCode;
    <#if chainModel>
import lombok.experimental.Accessors;
    </#if>
</#if>
<#assign EntityParam = entity?substring(0, entity?length - 2)>
<#assign commentMessage = table.comment?replace("表", "")>

/**
 * ${commentMessage!}编辑入参
 * @author ${author}
 * ${date}
 */
<#if entityLombokModel>
@Data
@EqualsAndHashCode(callSuper = false)
    <#if chainModel>
@Accessors(chain = true)
    </#if>
</#if>
<#if springdoc>
@Schema(name = "${EntityParam}", description = "${table.comment!}")
<#elseif swagger>
@Schema(name = "${EntityParam}EditParamPO", description = "${table.comment!}编辑入参")
</#if>
public class ${EntityParam}EditParamPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.comment!?length gt 0>
        <#if springdoc>
            @Schema(description = "${field.comment}")
        <#elseif swagger>
            <#assign exampleTitle = field.comment>
            <#if field.propertyType == "String">
                <#assign exampleTitle = field.comment>
            <#elseif field.propertyType == "LocalDateTime">
                <#assign exampleTitle = date + " 00:00:00">
            <#elseif field.propertyType == "LocalDate">
                <#assign exampleTitle = date>
            <#elseif field.propertyType == "Integer">
                <#assign exampleTitle = 0>
            <#elseif field.propertyType == "int">
                <#assign exampleTitle = 0>
            </#if>
    @Schema(description = "${field.comment}", example = "${exampleTitle}", requiredMode = Schema.RequiredMode.REQUIRED)
        <#else>
            /**
            * ${field.comment}
            */
        </#if>
    </#if>
    <#if field.keyFlag>
    <#-- 主键 -->
        <#if field.keyIdentityFlag>
    @TableId(value = "${field.annotationColumnName}", type = IdType.AUTO)
        <#elseif idType??>
    @TableId(value = "${field.annotationColumnName}", type = IdType.${idType})
        <#elseif field.convert>
    @TableId("${field.annotationColumnName}")
        </#if>
    <#-- 普通字段 -->
    <#elseif field.fill??>
    <#-- -----   存在字段填充设置   ----->
        <#if field.convert>
    @TableField(value = "${field.annotationColumnName}", fill = FieldFill.${field.fill})
        <#else>
    @TableField(fill = FieldFill.${field.fill})
        </#if>
    <#elseif field.convert>
    @TableField("${field.annotationColumnName}")
    </#if>
    @KPNotNull(errMeg = "请输入${field.comment}")
    <#if field.propertyType == "String">
    @KPMaxLength(max = ${(field.metaInfo.length?string)?replace(",", "")}, errMeg = "${field.comment}不能超过${(field.metaInfo.length?string)?replace(",", "")}个字符")
    </#if>
<#-- 乐观锁注解 -->
    <#if field.versionField>
        @Version
    </#if>
<#-- 逻辑删除注解 -->
    <#if field.logicDeleteField>
        @TableLogic
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->
<#if !entityLombokModel>
    <#list table.fields as field>
        <#if field.propertyType == "boolean">
            <#assign getprefix="is"/>
        <#else>
            <#assign getprefix="get"/>
        </#if>

        public ${field.propertyType} ${getprefix}${field.capitalName}() {
        return ${field.propertyName};
        }

        <#if chainModel>
            public ${entity} set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
        <#else>
            public void set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
        </#if>
        this.${field.propertyName} = ${field.propertyName};
        <#if chainModel>
            return this;
        </#if>
        }
    </#list>
</#if>
<#if entityColumnConstant>
    <#list table.fields as field>

        public static final String ${field.name?upper_case} = "${field.name}";
    </#list>
</#if>
<#if activeRecord>

    @Override
    public Serializable pkVal() {
    <#if keyPropertyName??>
        return this.${keyPropertyName};
    <#else>
        return null;
    </#if>
    }
</#if>
<#if !entityLombokModel>

    @Override
    public String toString() {
    return "${entity}{" +
    <#list table.fields as field>
        <#if field_index==0>
            "${field.propertyName} = " + ${field.propertyName} +
        <#else>
            ", ${field.propertyName} = " + ${field.propertyName} +
        </#if>
    </#list>
    "}";
    }
</#if>
}
