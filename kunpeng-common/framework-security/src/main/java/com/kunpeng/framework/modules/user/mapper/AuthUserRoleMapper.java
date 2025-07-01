package com.kunpeng.framework.modules.user.mapper;

import com.kunpeng.framework.common.parent.ParentSecurityMapper;
import com.kunpeng.framework.modules.user.po.AuthUserRolePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author lipeng
 * @since 2024-04-18
 */
@Mapper
public interface AuthUserRoleMapper extends ParentSecurityMapper<AuthUserRolePO> {

}
