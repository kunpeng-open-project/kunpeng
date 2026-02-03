package com.kp.framework.modules.dept.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.dept.po.DeptPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 部门信息表 Mapper 接口。
 * @author lipeng
 * 2025-04-08
 */
@Mapper
public interface DeptMapper extends ParentMapper<DeptPO> {

}
