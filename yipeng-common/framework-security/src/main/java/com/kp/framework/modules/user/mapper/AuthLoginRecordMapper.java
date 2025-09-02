package com.kp.framework.modules.user.mapper;


import com.kp.framework.common.parent.ParentSecurityMapper;
import com.kp.framework.modules.user.po.AuthLoginRecordPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户登录记录表 Mapper 接口
 * </p>
 *
 * @author lipeng
 * @since 2024-06-25
 */
@Mapper
public interface AuthLoginRecordMapper extends ParentSecurityMapper<AuthLoginRecordPO> {

}
