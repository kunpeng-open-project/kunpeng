package com.kp.framework.modules.monthly.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.monthly.po.MonthlyReportUserPO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.caches.ehcache.LoggingEhcache;

/**
 * @Author lipeng
 * @Description 月度计划责任人信息表 Mapper 接口
 * @Date 2025-07-25 15:27:59
**/
@Mapper
@CacheNamespace(implementation = LoggingEhcache.class)
public interface MonthlyReportUserMapper extends ParentMapper<MonthlyReportUserPO> {

}
