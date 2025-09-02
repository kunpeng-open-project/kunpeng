package com.kp.framework.utils.kptool;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public final class KPUrlUtil {



    /**
     * @Author lipeng
     * @Description get请求从url 获取参数
     * @Date 2024/1/31 15:14
     * @param url
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    public static Map<String, String> getQueryParams(String url) throws UnsupportedEncodingException, MalformedURLException {
        try {
            Map<String, String> params = new HashMap<>();
            URL urlObject = new URL(url);
            String query = urlObject.getQuery();

            if (query != null && !query.isEmpty()) {
                String[] queryParams = query.split("&");
                for (String param : queryParams) {
                    String[] pair = param.split("=");
                    if (pair.length > 1) {
                        String key = URLDecoder.decode(pair[0], "UTF-8");
                        String value = URLDecoder.decode(pair[1], "UTF-8");
                        params.put(key, value);
                    } else if (pair.length == 1) { // 处理无值的键（例如：key）
                        String key = URLDecoder.decode(pair[0], "UTF-8");
                        params.put(key, "");
                    }
                }
            }

            return params;
        }catch (Exception ex){}
        return null;
    }
}
