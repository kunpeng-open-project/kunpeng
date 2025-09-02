package com.kp.framework.modules.dict.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.dict.po.DictDataPO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.caches.ehcache.LoggingEhcache;

/**
 * @Author lipeng
 * @Description 字典数据表 Mapper 接口
 * @Date 2025-07-03
**/
@Mapper
@CacheNamespace(implementation = LoggingEhcache.class)
public interface DictDataMapper extends ParentMapper<DictDataPO> {

}
