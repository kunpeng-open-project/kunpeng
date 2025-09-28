package com.kp.framework.common.job;

import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.kp.framework.common.properties.MqProperties;
import com.kp.framework.modules.logRecord.mapper.InterfaceLogHistoryMapper;
import com.kp.framework.modules.logRecord.mapper.InterfaceLogMapper;
import com.kp.framework.modules.logRecord.po.InterfaceLogHistoryPO;
import com.kp.framework.modules.logRecord.po.InterfaceLogPO;
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
 * @Description 接口日志记录归档
 * @Date 2025/9/2
 * @return
 **/
@Slf4j
@Component
public class InterfaceLogHistoryHandle {


    @Autowired
    private InterfaceLogHistoryMapper interfaceLogHistoryMapper;

    @Autowired
    private InterfaceLogMapper interfaceLogMapper;

    @Resource(name = "kpExecutorService")
    private ExecutorService kpExecutorService;

    @Autowired
    private MqProperties mqProperties;

    @XxlJob("interfaceLogArchive")
    public void handle() {
        LambdaQueryWrapper<InterfaceLogPO> wrapper = Wrappers.lambdaQuery(InterfaceLogPO.class)
                .lt(InterfaceLogPO::getCallTime, KPLocalDateTimeUtil.addDays(LocalDateTime.now(), -mqProperties.getInterfaceArchiveDay()))
                .orderByAsc(InterfaceLogPO::getCallTime);

        // 把需要归档的数据先加载到内存中
        int pageNum = 1, pageSize = 5000, pageTotal = 0;
        List<InterfaceLogPO> body = new ArrayList<>();
        do {
            PageHelper.startPage(pageNum++, pageSize);
            List<InterfaceLogPO> interfaceLogPOList = interfaceLogMapper.selectList(wrapper);
            pageTotal = interfaceLogPOList.size();
            if (pageTotal == 0) break;
            body.addAll(interfaceLogPOList);
        } while (pageTotal == pageSize);


        //进多线程开始处理归档 1000 一个子线程
        List<List<InterfaceLogPO>> threadData = Lists.partition(body, 1000);
        threadData.forEach(list -> {
            kpExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    // 打印当前执行线程信息（验证线程名前缀）
                    log.info("【接口归档】线程[{}]开始处理归档任务，数据量：{}", Thread.currentThread().getName(), list.size());

                    //处理历史数据
                    KPCollectionUtil.insertBatch(interfaceLogHistoryMapper, list, InterfaceLogHistoryPO.class, 100);
                    //删除主表
                    interfaceLogMapper.deleteAllByIds(list.stream().map(InterfaceLogPO::getUuid).collect(Collectors.toList()));
                    log.info("【接口归档】线程[{}]：当前页数据归档完成，总处理量：{}", Thread.currentThread().getName(), list.size());
                }
            });
        });

        log.info("【接口归档】日志归档任务：所有待处理数据已提交至线程池，主线程结束（异步线程将继续执行）");
    }

}
