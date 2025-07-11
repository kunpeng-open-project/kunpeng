package com.kunpeng.framework.common.util;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

@Deprecated
@Slf4j
public class InterfaceCallRecord {

    private final static RabbitTemplate rabbitTemplate = ServiceUtil.getBean("rabbitTemplate", RabbitTemplate.class);
    ;
    private final static String NORMAL_EXCHANGE = "interface_exchange";//正常交换机
    private final static String NORMAL_ROUTING_KEY = "interface_routingkey";//正常路由


    public static void record(String result, String projectName) {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        log.info("请求域名： {}， 请求方式： {}", req.getRequestURL(), req.getMethod());
        JSONObject parameter = null;
        long disposeDate = -1;
        try {
//            parameter = getJSONParam();
//            if (parameter == null || parameter.size() == 0)
                parameter = getJSONParam(req);
            if (parameter == null || parameter.size() == 0)
                parameter = getParams(req);
            log.info("请求参数： {}", parameter);
        } catch (Exception ex) {
            log.info("请求参数： {}", parameter);
            log.info("请求参数获取异常： {}", ex.getMessage());
        }
        log.info("返回参数： {}", result);
        rabbitTemplate.convertAndSend(NORMAL_EXCHANGE, NORMAL_ROUTING_KEY, new JSONFactoryUtil()
                .put("url", req.getRequestURL())
                .put("uri", req.getRequestURI())
                .put("name", getInterfaceName(req))
                .put("method", req.getMethod())
                .put("parameters", parameter.toString())
                .put("result", result)
                .put("projectName", projectName)
                .put("callTime", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()))
                .put("disposeTime", disposeDate)
                .put("platForm", req.getHeader("Sec-Ch-Ua-Platform") == null ? "" : req.getHeader("Sec-Ch-Ua-Platform").replaceAll("\"", ""))
                .put("clinetIp", CommonUtil.getClinetIP(req))
                .put("userMessage", req.getAttribute("userMessage"))
                .buildString(), new CorrelationData(UUID.randomUUID().toString()));
    }


    private static String getInterfaceName(HttpServletRequest req) {
        if (req.getRequestURI().contains("/error")) return "异常地址error";
        HandlerMethod handlerMethod = CommonUtil.queryHandlerMethod(req);
        String name = "";
        if (handlerMethod != null) {
            ApiOperation apiOperation = handlerMethod.getMethodAnnotation(ApiOperation.class);
            name = apiOperation.value();
        }
        return name;
    }

    private static final JSONObject getJSONParam(HttpServletRequest request) {
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
            System.out.println(e);
        } finally {
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
     * @Date 2021/11/8
     * @return com.alibaba.fastjson.JSONObject
     **/
    private static final JSONObject getJSONParam() throws UnsupportedEncodingException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        JSONObject row = new JSONObject();
        if (request.getQueryString() == null || request.getQueryString() == "" || request.getQueryString().length() == 0)
            return row;

        String queryString = URLDecoder.decode(request.getQueryString(), "utf-8");
        String[] strs = queryString.split("&");
        for (String body : strs) {
            String[] str = body.split("=");
            try {
                if (str[0].contains("Array")) {
                    JSONArray jsonArray = new JSONArray();

                    try {
                        Object[] objs = str[1].split(",");
                        for (Object obj : objs) {
                            jsonArray.add(obj);
                        }
                    } catch (Exception ex) {
                    }

                    row.put(str[0].substring(0, str[0].length() - 5), jsonArray);
                } else {
                    row.put(str[0], str[1]);
                }

            } catch (Exception ex) {
                row.put(str[0], null);
            }
        }
        return row;
    }

    /**
     * @Author lipeng
     * @Description 把get请求参数转为json
     * @Date 2024/11/8
     * @param request
     * @return com.alibaba.fastjson.JSONObject
     **/
    private static JSONObject getParams(HttpServletRequest request) {
        JSONObject map = new JSONObject();
        Map<String, String[]> reqMap = request.getParameterMap();
        for (Object key : reqMap.keySet()) {
            map.put(key.toString(), ((String[]) reqMap.get(key))[0]);
        }
        return map;
    }
}
