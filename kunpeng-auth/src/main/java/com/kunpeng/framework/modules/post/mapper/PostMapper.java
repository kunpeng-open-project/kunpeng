package com.kunpeng.framework.modules.post.mapper;

import com.kunpeng.framework.mapper.ParentMapper;
import com.kunpeng.framework.modules.post.po.PostPO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.caches.ehcache.LoggingEhcache;

/**
 * @Author lipeng
 * @Description 岗位信息表 Mapper 接口
 * @Date 2025-04-07
**/
@Mapper
@CacheNamespace(implementation = LoggingEhcache.class)
public interface PostMapper extends ParentMapper<PostPO> {

}
