package com.kp.framework.modules.dept.mapper.customer;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.user.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户信息表Mapper接口。
 * @author lipeng
 * 2025-04-21
 */
@Mapper
//@Primary
public interface DeptCustomerMapper extends ParentMapper<UserPO> {

    /**
     * 查询部门id以及所有子集id。
     * @author lipeng
     * 2025/4/21
     * @param deptId 部门id
     * @return java.util.List<java.lang.String>
     */
    List<String> queryDepeSubsetId(String deptId);

}
