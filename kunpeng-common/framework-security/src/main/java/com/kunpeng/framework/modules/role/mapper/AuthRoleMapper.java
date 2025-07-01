package com.kunpeng.framework.modules.role.mapper;

import com.kunpeng.framework.common.parent.ParentSecurityMapper;
import com.kunpeng.framework.modules.role.po.AuthRolePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author lipeng
 * @since 2024-04-19
 */
@Mapper
public interface AuthRoleMapper extends ParentSecurityMapper<AuthRolePO> {

}
