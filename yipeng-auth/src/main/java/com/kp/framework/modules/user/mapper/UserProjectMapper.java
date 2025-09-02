package com.kp.framework.modules.user.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.user.po.UserProjectPO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.caches.ehcache.LoggingEhcache;

/**
 * @Author lipeng
 * @Description 用户项目关联表 Mapper 接口
 * @Date 2025-04-21
**/
@Mapper
@CacheNamespace(implementation = LoggingEhcache.class)
public interface UserProjectMapper extends ParentMapper<UserProjectPO> {

}
