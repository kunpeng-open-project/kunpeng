package com.kunpeng.framework.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface KPDataPermissions {

    /**
     * 默认的用户id字段
     **/
    String userFileName() default "create_user_id";

    /**
     *  默认的部门id字段
     **/
    String deptFileName() default "dept_id";


    /**
     *  要排除的表名, 多个表用逗号分隔
     **/
    String excludeTableName() default "";

}
