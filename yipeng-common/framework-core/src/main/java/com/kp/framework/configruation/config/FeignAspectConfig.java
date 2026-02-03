package com.kp.framework.configruation.config;


import com.kp.framework.utils.kptool.KPJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * 记录内部系统间调用日志。
 * @author lipeng
 * 2022/3/15
 */
@Aspect
@Component
@Slf4j
public class FeignAspectConfig {
    private ThreadLocal<Long> satrtDate = new ThreadLocal<>();
    private ThreadLocal<String> functionNmae = new ThreadLocal<>();

//    @Pointcut("execution(* com.kp.framework.microservices.*.interfaces.*.*(..))")
    @Pointcut("within(@org.springframework.cloud.openfeign.FeignClient *)")
    public void log(){}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        satrtDate.set(System.currentTimeMillis());

        String functionName = "";
        try {
            if (joinPoint.toString().startsWith("execution")){
                functionName = joinPoint.toString().substring(16, joinPoint.toString().length()-1);
            }else{
                functionName = joinPoint.getSourceLocation().getWithinType().getName();
            }
        }catch (Exception e){
            functionName = joinPoint.getSourceLocation().getWithinType().getName();
        }

        functionNmae.set(functionName);
        log.info("【Feign请求方法】 {}", functionNmae.get());
        try {
            log.info("【Feign请求参数】 {}", KPJsonUtil.toJsonString(joinPoint.getArgs()[0].toString()));
        }catch (Exception ex){}

    }

    @AfterReturning(returning = "ret", pointcut = "log()")
    public void doAfterReturning(JoinPoint joinPoint,  Object ret){
        log.info("【Feign返回报文】 {} ", KPJsonUtil.toJsonString(ret));
        log.info("【Feign耗时】  {} 毫秒, 方法名{}", System.currentTimeMillis()-satrtDate.get(), functionNmae.get());
    }



    @AfterThrowing( pointcut = "log()")
    public void doAfterThrowing(JoinPoint joinPoint){
        log.info("【Feign调用方法异常】  方法名{}", functionNmae.get());
    }
}
