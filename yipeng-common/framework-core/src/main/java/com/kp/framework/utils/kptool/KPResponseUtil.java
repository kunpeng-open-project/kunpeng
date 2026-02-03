package com.kp.framework.utils.kptool;

import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * response相关操作类。
 * @author lipeng
 * 2020/5/30
 */
@UtilityClass
public final class KPResponseUtil {
    private static Logger log = LoggerFactory.getLogger(KPResponseUtil.class);

    /**
     * 返回json。
     * @author lipeng
     * 2020/5/30
     * @param response 响应对象
     * @param jsonData 返回数据
     */
    public static void writeJson(HttpServletResponse response, Object jsonData) {
//        response.setStatus(KPStringUtil.isEmpty(status)?:status);
        response.setStatus(200);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
            out.close();
        }

        String object = KPJsonUtil.toJsonString(jsonData);
        log.info("返回内容：{}", object);
        out.println(object);
        out.flush();
        out.close();
    }


    public static void writeJson(JSONObject jsonData) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//        response.setStatus(KPStringUtil.isEmpty(status)?200:status);
        response.setStatus(200);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
            out.close();
        }

        log.info("返回内容：{}", jsonData);
        out.println(jsonData);
        out.flush();
        out.close();
    }


    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

}
