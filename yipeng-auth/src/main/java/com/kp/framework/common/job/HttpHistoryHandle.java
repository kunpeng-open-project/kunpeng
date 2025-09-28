package com.kp.framework.common.job;

import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.kp.framework.common.properties.MqProperties;
import com.kp.framework.modules.logRecord.mapper.HttpLogHistoryMapper;
import com.kp.framework.modules.logRecord.mapper.HttpLogMapper;
import com.kp.framework.modules.logRecord.po.HttpLogHistoryPO;
import com.kp.framework.modules.logRecord.po.HttpLogPO;
import com.kp.framework.utils.kptool.KPCollectionUtil;
import com.kp.framework.utils.kptool.KPLocalDateTimeUtil;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;


/**
 * @Author lipeng
 * @Description http日志记录归档
 * @Date 2025/9/2
 * @return
 **/
@Slf4j
@Component
public class HttpHistoryHandle {


    @Autowired
    private HttpLogHistoryMapper httpLogHistoryMapper;

    @Autowired
    private HttpLogMapper httpLogMapper;

    @Resource(name = "kpExecutorService")
    private ExecutorService kpExecutorService;

    @Autowired
    private MqProperties mqProperties;

    @XxlJob("httpLogArchive")
    public void handle() {
        LambdaQueryWrapper<HttpLogPO> wrapper = Wrappers.lambdaQuery(HttpLogPO.class)
                .lt(HttpLogPO::getCallTime, KPLocalDateTimeUtil.addDays(LocalDateTime.now(), -mqProperties.getHttpArchiveDay()))
                .orderByAsc(HttpLogPO::getCallTime);

        // 把需要归档的数据先加载到内存中
        int pageNum = 1, pageSize = 5000, pageTotal = 0;
        List<HttpLogPO> body = new ArrayList<>();
        do {
            PageHelper.startPage(pageNum++, pageSize);
            List<HttpLogPO> httpLogPOList = httpLogMapper.selectList(wrapper);
            pageTotal = httpLogPOList.size();
            if (pageTotal == 0) break;
            body.addAll(httpLogPOList);
        } while (pageTotal == pageSize);


        //进多线程开始处理归档 1000 一个子线程
        List<List<HttpLogPO>> threadData = Lists.partition(body, 1000);
        threadData.forEach(list -> {
            kpExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    // 打印当前执行线程信息（验证线程名前缀）
                    log.info("【http归档】线程[{}]开始处理归档任务，数据量：{}", Thread.currentThread().getName(), list.size());

                    //处理历史数据
                    KPCollectionUtil.insertBatch(httpLogHistoryMapper, list, HttpLogHistoryPO.class, 100);
                    //删除主表
                    httpLogMapper.deleteAllByIds(list.stream().map(HttpLogPO::getUuid).collect(Collectors.toList()));
                    log.info("【http归档】线程[{}]：当前页数据归档完成，总处理量：{}", Thread.currentThread().getName(), list.size());
                }
            });
        });

        log.info("【http归档】日志归档任务：所有待处理数据已提交至线程池，主线程结束（异步线程将继续执行）");
    }

}
