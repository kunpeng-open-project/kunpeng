package com.kunpeng.framework.modules.project.mapper;

import com.kunpeng.framework.mapper.ParentMapper;
import com.kunpeng.framework.modules.project.po.ProjectPO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.caches.ehcache.LoggingEhcache;

/**
 * @Author lipeng
 * @Description 项目表 Mapper 接口
 * @Date 2025-04-07
**/
@Mapper
@CacheNamespace(implementation = LoggingEhcache.class)
public interface ProjectMapper extends ParentMapper<ProjectPO> {

}
