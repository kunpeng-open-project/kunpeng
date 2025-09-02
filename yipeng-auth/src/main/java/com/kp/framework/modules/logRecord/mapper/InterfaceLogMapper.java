package com.kp.framework.modules.logRecord.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.logRecord.po.InterfaceLogPO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.caches.ehcache.LoggingEhcache;

/**
 * @Author lipeng
 * @Description 系统内部接口调用记录 Mapper 接口
 * @Date 2025-05-28
**/
@Mapper
@CacheNamespace(implementation = LoggingEhcache.class)
public interface InterfaceLogMapper extends ParentMapper<InterfaceLogPO> {

}
