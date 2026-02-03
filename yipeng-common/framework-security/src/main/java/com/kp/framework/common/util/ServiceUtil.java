package com.kp.framework.common.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 管理spring 中的bean。
 * @author lipeng
 * 2020/5/18
 */
@Deprecated
@Component
@Order(Integer.MAX_VALUE)
public final class ServiceUtil implements ApplicationContextAware {

    @Autowired
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (ServiceUtil.applicationContext == null) {
            ServiceUtil.applicationContext = applicationContext;
        }
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static <T> T getBean(String name) {
        try {
            return (T) getApplicationContext().getBean(name);
        } catch (Exception e) {
            throw new IllegalArgumentException("未找到对应的service");
        }

    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        try {
            return (T) getApplicationContext().getBean(CommonUtil.initialsLowerCase(clazz.getSimpleName()), clazz);
        } catch (Exception ex) {
            return getApplicationContext().getBean(clazz);
        }
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
