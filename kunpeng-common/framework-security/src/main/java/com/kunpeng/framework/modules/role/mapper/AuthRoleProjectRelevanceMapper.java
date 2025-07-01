package com.kunpeng.framework.modules.role.mapper;

import com.kunpeng.framework.common.parent.ParentSecurityMapper;
import com.kunpeng.framework.modules.role.po.AuthRoleProjectRelevancePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色项目关联表 Mapper 接口
 * </p>
 *
 * @author lipeng
 * @since 2024-04-26
 */
@Mapper
public interface AuthRoleProjectRelevanceMapper extends ParentSecurityMapper<AuthRoleProjectRelevancePO> {

}
