package com.kp.framework.modules.user.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.user.po.UserRolePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色关联表 Mapper 接口。
 * @author lipeng
 * 2025-04-21
 */
@Mapper
public interface UserRoleMapper extends ParentMapper<UserRolePO> {

}
