package com.kp.framework.modules.user.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.user.po.UserDeptPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户部门关联表 Mapper 接口。
 * @author lipeng
 * 2025-04-08
 */
@Mapper
public interface UserDeptMapper extends ParentMapper<UserDeptPO> {

}
