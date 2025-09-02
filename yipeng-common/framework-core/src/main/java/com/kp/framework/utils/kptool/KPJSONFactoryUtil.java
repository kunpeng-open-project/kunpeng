package com.kp.framework.utils.kptool;

import com.alibaba.fastjson2.JSONObject;

/**
 * @Author lipeng
 * @Description 组装josn
 * @Date 2021/3/4 16:25
 * @return
 **/
public final class KPJSONFactoryUtil {
    private JSONObject json = null;


    public KPJSONFactoryUtil() {
        this.json = new JSONObject();
    }

    public KPJSONFactoryUtil(JSONObject json) {
        this.json = json;
    }


    public KPJSONFactoryUtil put(String key, Object value) {
        json.put(key, value);
        return this;
    }

    public KPJSONFactoryUtil put(Boolean isNull, String key, Object value) {
        if (isNull) json.put(key, value);
        return this;
    }


    public JSONObject build() {
        return json;
    }

    public String buildString() {
        return KPJsonUtil.toJsonString(json);
    }
}
