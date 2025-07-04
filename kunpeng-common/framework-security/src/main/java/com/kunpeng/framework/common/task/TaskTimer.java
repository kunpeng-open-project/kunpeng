package com.kunpeng.framework.common.task;

import com.kunpeng.framework.common.properties.RedisSecurityConstant;
import com.kunpeng.framework.common.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TaskTimer {


    /**
     * @Author lipeng
     * @Description 清空授权登录次数
     * @Date 2022/5/19
     * @param
     * @return void
     **/
    @Scheduled(cron = "0 0 0 * * ?")
    private void process() {
        if (!CommonUtil.lock("authenticationToken", 11, 300))
            return;

        CommonUtil.removeBacth(RedisSecurityConstant.AUTHORIZATION_NUMBER);
        log.info("[清空redis项目获取token记录]");
    }
}
