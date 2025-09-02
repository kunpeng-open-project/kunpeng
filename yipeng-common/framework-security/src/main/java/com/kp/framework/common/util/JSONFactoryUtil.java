package com.kp.framework.common.util;

import com.alibaba.fastjson2.JSONObject;

/**
 * @Author lipeng
 * @Description 组装josn
 * @Date 2021/3/4
 * @return
 **/
@Deprecated
public final class JSONFactoryUtil {
    private JSONObject json = null;


    public JSONFactoryUtil() {
        this.json = new JSONObject();
    }

    public JSONFactoryUtil(JSONObject json) {
        this.json = json;
    }


    public JSONFactoryUtil put(String key, Object value) {
        json.put(key, value);
        return this;
    }

    public JSONFactoryUtil put(Boolean isNull, String key, Object value) {
        if (isNull) json.put(key, value);
        return this;
    }


    public JSONObject build() {
        return json;
    }

    public String buildString() {
        return CommonUtil.toJsonString(json);
    }
}
