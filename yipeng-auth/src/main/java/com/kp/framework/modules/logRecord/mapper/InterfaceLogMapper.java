package com.kp.framework.modules.logRecord.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.logRecord.po.InterfaceLogPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统内部接口调用记录 Mapper 接口。
 * @author lipeng
 * 2025-05-28
 */
@Mapper
public interface InterfaceLogMapper extends ParentMapper<InterfaceLogPO> {

}
