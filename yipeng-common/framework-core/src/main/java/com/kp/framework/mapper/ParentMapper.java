package com.kp.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.Collection;

public interface ParentMapper<T> extends BaseMapper<T>, MPJBaseMapper<T> {

    /**
     * 批量插入。
     * @author lipeng
     * 2022/3/29
     * @param entityList 实体对象集合
     * @return java.lang.Integer
     */
    Integer kpInsertBatchSomeColumn(Collection<T> entityList);

    /**
     * 单条物理删除。
     * @author lipeng
     * 2024/6/3
     * @param id 主键
     * @return int
     */
    int kpDeleteAllById(Serializable id);

    /**
     * 批量物理删除。
     * @author lipeng
     * 2024/6/3
     * @param idList 主键集合
     * @return int
     */
    int kpDeleteAllByIds(@Param("coll") Collection<?> idList);

    /**
     * 统计数量 并且根据指定字段去重。
     * @author lipeng
     * 2024/8/30
     * @param distinctFile 去重字段
     * @param tableName 表名
     * @return int
     */
    @Select("SELECT COUNT(DISTINCT ${distinctFile}) FROM ${tableName} WHERE delete_flag = 0")
    int kpSelectCountDistinct(@Param("distinctFile") String distinctFile, @Param("tableName") String tableName);
}
