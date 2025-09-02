package com.kp.framework.modules.user.mapper;

import com.kp.framework.common.parent.ParentSecurityMapper;
import com.kp.framework.modules.user.po.AuthUserDeptPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户部门关联表 Mapper 接口
 * </p>
 *
 * @author lipeng
 * @since 2024-06-04
 */
@Mapper
public interface AuthUserDeptMapper extends ParentSecurityMapper<AuthUserDeptPO> {

}
