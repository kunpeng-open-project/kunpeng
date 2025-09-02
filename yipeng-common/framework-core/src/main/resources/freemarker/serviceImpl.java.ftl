<#--处理@Api 命名规范-->
<#assign commentMessage = table.comment?replace("表", "")>
<#--用户表参与实体命名-->
<#assign EntityParam = entity?substring(0, entity?length - 2)>
<#assign entityParam = EntityParam?uncap_first>
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyName = field.propertyName>
        <#assign keyNameByGet = "get" + keyName?substring(0, 1)?upper_case + keyName?substring(1) + "()">
        <#assign keyValue = field.name>
        <#assign keyComment = field.comment>
    </#if>
</#list>
<#assign entityLowerCase = entity?uncap_first>
package ${package.ServiceImpl};

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import ${superServiceImplClassPackage};
import com.github.pagehelper.PageHelper;
import ${package.Mapper}.${table.mapperName};
import ${package.Entity}.${entity};
import ${package.Entity}.param.${EntityParam}EditParamPO;
import ${package.Entity}.param.${EntityParam}ListParamPO;
import com.kp.framework.constant.ReturnFinishedMessageConstant;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPVerifyUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ${author}
 * @Description ${table.comment!} 服务实现类
 * @Date ${date}
**/
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>()<#if table.serviceInterface>, ${table.serviceName}</#if> {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}><#if table.serviceInterface></#if> {


    /**
     * @Author ${author}
     * @Description 查询${commentMessage}列表
     * @Date ${date}
     * @param ${entityParam}ListParamPO
     * @return java.util.List<${entity}>
    **/
    public List<${entity}> queryPageList(${EntityParam}ListParamPO ${entityParam}ListParamPO){
        //搜索条件
        LambdaQueryWrapper<${entity}> queryWrapper = Wrappers.lambdaQuery(${entity}.class)
    <#assign isStop = false>
    <#assign totalFields = table.fields?size>
    <#list table.fields as field>
        <#assign currentIndex = field_index + 1>
        <#if field.propertyType == "String">
                .like(KPStringUtil.isNotEmpty(${entityParam}ListParamPO.get${field.propertyName?cap_first}()), ${entity}::get${field.propertyName?cap_first}, ${entityParam}ListParamPO.get${field.propertyName?cap_first}())<#if currentIndex == totalFields>;<#assign isStop = true></#if>
        </#if>
        <#if field.propertyType == "Integer">
                .eq(KPStringUtil.isNotEmpty(${entityParam}ListParamPO.get${field.propertyName?cap_first}()), ${entity}::get${field.propertyName?cap_first}, ${entityParam}ListParamPO.get${field.propertyName?cap_first}())<#if currentIndex == totalFields>;<#assign isStop = true></#if>
        </#if>
    </#list>
    <#if isStop==false>;</#if>
        //分页和排序
        PageHelper.startPage(${entityParam}ListParamPO.getPageNum(), ${entityParam}ListParamPO.getPageSize(), ${entityParam}ListParamPO.getOrderBy(${entity}.class));
        return this.baseMapper.selectList(queryWrapper);
    }


    /**
     * @Author ${author}
     * @Description 根据${keyComment}查询详情
     * @Date ${date}
     * @param parameter
     * @return ${entity}
    **/
    public ${entity} queryDetailsById(JSONObject parameter){
        ${entity} ${entityLowerCase} = KPJsonUtil.toJavaObject(parameter, ${entity}.class);
        KPVerifyUtil.notNull(${entityLowerCase}.${keyNameByGet}, "请输入${keyName}");
        return this.baseMapper.selectById(${entityLowerCase}.${keyNameByGet});
    }


    /**
     * @Author ${author}
     * @Description 新增${commentMessage}
     * @Date ${date}
     * @param ${entityParam}EditParamPO
     * @return void
    **/
    public void save${EntityParam}(${EntityParam}EditParamPO ${entityParam}EditParamPO){
        ${entity} ${entityLowerCase} = KPJsonUtil.toJavaObjectNotEmpty(${entityParam}EditParamPO, ${entity}.class);

        if (this.baseMapper.insert(${entityLowerCase}) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }


    /**
     * @Author ${author}
     * @Description 修改${commentMessage}
     * @Date ${date}
     * @param ${entityParam}EditParamPO
     * @return void
    **/
    public void update${EntityParam}(${EntityParam}EditParamPO ${entityParam}EditParamPO){
        ${entity} ${entityLowerCase} = KPJsonUtil.toJavaObjectNotEmpty(${entityParam}EditParamPO, ${entity}.class);

        if (this.baseMapper.updateById(${entityLowerCase}) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }


    /**
     * @Author ${author}
     * @Description 批量删除${commentMessage}
     * @Date ${date}
     * @param ids
     * @return String
    **/
    public String batchRemove(List<String> ids){
        if(KPStringUtil.isEmpty(ids)) throw new KPServiceException("请选择要删除的内容！");

        Integer row = this.baseMapper.deleteBatchIds(ids);
        if(row == 0) throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        return KPStringUtil.format("删除成功{0}条数据", row);
    }
}
</#if>
