package com.kp.framework.utils.kptool;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.exception.KPServiceException;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WebService 操作。
 * @author lipeng
 * 2021/9/12
 */
@UtilityClass
public final class KPWebServiceXmlUtil {
    private static final Logger log = LoggerFactory.getLogger(KPWebServiceXmlUtil.class);

    /**
     * 把xml转json。
     * @author lipeng
     * 2021/9/12
     * @param xml  xml
     * @param interfaceNmae 接口名称
     * @return com.alibaba.fastjson2.JSONArray
     */
    public static JSONArray toJsonXml(String xml, String interfaceNmae) {
        JSONObject json = KPJsonUtil.toJson(KPXmlUtil.xml2json(xml)).getJSONObject("soap:Envelope").getJSONObject("soap:Body").getJSONObject(interfaceNmae.concat("Response")).getJSONObject(interfaceNmae.concat("Result"));
        if (json == null) return null;
        if (json.keySet().size() > 1) {
            log.info("[返回节点存在多个，当前xml需要单独处理！]");
            throw new KPServiceException("简析xml异常");
        }

        String key = json.keySet().toArray()[0].toString();
        if (json.get(key) instanceof JSONObject) {
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(json.getJSONObject(key));
            json.remove(key);
            json.put(key, jsonArray);
        }
        return json.getJSONArray(key);
    }

    /**
     * 有些接口返回的xml格式不一致导致返回多条信息。
     * @author lipeng
     * 2021/9/12
     * @param xml  xml
     * @param interfaceNmae 接口名称
     * @return com.alibaba.fastjson2.JSONObject
     */
    public static JSONObject jsonToXml(String xml, String interfaceNmae) {
        JSONObject json = KPJsonUtil.toJson(KPXmlUtil.xml2json(xml)).getJSONObject("soap:Envelope").getJSONObject("soap:Body").getJSONObject(interfaceNmae.concat("Response")).getJSONObject(interfaceNmae.concat("Result"));
        return json;
    }

    /**
     * 处理数据中间层只有一条数据时格式不正确的问题。
     * @author lipeng
     * 2021/9/12
     * @param json  json
     * @return com.alibaba.fastjson2.JSONObject
     */
    public static JSONObject toJsonObject(JSONObject json) {
        for (String key : json.keySet()) {
            if (key.startsWith("#")) {
                json.remove(key);
            }
        }

        for (String key : json.keySet()) {
            if (json.get(key) instanceof JSONObject) {
                JSONArray jsonArray = new JSONArray();
                jsonArray.add(json.getJSONObject(key));
                json.remove(key);
                json.put(key, jsonArray);
            }
        }
        return json;
    }
}
