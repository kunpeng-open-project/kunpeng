package com.kp.framework.modules.dict.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.dict.po.DictTypeProjectPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典类型项目关联表 Mapper 接口。
 * @author lipeng
 * 2025-07-28
 */
@Mapper
public interface DictTypeProjectMapper extends ParentMapper<DictTypeProjectPO> {

}
