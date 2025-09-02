package com.kp.framework.modules.dept.mapper;


import com.kp.framework.common.parent.ParentSecurityMapper;
import com.kp.framework.modules.dept.po.AuthDeptPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author lipeng
 * @Description  部门信息Mapper接口
 * @Date 2024-09-11
 **/
@Mapper
public interface AuthDeptMapper extends ParentSecurityMapper<AuthDeptPO> {

}
