package com.kunpeng.framework.modules.dept.mapper.customer;

import com.kunpeng.framework.mapper.ParentMapper;
import com.kunpeng.framework.modules.user.po.UserPO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.caches.ehcache.LoggingEhcache;

import java.util.List;

/**
 * @Author lipeng
 * @Description 用户信息表 Mapper 接口
 * @Date 2025-04-21
**/
@Mapper
@CacheNamespace(implementation = LoggingEhcache.class)
//@Primary
public interface DeptCustomerMapper extends ParentMapper<UserPO> {


    /**
     * @Author lipeng
     * @Description 查询部门id以及所有子集id
     * @Date 2025/4/21
     * @param deptId
     * @return java.util.List<java.lang.String>
     **/
    List<String> queryDepeSubsetId(String deptId);

}
