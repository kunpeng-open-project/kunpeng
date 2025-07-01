package com.kunpeng.framework.utils.kptool;

import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author 李鹏
 * @Description //
 * @Date $ $
 * @Param $
 * @return $
 **/
public final class KPResponseUtil {
    private static Logger log = LoggerFactory.getLogger(KPResponseUtil.class );

    private KPResponseUtil(){}


    /* *
     * @Author 李鹏
     * @Description //返回json
     * @Date 2020/5/30 21:30
     * @Param [response, jsonData]
     * @return void
     **/
    public static final void writeJson(HttpServletResponse response, Object jsonData) {
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


    public static final void writeJson(JSONObject jsonData) {
        HttpServletResponse response =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
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
