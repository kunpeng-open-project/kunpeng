package com.kp.framework.modules.role.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.role.po.RolePermissionPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 色权限关联表 Mapper 接口。
 * @author lipeng
 * 2025-05-13
 */
@Mapper
public interface RolePermissionMapper extends ParentMapper<RolePermissionPO> {

}
