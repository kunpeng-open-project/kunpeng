package com.kunpeng.framework.common.job;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kunpeng.framework.modules.user.mapper.LoginRecordMapper;
import com.kunpeng.framework.modules.user.po.LoginRecordPO;
import com.kunpeng.framework.utils.kptool.KPJsonUtil;
import com.kunpeng.framework.utils.kptool.KPOkHttpHelperUtil;
import com.kunpeng.framework.utils.kptool.KPStringUtil;
import com.kunpeng.framework.utils.kptool.KPThreadUtil;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

        if (loginRecordPOList.size() == 0) return;

        for (LoginRecordPO loginRecordPO : loginRecordPOList) {
            String url = KPStringUtil.format("https://ip.taobao.com/outGetIpInfo?ip={0}&accessKey={1}", loginRecordPO.getLoginIp(), "alibaba-inc");
            JSONObject row = KPJsonUtil.toJson(KPOkHttpHelperUtil.get(url));

            if (!row.getInteger("code").equals(0)) continue;

            row = row.getJSONObject("data");

            loginRecordMapper.updateById(new LoginRecordPO()
                    .setAlrId(loginRecordPO.getAlrId())
                    .setLoginIpAddress(new StringBuilder(row.getString("country"))
                            .append(row.getString("region"))
                            .append(row.getString("city").equals(row.getString("region")) ? "" : row.getString("city"))
                            .toString()
                            .replaceAll("X", ""))
            );
            KPThreadUtil.sleep(1000);
        }

    }
}
