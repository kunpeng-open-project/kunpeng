package com.kunpeng.framework.modules.user.mapper;

import com.kunpeng.framework.mapper.ParentMapper;
import com.kunpeng.framework.modules.user.po.UserPostPO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.caches.ehcache.LoggingEhcache;

/**
 * @Author lipeng
 * @Description 用户岗位关联表 Mapper 接口
 * @Date 2025-04-21 15:51:03
**/
@Mapper
@CacheNamespace(implementation = LoggingEhcache.class)
public interface UserPostMapper extends ParentMapper<UserPostPO> {

}
