package com.kunpeng.framework.common.parent;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;

import java.util.Collection;

public interface ParentSecurityMapper<T> extends BaseMapper<T>, MPJBaseMapper<T> {

    /**
     * @Author lipeng
     * @Description 批量插入
     * @Date 2022/3/29 15:45
     * @param entityList
     * @return java.lang.Integer
     **/
    Integer insertBatchSomeColumn(Collection<T> entityList);
}
