package com.kp.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.Collection;

public interface ParentMapper<T> extends BaseMapper<T>, MPJBaseMapper<T> {

    /**
     * @Author lipeng
     * @Description 批量插入
     * @Date 2022/3/29 15:45
     * @param entityList
     * @return java.lang.Integer
     **/
    Integer insertBatchSomeColumn(Collection<T> entityList);


    /**
     * @Author lipeng
     * @Description 单条物理删除
     * @Date 2024/6/3 11:32
     * @param id
     * @return int
     **/
    int deleteAllById(Serializable id);


    /**
     * @Author lipeng
     * @Description 批量物理删除
     * @Date 2024/6/3 11:35
     * @param idList
     * @return int
     **/
    int deleteAllByIds(@Param("coll") Collection<?> idList);


    /**
     * @Author lipeng
     * @Description 统计数量 并且根据指定字段去重
     * @Date 2024/8/30 15:22
     * @param distinctFile
     * @param tableName
     * @return int
     **/
    @Select("SELECT COUNT(DISTINCT ${distinctFile}) FROM ${tableName} WHERE delete_flag = 0")
    int selectCountDistinct(@Param("distinctFile") String distinctFile, @Param("tableName") String tableName);
}
