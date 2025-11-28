package com.kp.framework.utils.kptool;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.configruation.config.MyRequestWrapper;
import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 李鹏
 * @Description //
 * @Date $ $
 * @Param $
 * @return $
 **/
@UtilityClass
public final class KPRequsetUtil {


    /**
     * @Author lipeng
     * @Description 获取 application/json 入参中的参数
     * @Date 2020/5/29 16:19
     * @param request
     * @return com.alibaba.fastjson2.JSONObject
     **/
    public JSONObject getJSONParam(HttpServletRequest request) {
        JSONObject jsonParam = null;
        BufferedReader streamReader = null;
        try {
            // 获取输入流
            streamReader = request.getReader();
            // 写入数据到Stringbuilder
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = streamReader.readLine()) != null) {
                sb.append(line);
            }
            jsonParam = JSONObject.parseObject(sb.toString());
        } catch (Exception e) {
            try {
                jsonParam = KPJsonUtil.toJson(((MyRequestWrapper) request).getBody());
            } catch (Exception ex) {
            }
        } finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return jsonParam;
    }


//    /**
//     * @Author lipeng
//     * @Description 把get请求参数转为json
//     * @Date 2021/11/8 15:19
//     * @return com.alibaba.fastjson.JSONObject
//     **/
//    public static final JSONObject getJSONParam() throws UnsupportedEncodingException {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        JSONObject row = new JSONObject();
//        if (KPStringUtil.isEmpty(request.getQueryString()))
//            return row;
//
//        String queryString = URLDecoder.decode(request.getQueryString(), "utf-8");
//        String[] strs = queryString.split("&");
//        for (String body : strs) {
//            String[] str = body.split("=");
//            try {
//                if (str[0].contains("Array")) {
//                    JSONArray jsonArray = new JSONArray();
//
//                    try {
//                        Object[] objs = str[1].split(",");
//                        for (Object obj : objs) {
//                            jsonArray.add(obj);
//                        }
//                    } catch (Exception ex) {
//                    }
//
//                    row.put(str[0].substring(0, str[0].length() - 5), jsonArray);
//                } else {
//                    row.put(str[0], str[1]);
//                }
//
//            } catch (Exception ex) {
//                row.put(str[0], null);
//            }
//        }
//        return row;
//    }


    /**
     * @Author lipeng
     * @Description 把request.getParameterMap()请求参数转为json
     * @Date 2024/11/8 14:53
     * @param request
     * @return com.alibaba.fastjson2.JSONObject
     **/
    public JSONObject getParam(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        // 获取所有参数的键值对集合
        Map<String, String[]> paramMap = request.getParameterMap();

        // 遍历 entrySet，性能更好
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();

            if (values != null && values.length > 0) {
                if (values.length == 1) {
                    // 如果只有一个值，直接存入字符串
                    result.put(key, values[0]);
                } else {
                    // 如果有多个值，存入 JSONArray
                    JSONArray jsonArray = new JSONArray();
                    for (String value : values) {
                        jsonArray.add(value);
                    }
                    result.put(key, jsonArray);
                }
            }
            // 如果 values 为 null 或空数组，则不存入该 key
        }
        return result;
    }


    /**
     * @Author lipeng
     * @Description 获取 request
     * @Date 2024/4/2 14:14
     * @param
     * @return javax.servlet.http.HttpServletRequest
     **/
    public HttpServletRequest getRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception ex) {
        }
        return null;
    }


    /**
     * @Author lipeng
     * @Description 多线程里面使用request
     * @Date 2024/4/2 14:13
     * @param
     * @return void
     **/
    public void setRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        RequestContextHolder.setRequestAttributes(requestAttributes, true);
    }

    /**
     * @Author lipeng
     * @Description 新增：清理请求上下文方法
     * @Date 2025/9/2 23:38
     * @param
     * @return void
     **/
    public void clearRequest() {
        // 移除当前线程的 RequestAttributes（清除 ThreadLocal 中的值）
        RequestContextHolder.resetRequestAttributes();
    }


    /**
     * @Author lipeng
     * @Description 从req中获取HandlerMethod
     * @Date 2024/4/10 14:50
     * @param req
     * @return org.springframework.web.method.HandlerMethod
     **/
    public HandlerMethod queryHandlerMethod(HttpServletRequest req) {
        try {
            RequestMappingHandlerMapping handlerMapping = KPServiceUtil.getBean(RequestMappingHandlerMapping.class);
            HandlerExecutionChain handlerExecutionChain = handlerMapping.getHandler(req);
            if (handlerExecutionChain != null && handlerExecutionChain.getHandler() instanceof HandlerMethod) {
                return (HandlerMethod) handlerExecutionChain.getHandler();
            }
        } catch (Exception e) {
        }
        return null;
    }


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
