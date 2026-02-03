package com.kp.framework.configruation.fliter;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.annotation.KPExcludeInterfaceJournal;
import com.kp.framework.configruation.config.MyRequestWrapper;
import com.kp.framework.configruation.config.MyResponseWrapper;
import com.kp.framework.configruation.mq.InterfaceRabbitMqConfig;
import com.kp.framework.configruation.properties.KPLogRecordProperties;
import com.kp.framework.utils.LogUtil;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPRabbitMqUtil;
import com.kp.framework.utils.kptool.KPRequsetUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;

/**
 * 请求记录过滤器 并设置最后一个执行。
 * @author lipeng
 * 2024/1/24
 */
@Component
@Slf4j
@Order(Integer.MAX_VALUE)
public class RequestRecordHeadFilter implements Filter {

    @Autowired
    private LogUtil logUtil;

    @Autowired
    private KPLogRecordProperties kpLogRecordProperties;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();

        // 所有特殊请求（OPTIONS、静态资源）统一处理
        if (handleSpecialRequests(req, res, chain, uri)) return;

        // 过滤静态资源/监控接口
        Instant start = Instant.now();
        MyRequestWrapper myRequestWrapper = new MyRequestWrapper(req);
        MyResponseWrapper myResponseWrapper = new MyResponseWrapper(res);
        chain.doFilter(myRequestWrapper, myResponseWrapper);
        this.accessRecord(myRequestWrapper, myResponseWrapper, res, start);
    }

    /**
     * 独立方法：处理所有无需执行业务记录的特殊请求。
     * 包括：OPTIONS跨域预检请求、Chrome DevTools静态资源、ico图标请求
     * @author lipeng
     * 2025/12/23
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param chain FilterChain
     * @param uri 请求地址
     * @return boolean
     */
    private boolean handleSpecialRequests(HttpServletRequest req, HttpServletResponse res, FilterChain chain, String uri) throws IOException, ServletException {
        // 处理OPTIONS跨域预检请求
        if (RequestMethod.OPTIONS.name().equals(req.getMethod())) {
            chain.doFilter(req, res);
            return true;
        }

        // 处理 Chrome DevTools 相关静态资源请求
        if (uri.contains("/.well-known/appspecific/com.chrome.devtools.json")) {
            res.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return true;
        }

        // 处理 ico 图标请求（如favicon.ico）
        if (uri.contains(".ico")) {
            res.setStatus(HttpServletResponse.SC_NO_CONTENT);
            res.setHeader("Cache-Control", "no-store"); // 禁用缓存
            return true;
        }

        // 处理静态资源请求 以下资源直接放行 也不记录接口日志
        if (uri.contains(".") || uri.contains("swagger") || uri.contains("v2") || uri.contains("/actuator")) {
            chain.doFilter(req, res);
            return true;
        }

        return false; // 未匹配特殊请求，继续执行过滤器链
    }

    /**
     * 记录访问信息。
     * @author lipeng
     * 2023/11/20
     * @param myRequestWrapper 请求参数
     * @param myResponseWrapper 响应参数
     * @param res 响应参数
     * @param start 请求开始时间
     */
    private void accessRecord(MyRequestWrapper myRequestWrapper, MyResponseWrapper myResponseWrapper, HttpServletResponse res, Instant start) {
        log.info("---------- [请求本系统接口开始] ----------");
        log.info("请求域名： {}， 请求方式： {}", myRequestWrapper.getRequestURL(), myRequestWrapper.getMethod());
        String parameter = null, result = null;
        long disposeDate = 0;


        try {
            String contentType = myRequestWrapper.getContentType() == null ? "" : myRequestWrapper.getContentType();

            if (contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
                try {
                    parameter = KPJsonUtil.toJson(myRequestWrapper.getBody()).toJSONString();
                } catch (Exception ex) {
                    JSONArray JSONArray = parseIfArray(myRequestWrapper.getBody());
                    if (JSONArray == null) {
                        parameter = KPJsonUtil.toJsonString(myRequestWrapper.getBody());
                    } else {
                        parameter = JSONArray.toJSONString();
                    }
                }
            } else if (contentType.contains(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
                JSONObject row = KPRequsetUtil.getParam(myRequestWrapper);
                if (!row.isEmpty()) {
                    parameter = row.toJSONString();
                } else {
                    parameter = new JSONObject().toJSONString();
                }
            } else if (contentType.contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
                parameter = "上传文件, 系统不记录";
            } else {
                parameter = contentType + "类型，未获取到参数，目前只支持application/json和application/x-www-form-urlencoded";
            }
            log.info("请求参数： {}", parameter);
        } catch (Exception ex) {
            log.info("请求参数获取异常", ex.getMessage());
        }

        try {
            byte[] content = myResponseWrapper.getBytes();
            if (content.length > 0) {
                result = new String(content, StandardCharsets.UTF_8);
                log.info("返回参数： {}", result);
                byte[] responseBytes = result.getBytes(StandardCharsets.UTF_8);
                // 支持大响应体，替换setContentLength（int限制）
                res.setContentLengthLong(responseBytes.length);
                // try-with-resources自动关闭流，避免资源泄漏
                //这是 Java 7 引入的特性，只要 OutputStream 实现了 AutoCloseable 接口，就会在 try 代码块执行完毕后自动调用 close() 方法。
                try (OutputStream outputStream = res.getOutputStream()) {
                    outputStream.write(responseBytes);
                    outputStream.flush();
                }
            }
            // 计算接口耗时
            disposeDate = Duration.between(start, Instant.now()).toMillis();
            log.info("接口处理用时： {} 毫秒", disposeDate);

            // 判断是否需要发送MQ日志
			HandlerMethod handlerMethod = KPRequsetUtil.queryHandlerMethod(myRequestWrapper);
			if (handlerMethod != null) {
				KPExcludeInterfaceJournal kpExcludeInterfaceJournal = handlerMethod.getMethodAnnotation(KPExcludeInterfaceJournal.class);
				if (kpExcludeInterfaceJournal == null) {
					if (kpLogRecordProperties.getInterfaceLog())
						KPRabbitMqUtil.sendDeadMessage(InterfaceRabbitMqConfig.NORMAL_EXCHANGE, InterfaceRabbitMqConfig.NORMAL_ROUTING_KEY, logUtil.interfaceRecordLog(myRequestWrapper, parameter, result, disposeDate));
				}
			}else{
				if (kpLogRecordProperties.getInterfaceLog())
					KPRabbitMqUtil.sendDeadMessage(InterfaceRabbitMqConfig.NORMAL_EXCHANGE, InterfaceRabbitMqConfig.NORMAL_ROUTING_KEY, logUtil.interfaceRecordLog(myRequestWrapper, parameter, result, disposeDate));
			}
        } catch (Exception ex) {
        }
        log.info("---------- [请求本系统接口结束] ----------");
        log.info("");
    }

    private static JSONArray parseIfArray(String input) {
        // 空值直接返回null
        if (input == null || input.trim().isEmpty()) {
            return null;
        }

        try {
            // 处理可能的外层引号
            String processed = input.trim();
            if (processed.startsWith("\"") && processed.endsWith("\"")) {
                processed = processed.substring(1, processed.length() - 1);
            }

            // 处理转义符
            processed = processed.replaceAll("\\\\\"", "\"");

            // 尝试解析为JSON数组
            JSONArray jsonArray = JSONArray.parseArray(processed);

            // 解析成功且确实是数组（防御性判断）
            return (jsonArray != null && !jsonArray.isEmpty()) ? jsonArray : null;
        } catch (Exception e) {
            // 解析失败，不是有效数组
            return null;
        }
    }
}
