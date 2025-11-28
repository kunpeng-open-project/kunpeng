package com.kp.framework.utils.kptool;

import com.kp.framework.exception.KPServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/* *
 * @Author 李鹏
 * @Description 管理spring 中的bean
 * @Date 2020/5/18 22:37
 * @Param
 * @return
 **/
@Component
@Order(9999)
public final class KPServiceUtil implements ApplicationContextAware {

    private Logger log = LoggerFactory.getLogger(KPServiceUtil.class);

    @Autowired
    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (KPServiceUtil.applicationContext == null) {
            KPServiceUtil.applicationContext = applicationContext;
        }
        log.info("ApplicationContext获取成功！");
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    /**
     * @Author lipeng
     * @Description 通过name获取 Bean.
     * @param name
     * @return T
     **/
    public static <T> T getBean(String name) {
        try {
            return (T)getApplicationContext().getBean(name);
        }catch (Exception e){
            throw new KPServiceException("未找到对应的service");
        }

    }

    /**
     * @Author lipeng
     * @Description 通过class获取Bean.
     * @param clazz
     * @return T
     **/
    public static <T> T getBean(Class<T> clazz) {
        try {
            return (T) getApplicationContext().getBean(KPStringUtil.initialsLowerCase(clazz.getSimpleName()), clazz);
        }catch (Exception ex){
            return getApplicationContext().getBean(clazz);
        }
    }

    /**
     * @Author lipeng
     * @Description 通过name,以及Clazz返回指定的Bean
     * @param name
     * @param clazz
     * @return T
     **/
    public static <T> T getBean(String name, Class<T> clazz)  {
        return getApplicationContext().getBean(name, clazz);
    }
}
