package com.kp.framework.common.job;

import com.kp.framework.entity.bo.DictionaryBO;
import com.kp.framework.modules.logRecord.service.InterfaceLogService;
import com.kp.framework.utils.kptool.KPJSONFactoryUtil;
import com.kp.framework.utils.kptool.KPRedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CacheTask {

    @Autowired
    private InterfaceLogService interfaceLogService;

    /**
     * 项目启动立即执行一次 然后每隔60分钟执行一次。
     * @author lipeng
     * 2025/10/17
     */
    @Scheduled(fixedRate = 3600000, initialDelay = 0) // 以固定频率执
    private void preload() {
        if (!KPRedisUtil.lock("preloadServiceName", 11, 1800))
            return;

        log.info("[后台缓存执行]");
        interfaceLogService.clearCache();
        List<DictionaryBO> project = interfaceLogService.queryProject();
        project.forEach(dictionaryBO -> {
            interfaceLogService.queryInterfaceName(new KPJSONFactoryUtil().put("projectName", dictionaryBO.getValue()).build());
        });
    }
}
