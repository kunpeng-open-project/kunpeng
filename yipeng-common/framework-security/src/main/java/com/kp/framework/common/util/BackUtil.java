package com.kp.framework.common.util;

import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.common.enums.AuthCodeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Deprecated
public class BackUtil {

    @Value("${kp.project-name}")
    private String projectName;


    @Value("${kp.log.interface-log:true}")
    private Boolean interfaceLog;

    public void writeJson(Integer code, String message) {
        JSONObject body = new JSONObject()
                .fluentPut("code", code)
                .fluentPut("message", message);

        if (interfaceLog)
            InterfaceCallRecord.record(body.toString(), projectName);
        CommonUtil.writeJson(body);
    }


    public void writeJson(AuthCodeEnum authCodeEnum) {
        JSONObject body = new JSONObject()
                .fluentPut("code", authCodeEnum.code())
                .fluentPut("message", authCodeEnum.message());
        if (interfaceLog)
            InterfaceCallRecord.record(body.toString(), projectName);
        CommonUtil.writeJson(body);
    }
}
