package com.kp.framework.annotation;

import com.kp.framework.constant.ObjectChangeLogOperateType;
import com.kp.framework.mapper.ParentMapper;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * @Author lipeng
 * @Description 记录对象改变内容
 * @Date 2024/2/22 17:13
 * @return
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Repeatable(KPObjectChangeLogListNote.class) // 使用@Repeatable指定容器注解
public @interface KPObjectChangeLogNote {

    /**
     * 查询数据库所调用的mapper类
     **/
    Class parentMapper() default ParentMapper.class;

    /**
     *  唯一标识，查询数据库的条件， 如果数据库字段和实体表字段名称不一样 写 字段名称|数据库字段名称 如 houseId,house_id 英文逗号
     **/
    String identification() default "id";

    /**
     * 操作类型 ObjectChangeLogOperateType
     **/
    String operateType() default ObjectChangeLogOperateType.UPDATE;



    /**
     * 业务类型， 使用者自己扩展
     **/
    String businessType() default "";

    /**
     * 保存日志的数据源
     **/
    String saveDS() default "yipeng_auth";

    /**
     * 保存日志的数据库表名
     **/
    String saveTableName() default "auth_object_change_log";


    /**
     * 不记录改变的字段 入 body,text
     **/
    String notRecordField() default "createDate,updateDate,deleteFlag";
}
