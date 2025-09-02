package com.kp.framework.modules.user.mapper;


import com.kp.framework.common.parent.ParentSecurityMapper;
import com.kp.framework.modules.user.po.AuthUserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author lipeng
 * @since 2024-04-19
 */
@Mapper
public interface AuthUserMapper extends ParentSecurityMapper<AuthUserPO> {

}
