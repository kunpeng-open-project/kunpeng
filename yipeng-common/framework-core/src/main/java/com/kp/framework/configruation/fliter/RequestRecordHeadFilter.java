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
import com.kp.framework.utils.kptool.KPStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

/**
 * @Author lipeng
 * @Description 请求记录过滤器 并设置最后一个执行
 * @Date 2024/1/24 14:39
 * @return
 **/
@Component
@Slf4j
@Order(Integer.MAX_VALUE)
public class RequestRecordHeadFilter implements Filter {

	@Autowired
	private LogUtil logUtil;

	@Autowired
	private KPLogRecordProperties kpLogRecordProperties;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;

		if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
			chain.doFilter(req, res);
			return;
		}


		Instant start = Instant.now();
		String uri = req.getRequestURI();
		if (uri.contains(".") || uri.contains("swagger") || uri.contains("v2") || uri.contains("/pay/call/back") || uri.contains("/actuator")){
			chain.doFilter(req, res);
		}else {
			MyRequestWrapper myRequestWrapper = new MyRequestWrapper(req);
			MyResponseWrapper myResponseWrapper = new MyResponseWrapper(res);
			chain.doFilter(myRequestWrapper, myResponseWrapper);
			this.accessRecord(myRequestWrapper, myResponseWrapper, res, start);
		}
	}

	/**
	 * @Author lipeng
	 * @Description 记录访问信息
	 * @Date 2023/11/20 14:17
	 * @param myRequestWrapper
	 * @return void
	 **/
	private void accessRecord(MyRequestWrapper myRequestWrapper, MyResponseWrapper myResponseWrapper, HttpServletResponse res, Instant start) {
		log.info("---------- [请求本系统接口开始] ----------");
		log.info("请求域名： {}， 请求方式： {}", myRequestWrapper.getRequestURL(), myRequestWrapper.getMethod());
		String parameter = null, result = null;
		long disposeDate = 0;
		try {
			String parameters = myRequestWrapper.getBody();
			if (KPStringUtil.isNotEmpty(parameters)){
				parameter = KPJsonUtil.toJson(myRequestWrapper.getBody()).toJSONString();
			}else{
				JSONObject row = KPRequsetUtil.getJSONParam();
				if (row.size()!=0){
					parameter = KPRequsetUtil.getJSONParam().toJSONString();
				}else{
					parameter = KPJsonUtil.toJsonString(myRequestWrapper.getParameterMap());
				}
			}
			log.info("请求参数： {}", parameter);
		}catch (Exception ex){
			JSONArray JSONArray = parseIfArray(myRequestWrapper.getBody());
			if (JSONArray==null){
				parameter = KPJsonUtil.toJsonString(myRequestWrapper.getBody());
				log.info("请求参数： {}", parameter);
				log.info("请求参数获取异常： {}", ex.getMessage());
			}else{
				log.info("请求参数： {}", JSONArray);
			}

		}

		try {
			byte[] content = myResponseWrapper.getBytes();
			if (content.length > 0) {
				result = new String(content, "UTF-8");
				log.info("返回参数： {}", result);

				byte[] gbks = result.getBytes("UTF-8");//这里编码转换操作，这里转换的一定要与接口响应的编码相同
				res.setContentLength(gbks.length);//这一步一定要有，否则字节数组长度不够会造成线程一直等待而阻塞
				res.getOutputStream().write(gbks);
				res.getOutputStream().flush();
				res.getOutputStream().close();
			}
			disposeDate = Duration.between(start, Instant.now()).toMillis();
			log.info("接口处理用时： {} 毫秒",  disposeDate);

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
		}catch (Exception ex){}
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
