package com.kp.framework.common.job;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kp.framework.modules.user.mapper.LoginRecordMapper;
import com.kp.framework.modules.user.po.LoginRecordPO;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPOkHttpHelperUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPThreadUtil;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @Author lipeng
 * @Description 获取ip的所在地
 * @Date 2025/7/2
 * @return
 **/
@Slf4j
@Component
public class UserLoginIPHandle {

    @Autowired
    private LoginRecordMapper loginRecordMapper;

    @XxlJob("ipQueryPosition")
    public void handle() {
        List<LoginRecordPO> loginRecordPOList = loginRecordMapper.selectList(Wrappers.lambdaQuery(LoginRecordPO.class)
                .isNull(LoginRecordPO::getLoginIpAddress)
                .or()
                .eq(LoginRecordPO::getLoginIpAddress, "")
                .last("limit 100")
        );

        log.info("[需要查询的ip数量]  " + loginRecordPOList.size());

        if (loginRecordPOList.size() == 0) return;

        log.info("[开始执行查询的ip数量定时器]");

        for (LoginRecordPO loginRecordPO : loginRecordPOList) {
            String url = KPStringUtil.format("https://ip.taobao.com/outGetIpInfo?ip={0}&accessKey={1}", loginRecordPO.getLoginIp(), "alibaba-inc");
            JSONObject row = KPJsonUtil.toJson(KPOkHttpHelperUtil.get(url));

            if (!row.getInteger("code").equals(0)) continue;

            row = row.getJSONObject("data");


            String loginIpAddress = new StringBuilder(row.getString("country"))
                    .append(row.getString("region"))
                    .append(row.getString("city").equals(row.getString("region")) ? "" : row.getString("city"))
                    .toString().replaceAll("X", "");

            loginRecordMapper.updateById(new LoginRecordPO()
                    .setAlrId(loginRecordPO.getAlrId())
                    .setLoginIpAddress(row.getString("isp").equals("内网IP") ? "内网IP" : KPStringUtil.isEmpty(loginIpAddress) ? "内网IP" : loginIpAddress)
            );
            KPThreadUtil.sleep(2000);
        }

    }
}
