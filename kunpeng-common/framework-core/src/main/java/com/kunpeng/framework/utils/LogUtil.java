package com.kunpeng.framework.utils;

import com.kunpeng.framework.configruation.properties.KunPengFrameworkConfig;
import com.kunpeng.framework.utils.kptool.KPDateUtil;
import com.kunpeng.framework.utils.kptool.KPIPUtil;
import com.kunpeng.framework.utils.kptool.KPJSONFactoryUtil;
import com.kunpeng.framework.utils.kptool.KPRequsetUtil;
import com.kunpeng.framework.utils.kptool.KPStringUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Component
public class LogUtil {

    @Autowired
    private KunPengFrameworkConfig kunPengFrameworkConfig;


    /**
     * @param req
     * @param parameter   入参
     * @param result      出差
     * @param disposeDate 接口用时
     * @return java.lang.String
     * @Author lipeng
     * @Description 内部接口用时
     * @Date 2024/2/5
     **/
    public String interfaceRecordLog(HttpServletRequest req, String parameter, String result, Long disposeDate) {
        return new KPJSONFactoryUtil()
                .put("url", req.getRequestURL())
                .put("uri", req.getRequestURI())
                .put("name", this.getInterfaceName(req))
                .put("method", req.getMethod())
                .put("parameters", parameter)
                .put("result", result)
                .put("projectName", kunPengFrameworkConfig.getProjectName())
                .put("callTime", KPDateUtil.dateFormat(new Date(), KPDateUtil.DATE_TIME_PATTERN))
                .put("disposeTime", disposeDate)
                .put("platForm", req.getHeader("Sec-Ch-Ua-Platform") == null ? "" : req.getHeader("Sec-Ch-Ua-Platform").replaceAll("\"", ""))
                .put("clinetIp", KPIPUtil.getClinetIP())
                .put("userMessage", req.getAttribute("userMessage"))
                .buildString();
    }


    /**
     * @param url
     * @param method
     * @param req
     * @param parameter   入参
     * @param result      出差
     * @param disposeDate 接口用时
     * @return java.lang.String
     * @Author lipeng
     * @Description 调用第三方接口用时
     * @Date 2024/2/5
     **/
    public String interfaceRecordLog(String url, String method, HttpServletRequest req, String parameter, String result, Long disposeDate) {
        if (KPStringUtil.isEmpty(req)) {
            return new KPJSONFactoryUtil()
                    .put("url", url)
                    .put("name", this.getInterfaceName(req))
                    .put("method", method)
                    .put("parameters", parameter)
                    .put("result", result)
                    .put("projectName", kunPengFrameworkConfig.getProjectName())
                    .put("callTime", KPDateUtil.dateFormat(new Date(), KPDateUtil.DATE_TIME_PATTERN))
                    .put("disposeTime", disposeDate)
                    .put("platForm", "")
                    .put("clinetIp", KPStringUtil.isEmpty(KPIPUtil.getClinetIP()) ? KPIPUtil.getHostIp() : KPIPUtil.getClinetIP())
                    .buildString();
        }


        return new KPJSONFactoryUtil()
                .put("url", url)
                .put("name", this.getInterfaceName(req))
                .put("method", method)
                .put("parameters", parameter)
                .put("result", result)
                .put("projectName", kunPengFrameworkConfig.getProjectName())
                .put("callTime", KPDateUtil.dateFormat(new Date(), KPDateUtil.DATE_TIME_PATTERN))
                .put("disposeTime", disposeDate)
                .put(KPStringUtil.isNotEmpty(req), "platForm", req.getHeader("Sec-Ch-Ua-Platform") == null ? "" : req.getHeader("Sec-Ch-Ua-Platform").replaceAll("\"", ""))
                .put("clinetIp", KPIPUtil.getClinetIP())
                .put(KPStringUtil.isNotEmpty(req), "userMessage", req.getAttribute("userMessage"))
                .buildString();
    }


    private String getInterfaceName(HttpServletRequest req) {
        HandlerMethod handlerMethod = KPRequsetUtil.queryHandlerMethod(req);
        String name = "";
        if (handlerMethod != null) {
            ApiOperation apiOperation = handlerMethod.getMethodAnnotation(ApiOperation.class);
            name = apiOperation.value();
        }
        return name;
    }

}
