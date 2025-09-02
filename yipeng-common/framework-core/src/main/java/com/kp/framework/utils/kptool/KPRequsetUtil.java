package com.kp.framework.utils.kptool;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.configruation.config.MyRequestWrapper;
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
import java.net.URLDecoder;
import java.util.Map;

/**
 * @Author 李鹏
 * @Description //
 * @Date $ $
 * @Param $
 * @return $
 **/
public final class KPRequsetUtil {

    private KPRequsetUtil(){}

    /* *
     * @Author 李鹏
     * @Description //request 中获取json
     * @Date 2020/5/29 16:19
     * @Param [request]
     * @return com.alibaba.fastjson.JSONObject
     **/
    public static final JSONObject getJSONParam(HttpServletRequest request){
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
        }
        finally {
            try {
                streamReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return jsonParam;
    }


    /**
     * @Author lipeng
     * @Description 把get请求参数转为json
     * @Date 2021/11/8 15:19
     * @return com.alibaba.fastjson.JSONObject
     **/
    public static final JSONObject getJSONParam() throws UnsupportedEncodingException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        JSONObject row = new JSONObject();
        if (KPStringUtil.isEmpty(request.getQueryString()))
            return row;

        String queryString = URLDecoder.decode(request.getQueryString(), "utf-8");
        String[] strs = queryString.split("&");
        for (String body : strs){
            String[] str = body.split("=");
            try {
                if (str[0].contains("Array")){
                    JSONArray jsonArray = new JSONArray();

                    try {
                        Object[] objs = str[1].split(",");
                        for (Object obj : objs){
                            jsonArray.add(obj);
                        }
                    }catch (Exception ex){}

                    row.put(str[0].substring(0, str[0].length()-5), jsonArray);
                }else{
                    row.put(str[0], str[1]);
                }

            }catch (Exception ex){
                row.put(str[0], null);
            }
        }
        return row;
    }

    /**
     * @Author lipeng
     * @Description 把get请求参数转为json
     * @Date 2024/11/8 14:53
     * @param request
     * @return com.alibaba.fastjson.JSONObject
     **/
    public static JSONObject getParams(HttpServletRequest request){
        JSONObject map = new JSONObject();
        Map<String, String[]> reqMap = request.getParameterMap();
        for(Object key:reqMap.keySet()){
            map.put(key.toString(), ((String[])reqMap.get(key))[0]);
        }
        return map;
    }


    /**
     * @Author lipeng
     * @Description 获取 request
     * @Date 2024/4/2 14:14
     * @param
     * @return javax.servlet.http.HttpServletRequest
     **/
    public static HttpServletRequest getRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }catch (Exception ex){}
        return null;
    }


    /**
     * @Author lipeng
     * @Description 多线程里面使用request
     * @Date 2024/4/2 14:13
     * @param
     * @return void
     **/
    public static void setRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        RequestContextHolder.setRequestAttributes(requestAttributes,true);
    }



    /**
     * @Author lipeng
     * @Description 从req中获取HandlerMethod
     * @Date 2024/4/10 14:50
     * @param req
     * @return org.springframework.web.method.HandlerMethod
     **/
    public static HandlerMethod queryHandlerMethod(HttpServletRequest req){
        try {
            RequestMappingHandlerMapping handlerMapping = KPServiceUtil.getBean(RequestMappingHandlerMapping.class);
            HandlerExecutionChain handlerExecutionChain = handlerMapping.getHandler(req);
            if (handlerExecutionChain != null && handlerExecutionChain.getHandler() instanceof HandlerMethod) {
                return (HandlerMethod) handlerExecutionChain.getHandler();
            }
        } catch (Exception e) {}
        return null;
    }
}
