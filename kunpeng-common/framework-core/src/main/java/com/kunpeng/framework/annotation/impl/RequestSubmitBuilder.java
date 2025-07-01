package com.kunpeng.framework.annotation.impl;

import com.alibaba.fastjson2.JSONObject;
import com.kunpeng.framework.annotation.KPRepeatSubmitNote;
import com.kunpeng.framework.entity.internal.ResultCode;
import com.kunpeng.framework.utils.kptool.KPIPUtil;
import com.kunpeng.framework.utils.kptool.KPJSONFactoryUtil;
import com.kunpeng.framework.utils.kptool.KPRedisUtil;
import com.kunpeng.framework.utils.kptool.KPRequsetUtil;
import com.kunpeng.framework.utils.kptool.KPResponseUtil;
import com.kunpeng.framework.utils.kptool.KPStringUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


/**
 * @Author lipeng
 * @Description
 * @Date 2023/12/19 12:04
 * @return
 **/
@Component
public class RequestSubmitBuilder {

    private String redis_key = "request:submit:";

    public Boolean dispose(KPRepeatSubmitNote repeatSubmit)  {
        HttpServletRequest request = KPRequsetUtil.getRequest();
        String redisKey = redis_key +  KPIPUtil.getClinetIP() + ":" + request.getRequestURI();

        String timestamp = KPRedisUtil.getString(redisKey);
        if (KPStringUtil.isEmpty(timestamp)){
            KPRedisUtil.set(redisKey, System.currentTimeMillis(), 3600);
            return true;
        }

        if (System.currentTimeMillis() - Long.valueOf(timestamp) <= repeatSubmit.value()){
            JSONObject error = new KPJSONFactoryUtil()
                    .put("code", ResultCode.INTERFACE_VISIT_ERROR.code())
                    .put("message", ResultCode.INTERFACE_VISIT_ERROR.message())
                    .put("data", "")
                    .build();
            KPResponseUtil.writeJson(error);
            return false;
        }else {
            KPRedisUtil.set(redisKey, System.currentTimeMillis(), 3600);
        }
        return true;
    }
}
