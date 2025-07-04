package com.kunpeng.framework.modules.logRecord.mapper;

import com.kunpeng.framework.mapper.ParentMapper;
import com.kunpeng.framework.modules.logRecord.po.HttpLogHistoryPO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.caches.ehcache.LoggingEhcache;

/**
 * @Author lipeng
 * @Description 系统外部接口调用记录-历史表 Mapper 接口
 * @Date 2025-05-28
**/
@Mapper
@CacheNamespace(implementation = LoggingEhcache.class)
public interface HttpLogHistoryMapper extends ParentMapper<HttpLogHistoryPO> {

}
