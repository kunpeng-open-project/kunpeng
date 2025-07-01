package com.kunpeng.framework.modules.role.mapper;

import com.kunpeng.framework.common.parent.ParentSecurityMapper;
import com.kunpeng.framework.modules.role.po.AuthRolePermissionPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色权限关联表 Mapper 接口
 * </p>
 *
 * @author lipeng
 * @since 2024-04-19
 */
@Mapper
public interface AuthRolePermissionMapper extends ParentSecurityMapper<AuthRolePermissionPO> {

}
