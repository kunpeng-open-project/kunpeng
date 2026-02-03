package com.kp.framework.modules.logRecord.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.logRecord.po.InterfaceLogHistoryPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统内部接口调用记录-历史表 Mapper 接口。
 * @author lipeng
 * 2025-05-28
 */
@Mapper
public interface InterfaceLogHistoryMapper extends ParentMapper<InterfaceLogHistoryPO> {

}
