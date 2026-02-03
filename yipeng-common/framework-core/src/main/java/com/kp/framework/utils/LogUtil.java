package com.kp.framework.utils;

import com.kp.framework.configruation.properties.KPFrameworkConfig;
import com.kp.framework.utils.kptool.KPDateUtil;
import com.kp.framework.utils.kptool.KPIPUtil;
import com.kp.framework.utils.kptool.KPJSONFactoryUtil;
import com.kp.framework.utils.kptool.KPRequsetUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.util.Date;


@Component
public class LogUtil {

    @Autowired
    private KPFrameworkConfig kpFrameworkConfig;

    /**
     * 内部接口用时。
     * @author lipeng
     * 2024/2/5
     * @param req 请求
     * @param parameter 入参
     * @param result 出参
     * @param disposeDate 接口用时
     * @return java.lang.String
     */
    public String interfaceRecordLog(HttpServletRequest req, String parameter, String result, Long disposeDate) {
        return new KPJSONFactoryUtil()
                .put("url", req.getRequestURL().toString())
                .put("uri", req.getRequestURI())
                .put("name", this.getInterfaceName(req))
                .put("method", req.getMethod())
                .put("parameters", parameter)
                .put("result", result)
                .put("projectName", kpFrameworkConfig.getProjectName())
                .put("callTime", KPDateUtil.format(new Date(), KPDateUtil.DATE_TIME_PATTERN))
                .put("disposeTime", disposeDate)
                .put("platForm", req.getHeader("Sec-Ch-Ua-Platform") == null ? "" : req.getHeader("Sec-Ch-Ua-Platform").replaceAll("\"", ""))
                .put("clinetIp", KPIPUtil.getClientIP())
                .put("userMessage", req.getAttribute("userMessage"))
                .buildString();
    }

    /**
     * 调用第三方接口用时。
     * @author lipeng
     * 2024/2/5
     * @param url  url
     * @param method  方法
     * @param req 请求
     * @param parameter 入参
     * @param result 出参
     * @param disposeDate 接口用时
     * @return java.lang.String
     */
    public String interfaceRecordLog(String url, String method, HttpServletRequest req, String parameter, String result, Long disposeDate) {
        if (KPStringUtil.isEmpty(req)) {
            return new KPJSONFactoryUtil()
                    .put("url", url)
                    .put("name", this.getInterfaceName(req))
                    .put("method", method)
                    .put("parameters", parameter)
                    .put("result", result)
                    .put("projectName", kpFrameworkConfig.getProjectName())
                    .put("callTime", KPDateUtil.format(new Date(), KPDateUtil.DATE_TIME_PATTERN))
                    .put("disposeTime", disposeDate)
                    .put("platForm", "")
                    .put("clinetIp", KPStringUtil.isEmpty(KPIPUtil.getClientIP()) ? KPIPUtil.getHostIp() : KPIPUtil.getClientIP())
                    .buildString();
        }


        return new KPJSONFactoryUtil()
                .put("url", url)
                .put("name", this.getInterfaceName(req))
                .put("method", method)
                .put("parameters", parameter)
                .put("result", result)
                .put("projectName", kpFrameworkConfig.getProjectName())
                .put("callTime", KPDateUtil.format(new Date(), KPDateUtil.DATE_TIME_PATTERN))
                .put("disposeTime", disposeDate)
                .put(KPStringUtil.isNotEmpty(req), "platForm", req.getHeader("Sec-Ch-Ua-Platform") == null ? "" : req.getHeader("Sec-Ch-Ua-Platform").replaceAll("\"", ""))
                .put("clinetIp", KPIPUtil.getClientIP())
                .put(KPStringUtil.isNotEmpty(req), "userMessage", req.getAttribute("userMessage"))
                .buildString();
    }


    private String getInterfaceName(HttpServletRequest req) {
        HandlerMethod handlerMethod = KPRequsetUtil.queryHandlerMethod(req);
        String name = "";
        if (handlerMethod != null) {
            Operation operation = handlerMethod.getMethodAnnotation(Operation.class);
            if (operation != null) name = operation.summary();
        }
        return name;
    }
}
