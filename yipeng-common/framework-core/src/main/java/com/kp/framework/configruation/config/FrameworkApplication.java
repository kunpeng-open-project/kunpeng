package com.kp.framework.configruation.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.kp.framework.configruation.properties.KPFrameworkAsyncProperties;
import com.kp.framework.configruation.properties.KPFrameworkTaskExecutorProperties;
import com.kp.framework.configruation.properties.KPFrameworkTaskProperties;
import com.kp.framework.utils.kptool.KPIconUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author 李鹏
 * @Description
 * @Date $ $
 * @return $
 **/
@Configuration
@Component
public class FrameworkApplication implements AsyncConfigurer {

    @Autowired
    private KPFrameworkTaskExecutorProperties kpFrameworkTaskExecutorProperties;
    @Autowired
    private KPFrameworkTaskProperties kpFrameworkTaskProperties;
    @Autowired
    private KPFrameworkAsyncProperties kpFrameworkAsyncProperties;
    private Logger logger = LoggerFactory.getLogger(FrameworkApplication.class);

    @PostConstruct
    public void init() {
        KPIconUtil.println(KPIconUtil.PURPLE, Arrays.asList("加载模块 [framework-core] 框架核心模块!", "版本      1.2.0-SNAPSHOT"));
        logger.info("加载模块 [framework-core] 框架核心模块!");
    }


    /**
     * @Author lipeng
     * @Description 定时器调度线程
     **/
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        //线程池大小
        scheduler.setPoolSize(kpFrameworkTaskProperties.getPoolSize());
        //线程名字前缀
        scheduler.setThreadNamePrefix("kp-task-timer");
        return scheduler;
    }


    /**
     * @Author lipeng
     * @Description 线程池配置
     **/
//    @Bean(name = "threadPoolTaskExecutor")
//    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        // 设置最大线程数，当队列满时可创建的最大线程数量
//        executor.setMaxPoolSize(kpFrameworkTaskExecutorProperties.getMaxPoolSize());
//        // 设置核心线程数，线程池初始创建的线程数量
//        executor.setCorePoolSize(kpFrameworkTaskExecutorProperties.getCorePoolSize());
//        // 设置任务队列容量，存放待执行任务的队列最大长度
//        executor.setQueueCapacity(kpFrameworkTaskExecutorProperties.getQueueCapacity());
//        // 设置线程空闲时间，超过此时间的空闲线程将被销毁
//        executor.setKeepAliveSeconds(kpFrameworkTaskExecutorProperties.getKeepAliveSeconds());
//        // 设置线程名称前缀，便于日志追踪和问题排查
//        executor.setThreadNamePrefix("kp-task-executor-timer");
//        // 设置拒绝策略，当线程池和队列都满时的处理方式
//        // CallerRunsPolicy表示由调用线程来执行该任务
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        return executor;
//    }
    @Bean(name = "kpExecutorService")
    public ExecutorService kpExecutorService() {
        // 1. 自定义 ThreadFactory：设置线程名前缀为 "kp-task-executor-timer"
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("kp-task-executor-timer-%d") // %d 会自动替换为线程编号（1、2、3...）
                .setDaemon(false) // 非守护线程（默认false，确保任务执行完再退出）
                .build();

        return new ThreadPoolExecutor(
                kpFrameworkTaskExecutorProperties.getCorePoolSize(), // 核心线程数
                kpFrameworkTaskExecutorProperties.getMaxPoolSize(),  // 最大线程数
                kpFrameworkTaskExecutorProperties.getKeepAliveSeconds(), // 非核心线程空闲时间
                TimeUnit.SECONDS,            // 时间单位
                new LinkedBlockingQueue<>(kpFrameworkTaskExecutorProperties.getQueueCapacity()), // 有界队列：满了排队（容量=queueCapacity）
                threadFactory, // 传入自定义线程工厂（带名称前缀）
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }


    /**
     * @Author lipeng
     * @Description @Async 线程池配置 实现 AsyncConfigurer
     * @return java.util.concurrent.Executor
     **/
    @Override
    public Executor getAsyncExecutor() {
        // 创建基于线程池的异步任务执行器，负责管理异步任务的执行
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // 设置核心线程数：线程池初始化时创建的线程数量，即使这些线程空闲也不会被销毁
        // 适用于IO密集型任务，可同时处理多个并发请求而无需频繁创建线程
        executor.setCorePoolSize(kpFrameworkAsyncProperties.getCorePoolSize());

        // 设置最大线程数：当队列满时，线程池允许创建的最大线程数量
        // 超出核心线程数的线程在空闲时间超过keepAliveSeconds后会被销毁
        executor.setMaxPoolSize(kpFrameworkAsyncProperties.getMaxPoolSize());

        // 设置任务队列容量：当核心线程都在忙碌时，新提交的任务会被放入此队列等待执行
        // 队列满后才会创建新线程直到达到最大线程数
        executor.setQueueCapacity(kpFrameworkAsyncProperties.getQueueCapacity());

        // 设置线程名称前缀：便于在日志中识别哪些线程属于此执行器
        // 例如：kp-Async-1、kp-Async-2 等，有助于排查问题
        executor.setThreadNamePrefix("kp-Async");

        // 设置线程空闲时间：非核心线程在空闲超过此时间后会被销毁
        // 避免长时间保持空闲线程占用系统资源，60秒是常见的合理值
        executor.setKeepAliveSeconds(kpFrameworkAsyncProperties.getKeepAliveSeconds());

        // 设置拒绝策略：当线程池和队列都满时，对新提交任务的处理方式
        // CallerRunsPolicy 表示由调用者线程（提交任务的线程）直接执行该任务
        // 可避免任务丢失，同时给线程池留出处理现有任务的时间
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 添加 TaskDecorator 传递请求上下文
        executor.setTaskDecorator(new MyRequestAttributes());

        // 初始化执行器：必须调用此方法使配置生效
        // 会创建核心线程并启动线程池
        executor.initialize();

        // 返回配置好的执行器实例，供Spring框架管理异步任务
        return executor;
    }


//    @Override
//    public Executor getAsyncExecutor() {

    ////        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    ////        // 设置核心线程数、最大线程数、队列容量等参数
    ////        executor.setCorePoolSize(5);
    ////        executor.setMaxPoolSize(10);
    ////        executor.setQueueCapacity(200);
    ////        executor.setThreadNamePrefix("Async");
    ////        executor.initialize();
    ////        return executor;
//
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(5);  // 核心线程数 - 线程池创建时初始化的线程数
//        executor.setMaxPoolSize(20);// 最大线程数 - 线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
//        executor.setQueueCapacity(500);// 缓冲队列 - 用来缓冲执行任务的队列
//        // 根据任务执行时间调整线程存活时间
//        // 如果任务执行时间较长，适当增加这个值
//        // 如果任务执行时间很短，可以减小这个值
//        executor.setKeepAliveSeconds(120);   // 线程存活时间 - 当超过了核心线程出之外的线程在空闲时间到达之后会被销毁 单位秒
//
//
//        executor.setThreadNamePrefix("async-task-executor-"); // 线程名前缀 - 方便我们定位处理任务所在的线程池
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());    // 设置拒绝策略 - 当线程池满时，将任务加入队列等待执行
//        executor.setWaitForTasksToCompleteOnShutdown(true);// 等待所有任务结束后再关闭线程池
//        executor.setAwaitTerminationSeconds(60);// 设置等待时间，如果超过这个时间还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
//        // 使用自定义装饰器替代ThreadPoolTaskDecorator
//        executor.setTaskDecorator(new TaskDecorator() {
//            @Override
//            public Runnable decorate(Runnable runnable) {
//                return () -> {
//                    try {
//                        long startTime = System.currentTimeMillis();
//                        runnable.run();
//                        long endTime = System.currentTimeMillis();
//                        log.info("异步任务执行时间: {}ms", endTime - startTime);
//                    } catch (Exception e) {
//                        log.error("异步任务执行异常", e);
//                        throw e;
//                    }
//                };
//            }
//        });
//        // 初始化
//        executor.initialize();
//        return executor;
//    }
}
