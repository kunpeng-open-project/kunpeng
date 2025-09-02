package com.kp.framework.modules.dict.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.dict.po.DictTypeProjectPO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.caches.ehcache.LoggingEhcache;

/**
 * @Author lipeng
 * @Description 字典类型项目关联表 Mapper 接口
 * @Date 2025-07-28 16:38:59
**/
@Mapper
@CacheNamespace(implementation = LoggingEhcache.class)
public interface DictTypeProjectMapper extends ParentMapper<DictTypeProjectPO> {

}
