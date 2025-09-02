package com.kp.framework.modules.project.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.project.po.ProjectMenuPO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.caches.ehcache.LoggingEhcache;

/**
 * @Author lipeng
 * @Description 项目菜单关联表 Mapper 接口
 * @Date 2025-05-16
**/
@Mapper
@CacheNamespace(implementation = LoggingEhcache.class)
public interface ProjectMenuMapper extends ParentMapper<ProjectMenuPO> {

}
