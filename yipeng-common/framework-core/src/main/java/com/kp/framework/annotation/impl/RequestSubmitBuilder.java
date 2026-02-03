package com.kp.framework.annotation.impl;

import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.annotation.KPRepeatSubmitNote;
import com.kp.framework.entity.internal.ResultCode;
import com.kp.framework.utils.kptool.KPIPUtil;
import com.kp.framework.utils.kptool.KPJSONFactoryUtil;
import com.kp.framework.utils.kptool.KPRedisUtil;
import com.kp.framework.utils.kptool.KPRequsetUtil;
import com.kp.framework.utils.kptool.KPResponseUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 * KPRepeatSubmitNote 防抖注解实现方式。
 * @author lipeng
 * 2023/12/19
 */
@Component
public class RequestSubmitBuilder {

    private String redis_key = "request:submit:";

    public Boolean dispose(KPRepeatSubmitNote repeatSubmit) {
        HttpServletRequest request = KPRequsetUtil.getRequest();
        String redisKey = redis_key + KPIPUtil.getClientIP() + ":" + request.getRequestURI();

        String timestamp = KPRedisUtil.getString(redisKey);
        if (KPStringUtil.isEmpty(timestamp)) {
            KPRedisUtil.set(redisKey, System.currentTimeMillis(), 3600);
            return true;
        }

        if (System.currentTimeMillis() - Long.valueOf(timestamp) <= repeatSubmit.value()) {
            JSONObject error = new KPJSONFactoryUtil()
                    .put("code", ResultCode.INTERFACE_VISIT_ERROR.code())
                    .put("message", ResultCode.INTERFACE_VISIT_ERROR.message())
                    .put("data", "")
                    .build();
            KPResponseUtil.writeJson(error);
            return false;
        } else {
            KPRedisUtil.set(redisKey, System.currentTimeMillis(), 3600);
        }
        return true;
    }
}
