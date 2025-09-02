package com.kp.framework.modules.dict.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.dict.po.DictTypePO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.caches.ehcache.LoggingEhcache;

/**
 * @Author lipeng
 * @Description 字典类型表 Mapper 接口
 * @Date 2025-07-03
**/
@Mapper
@CacheNamespace(implementation = LoggingEhcache.class)
public interface DictTypeMapper extends ParentMapper<DictTypePO> {

}
