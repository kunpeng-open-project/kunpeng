<#--权限字符 命名规范-->
<#assign controllerPreAuthorize = table.name?replace("_", ":")>
<#--用户表参与实体命名-->
<#assign EntityParam = entity?substring(0, entity?length - 2)>
<#assign entityParam = EntityParam?uncap_first>
<#assign serviceImplName = table.serviceImplName?uncap_first>
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyName = field.propertyName>
        <#assign keyNameByGet = "get" + keyName?substring(0, 1)?upper_case + keyName?substring(1) + "()">
        <#assign keyValue = field.name>
        <#assign keyComment = field.comment>
    </#if>
</#list>
<#assign serviceName = table.serviceImplName?uncap_first>
package ${package.Controller};

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kunpeng.framework.annotation.KPApiJsonlParam;
import com.kunpeng.framework.annotation.KPApiJsonlParamMode;
import com.kunpeng.framework.annotation.verify.KPVerifyNote;
import com.kunpeng.framework.entity.bo.KPResult;
import ${package.Entity}.${entity};
import ${package.Entity}.param.${EntityParam}EditParamPO;
import ${package.Entity}.param.${EntityParam}ListParamPO;
import ${package.ServiceImpl}.${table.serviceImplName};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

import java.util.List;

<#--处理@Api 命名规范-->
<#assign commentMessage = table.comment?replace("表", "")>
<#--处理requestMapping 命名规范-->
<#if table.name?contains("_")>
    <#assign requestMapping = "/" + table.name?replace("_", "/")>
<#elseif table.name?contains("-")>
    <#assign requestMapping = "/" + table.name?replace("-", "/")>
<#elseif table.name?matches(".*[A-Z].*")>
    <#assign requestMapping = "/" + table.name?replace("([A-Z])", "/$1")?lower_case>
<#else>
    <#assign requestMapping = "/" + table.name>
</#if>
/**
* @Author ${author}
* @Description  ${table.comment!}相关接口
* @Date ${date}
**/
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
<#--@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if requestMappingHyphenStyle>${requestMappingHyphen}<#else>${table.entityPath}</#if>")-->
@RequestMapping("${requestMapping}")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
@Api(tags = "${commentMessage}相关接口", value = "${commentMessage}相关接口")
@ApiSupport(order = 1)
public class ${table.controllerName} {
</#if>

    @Autowired
    private ${table.serviceImplName} ${serviceImplName};


    @PreAuthorize("hasPermission('${requestMapping}/page/list', '${controllerPreAuthorize}:page:list')")
    @ApiOperation(value = "查询${commentMessage}分页列表", notes = "权限 ${controllerPreAuthorize}:page:list")
    @PostMapping("/page/list")
    @KPVerifyNote
    public KPResult<${entity}> queryPageList(@RequestBody ${EntityParam}ListParamPO ${entityParam}ListParamPO){
        return KPResult.list(${serviceImplName}.queryPageList(${entityParam}ListParamPO));
    }


    @PreAuthorize("hasPermission('${requestMapping}/details','${controllerPreAuthorize}:details')")
    @ApiOperation(value = "根据${keyComment}查询详情", notes="权限 ${controllerPreAuthorize}:details")
    @PostMapping("/details")
    @KPApiJsonlParam({
        @ApiModelProperty(name = "${keyName}", value = "${keyComment}", required = true)
    })
    public KPResult<${entity}> queryDetailsById(@RequestBody JSONObject parameter){
        return KPResult.success(${serviceName}.queryDetailsById(parameter));
    }


    @PreAuthorize("hasPermission('${requestMapping}/save','${controllerPreAuthorize}:save')")
    @ApiOperation(value = "新增${commentMessage}", notes="权限 ${controllerPreAuthorize}:save")
    @PostMapping("/save")
    @KPVerifyNote
    @KPApiJsonlParamMode(component = ${EntityParam}EditParamPO.class, ignores = "${keyName}")
    public KPResult<${entity}> save(@RequestBody ${EntityParam}EditParamPO ${entityParam}EditParamPO){
        ${serviceName}.save${EntityParam}(${entityParam}EditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('${requestMapping}/update','${controllerPreAuthorize}:update')")
    @ApiOperation(value = "修改${commentMessage}", notes="权限 ${controllerPreAuthorize}:update")
    @PostMapping("/update")
    @KPVerifyNote
    public KPResult<${entity}> update(@RequestBody ${EntityParam}EditParamPO ${entityParam}EditParamPO){
        ${serviceName}.update${EntityParam}(${entityParam}EditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('${requestMapping}/batch/remove','${controllerPreAuthorize}:batch:remove')")
    @ApiOperation(value = "批量删除${commentMessage}", notes="权限 ${controllerPreAuthorize}:batch:remove")
    @PostMapping("/batch/remove")
    @KPApiJsonlParam({
        @ApiModelProperty(name = "ids", value = "${keyComment}", required = true, dataType = "list")
    })
    public KPResult batchRemove(@RequestBody List<String> ids){
        return KPResult.success(${serviceName}.batchRemove(ids));
    }
}
</#if>
