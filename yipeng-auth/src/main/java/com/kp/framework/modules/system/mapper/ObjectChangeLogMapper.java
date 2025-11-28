package com.kp.framework.modules.system.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.system.po.ObjectChangeLogPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author lipeng
 * @Description  Mapper 接口
 * @Date 2025-11-10 16:34:05
**/
@Mapper
public interface ObjectChangeLogMapper extends ParentMapper<ObjectChangeLogPO> {

}
