package com.kp.framework.modules.week.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.week.po.WeeklyPalanPO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.caches.ehcache.LoggingEhcache;

/**
 * @Author lipeng
 * @Description 周计划表 Mapper 接口
 * @Date 2025-09-21
**/
@Mapper
@CacheNamespace(implementation = LoggingEhcache.class)
public interface WeeklyPalanMapper extends ParentMapper<WeeklyPalanPO> {

}
