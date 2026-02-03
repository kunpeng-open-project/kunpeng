package com.kp.framework.modules.dict.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.dict.po.DictTypePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典类型表 Mapper 接口。
 * @author lipeng
 * 2025-07-03
 */
@Mapper
public interface DictTypeMapper extends ParentMapper<DictTypePO> {

}
