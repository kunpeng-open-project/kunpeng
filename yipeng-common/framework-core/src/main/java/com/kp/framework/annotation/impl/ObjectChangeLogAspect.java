package com.kp.framework.annotation.impl;
import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.annotation.KPObjectChangeLogNote;
import com.kp.framework.annotation.impl.util.ObjectChangeUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @Author lipeng
 * @Description 单注解处理数据改变信息
 * @Date 2024/2/22 10:31
 * @return
 **/
@Aspect
@Component
@Slf4j
//@Scope("prototype")
@SuppressWarnings("all")
public class ObjectChangeLogAspect{

    @Autowired
    private ObjectChangeUtil objectChangeUtil;


    @Around("@annotation(operateLog)")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint, KPObjectChangeLogNote operateLog) throws Throwable {
        log.info("[记录执行前字段变化]");
        JSONObject beforeJson = objectChangeUtil.boBefore(proceedingJoinPoint, operateLog);
        // 执行目标方法
        Object result = proceedingJoinPoint.proceed();
        log.info("[记录执行后字段变化]");
        objectChangeUtil.boAround(proceedingJoinPoint, operateLog, beforeJson);
        return result;
    }

}
