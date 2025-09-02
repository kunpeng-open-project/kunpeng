package ${package.Mapper};

import ${superMapperClassPackage};
import ${package.Entity}.${entity};
<#--import org.apache.ibatis.annotations.CacheNamespaceRef;-->
import org.apache.ibatis.annotations.CacheNamespace;
<#if mapperAnnotationClass??>
import ${mapperAnnotationClass.name};
</#if>
import org.mybatis.caches.ehcache.LoggingEhcache;

/**
 * @Author ${author}
 * @Description ${table.comment!} Mapper 接口
 * @Date ${date}
**/
<#if mapperAnnotationClass??>
@${mapperAnnotationClass.simpleName}
</#if>
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
<#--@CacheNamespaceRef(name = "${package.Mapper}.${table.mapperName}")-->
@CacheNamespace(implementation = LoggingEhcache.class)
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}
</#if>
