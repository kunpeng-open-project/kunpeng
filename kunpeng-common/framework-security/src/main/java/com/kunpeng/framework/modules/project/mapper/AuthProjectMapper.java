package com.kunpeng.framework.modules.project.mapper;

import com.kunpeng.framework.common.parent.ParentSecurityMapper;
import com.kunpeng.framework.modules.project.po.AuthProjectPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 项目表 Mapper 接口
 * </p>
 *
 * @author lipeng
 * @since 2024-07-01
 */
@Mapper
public interface AuthProjectMapper extends ParentSecurityMapper<AuthProjectPO> {

}
