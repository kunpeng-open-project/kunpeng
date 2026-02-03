package com.kp.framework.configruation.interceptor;

import com.kp.framework.annotation.KPProhibitCrawlerNote;
import com.kp.framework.annotation.KPRepeatSubmitNote;
import com.kp.framework.annotation.impl.ProhibitCrawlerBuilder;
import com.kp.framework.annotation.impl.RequestSubmitBuilder;
import com.kp.framework.annotation.impl.verify.KPVerifyBuilder;
import com.kp.framework.annotation.verify.KPVerifyNote;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 全局拦截器 ，如果有全局拦截任务建议写在这里 别写多个拦截器。
 * @author lipeng
 * 2023/12/27
 */
@Component
public class GlobalHandlerInstantiator implements HandlerInterceptor {

    @Autowired
    private RequestSubmitBuilder requestSubmitBuilder;
    @Autowired
    private ProhibitCrawlerBuilder prohibitCrawlerBuilder;
    @Autowired
    private KPVerifyBuilder kpVerifyBuilder;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            KPRepeatSubmitNote repeatSubmit = handlerMethod.getMethodAnnotation(KPRepeatSubmitNote.class);
            if (repeatSubmit != null) return requestSubmitBuilder.dispose(repeatSubmit);

            KPProhibitCrawlerNote prohibitCrawler = handlerMethod.getMethodAnnotation(KPProhibitCrawlerNote.class);
            if (prohibitCrawler != null) return prohibitCrawlerBuilder.dispose(prohibitCrawler);

            KPVerifyNote kpVerifyNote = handlerMethod.getMethodAnnotation(KPVerifyNote.class);
            if (kpVerifyNote != null) return kpVerifyBuilder.verify(request, handlerMethod);
        }
        return true;
    }
}
