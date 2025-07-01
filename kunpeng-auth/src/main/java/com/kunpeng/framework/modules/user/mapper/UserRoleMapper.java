package com.kunpeng.framework.modules.user.mapper;

import com.kunpeng.framework.mapper.ParentMapper;
import com.kunpeng.framework.modules.user.po.UserRolePO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.caches.ehcache.LoggingEhcache;

/**
 * @Author lipeng
 * @Description 用户角色关联表 Mapper 接口
 * @Date 2025-04-21 16:17:37
**/
@Mapper
@CacheNamespace(implementation = LoggingEhcache.class)
public interface UserRoleMapper extends ParentMapper<UserRolePO> {

}
