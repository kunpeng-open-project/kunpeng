package com.kunpeng.framework.annotation.impl.verify;

import com.alibaba.fastjson2.JSONObject;
import com.kunpeng.framework.annotation.verify.KPLength;
import com.kunpeng.framework.utils.kptool.KPVerifyUtil;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.math.BigDecimal;


/**
 * @Author lipeng
 * @Description 校验字段长度
 * @Date 2024/3/1 9:56
 * @return
 **/
@Component
public class KPLengthBuilder {

    public Boolean dispose(Field field, KPLength kplength, JSONObject json)  {
        if (field.getType().equals(String.class)) {
            String value =  json.getString(field.getName());
            KPVerifyUtil.length(value, kplength.min(), kplength.max(), kplength.errMeg());
        } else if (field.getType().equals(Integer.class)) {
            Integer value =  json.getInteger(field.getName());
            KPVerifyUtil.length(value, kplength.min(), kplength.max(), kplength.errMeg());
        } else if (field.getType().equals(Long.class)) {
            Long value =  json.getLong(field.getName());
            KPVerifyUtil.length(value, kplength.min(), kplength.max(), kplength.errMeg());
        } else if (field.getType().equals(Double.class) || field.getType().equals(Float.class)) {
            Double value =  json.getDouble(field.getName());
            KPVerifyUtil.length(value, kplength.min(), kplength.max(), kplength.errMeg());
        } else if (field.getType().equals(BigDecimal.class)) {
            BigDecimal value =  json.getBigDecimal(field.getName());
            KPVerifyUtil.length(value, kplength.min(), kplength.max(), kplength.errMeg());
        }
        return true;
    }
}
