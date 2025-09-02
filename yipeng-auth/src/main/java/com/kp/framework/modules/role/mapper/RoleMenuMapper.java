package com.kp.framework.modules.role.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.role.po.RoleMenuPO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.caches.ehcache.LoggingEhcache;

/**
 * @Author lipeng
 * @Description 角色菜单关联表 Mapper 接口
 * @Date 2025-04-20
**/
@Mapper
@CacheNamespace(implementation = LoggingEhcache.class)
public interface RoleMenuMapper extends ParentMapper<RoleMenuPO> {

}
