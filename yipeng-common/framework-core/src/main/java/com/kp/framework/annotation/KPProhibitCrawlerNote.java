package com.kp.framework.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * @Author lipeng
 * @Description 禁止爬虫
 * @Date 2023/12/20 8:59
 * @return
 **/
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KPProhibitCrawlerNote {

    //minute 分 最大访问 minuteCount  次 防止接口频繁调用
    // 如果超过 次数 12小时内禁止 访问 如果禁止访问次数超过3次（第三次拉入黑名单 设置3 表示 最多2次机会） 永久拉入黑名单
    int minute() default 5;

    int minuteCount() default 50;

    // 默认禁止访问时间
    int forbidHouse() default 12;

    int blacklist() default 3;
}
