package com.kp.framework.configruation.config;

import com.kp.framework.configruation.properties.XXLJobProperties;
import com.kp.framework.utils.kptool.KPIPUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * xxl-job config。
 * @author lipeng
 * 2017-04-28
 */
@Configuration
@Slf4j
public class XxlJobConfig {

    @Autowired
    private XXLJobProperties xxlJobProperties;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        if (KPStringUtil.isEmpty(xxlJobProperties.getAdmin().getAddresses())) return new XxlJobSpringExecutor();
        log.info(">>>>>>>>>>> xxl-job 初始化成功  <<<<<<<<<<<<<");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(xxlJobProperties.getAdmin().getAddresses());
        xxlJobSpringExecutor.setAccessToken(xxlJobProperties.getAdmin().getAccessToken());
        xxlJobSpringExecutor.setTimeout(xxlJobProperties.getAdmin().getTimeout());

        xxlJobSpringExecutor.setAppname(xxlJobProperties.getExecutor().getAppname());
        xxlJobSpringExecutor.setAddress(xxlJobProperties.getExecutor().getAddress());
        xxlJobSpringExecutor.setIp(xxlJobProperties.getExecutor().getIp());
        if (KPStringUtil.isEmpty(xxlJobProperties.getExecutor().getIp())) {
            xxlJobSpringExecutor.setPort(KPIPUtil.findAvailablePort(KPIPUtil.getHostIp(), xxlJobProperties.getExecutor().getPort()));
        } else {
            xxlJobSpringExecutor.setPort(KPIPUtil.findAvailablePort(xxlJobProperties.getExecutor().getIp(), xxlJobProperties.getExecutor().getPort()));
        }
        xxlJobSpringExecutor.setLogPath(xxlJobProperties.getExecutor().getLogpath());
        xxlJobSpringExecutor.setLogRetentionDays(xxlJobProperties.getExecutor().getLogRetentionDays());

        return xxlJobSpringExecutor;
    }
}