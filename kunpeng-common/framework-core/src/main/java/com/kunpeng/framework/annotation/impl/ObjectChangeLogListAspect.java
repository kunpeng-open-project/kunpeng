package com.kunpeng.framework.annotation.impl;

import com.alibaba.fastjson2.JSONObject;
import com.kunpeng.framework.annotation.KPObjectChangeLogListNote;
import com.kunpeng.framework.annotation.KPObjectChangeLogNote;
import com.kunpeng.framework.annotation.impl.util.ObjectChangeUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author lipeng
 * @Description 多注解处理数据改变信息
 * @Date 2024/3/5 10:31
 * @return
 **/
@Aspect
@Component
@Slf4j
@SuppressWarnings("all")
public class ObjectChangeLogListAspect {

    @Autowired
    private ObjectChangeLogAspect objectChangeLogAspect;

    @Autowired
    private ObjectChangeUtil objectChangeUtil;


    @Around("@annotation(kpObjectChangeLogListNote)")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint, KPObjectChangeLogListNote kpObjectChangeLogListNote) throws Throwable {
        log.info("[批量记录执行前字段变化]");
        Map<String, JSONObject> map = new HashMap<>();
        for (KPObjectChangeLogNote operateLog : kpObjectChangeLogListNote.value()) {
            JSONObject beforeJson = objectChangeUtil.boBefore(proceedingJoinPoint, operateLog);
            map.put(operateLog.businessType(), beforeJson);
        }

        Object result = proceedingJoinPoint.proceed();

        log.info("[批量记录执行后字段变化]");
        for (KPObjectChangeLogNote operateLog : kpObjectChangeLogListNote.value()) {
            objectChangeUtil.boAround(proceedingJoinPoint, operateLog, map.get(operateLog.businessType()));
        }

        return result;
    }
}
