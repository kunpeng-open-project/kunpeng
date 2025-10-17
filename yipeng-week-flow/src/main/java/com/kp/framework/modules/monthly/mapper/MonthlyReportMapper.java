package com.kp.framework.modules.monthly.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.monthly.po.MonthlyReportPO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.caches.ehcache.LoggingEhcache;

/**
 * @Author lipeng
 * @Description 月度计划表 Mapper 接口
 * @Date 2025-09-15
**/
@Mapper
@CacheNamespace(implementation = LoggingEhcache.class)
public interface MonthlyReportMapper extends ParentMapper<MonthlyReportPO> {

}
