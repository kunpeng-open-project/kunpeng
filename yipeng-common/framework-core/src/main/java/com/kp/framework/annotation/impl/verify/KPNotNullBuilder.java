package com.kp.framework.annotation.impl.verify;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.annotation.verify.KPNotNull;
import com.kp.framework.utils.kptool.KPVerifyUtil;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 非空校验实现。
 * @author lipeng
 * 2024/3/11
 */
@Component
public class KPNotNullBuilder {

    public Boolean dispose(Field field, KPNotNull kpNotNull, JSONObject json) {
        if (field.getType().equals(String.class)) {
            String value = json.getString(field.getName());
            KPVerifyUtil.notNull(value, kpNotNull.errMeg());
        } else if (field.getType().equals(Integer.class)) {
            Integer value = json.getInteger(field.getName());
            KPVerifyUtil.notNull(value, kpNotNull.errMeg());
        } else if (field.getType().equals(Long.class)) {
            Long value = json.getLong(field.getName());
            KPVerifyUtil.notNull(value, kpNotNull.errMeg());
        } else if (field.getType().equals(Double.class) || field.getType().equals(Float.class)) {
            Double value = json.getDouble(field.getName());
            KPVerifyUtil.notNull(value, kpNotNull.errMeg());
        } else if (field.getType().equals(BigDecimal.class)) {
            BigDecimal value = json.getBigDecimal(field.getName());
            KPVerifyUtil.notNull(value, kpNotNull.errMeg());
        } else if (field.getType().equals(Date.class)) {
            Date value = json.getDate(field.getName());
            KPVerifyUtil.notNull(value, kpNotNull.errMeg());
        } else if (field.getType().equals(List.class)) {
            JSONArray value = null;
            try {
                value = json.getJSONArray(field.getName());
            } catch (Exception e) {
                throw new IllegalArgumentException(field.getName() + "类型错误，请传入数组类型");
            }
            if (value == null) throw new IllegalArgumentException(kpNotNull.errMeg());
            List<String> fastjsonStringList = JSON.parseArray(value.toJSONString(), String.class);
            KPVerifyUtil.notNull(fastjsonStringList, kpNotNull.errMeg());
        }
        return true;
    }
}
