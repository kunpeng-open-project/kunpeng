package com.kp.framework.modules.dept.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.dept.po.DeptPO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.caches.ehcache.LoggingEhcache;

/**
 * @Author lipeng
 * @Description 部门信息表 Mapper 接口
 * @Date 2025-04-08
**/
@Mapper
@CacheNamespace(implementation = LoggingEhcache.class)
public interface DeptMapper extends ParentMapper<DeptPO> {

}
