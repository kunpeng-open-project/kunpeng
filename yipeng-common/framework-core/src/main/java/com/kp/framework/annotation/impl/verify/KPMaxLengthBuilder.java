package com.kp.framework.annotation.impl.verify;

import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.annotation.verify.KPMaxLength;
import com.kp.framework.utils.kptool.KPVerifyUtil;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * 最大校验。
 * @author lipeng
 * 2024/4/28
 */
@Component
public class KPMaxLengthBuilder {

    public Boolean dispose(Field field, KPMaxLength kpMaxLength, JSONObject json) {
        if (field.getType().equals(String.class)) {
            String value = json.getString(field.getName());
            KPVerifyUtil.maxLength(value, kpMaxLength.max(), kpMaxLength.errMeg());
        } else if (field.getType().equals(Integer.class)) {
            Integer value = json.getInteger(field.getName());
            KPVerifyUtil.maxLength(value, kpMaxLength.max(), kpMaxLength.errMeg());
        } else if (field.getType().equals(Long.class)) {
            Long value = json.getLong(field.getName());
            KPVerifyUtil.maxLength(value, kpMaxLength.max(), kpMaxLength.errMeg());
        }
        return true;
    }
}
