package com.kp.framework.listener;

import com.kp.framework.utils.kptool.KPServiceUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * 分发器。
 * @author lipeng
 * 2020/9/26
 */
@Component
@Slf4j
public class DispatcherListenerManager {

    private final Map<String, DispatcherListener> listener = new HashMap<>();

    private DispatcherListenerManager(KPServiceUtil kPServiceUtil) {
    }

    /**
     * 把监听器监听起来。
     * @author lipeng
     * 2020/9/26
     * @param dispatcherListener 监听器
     */
    public void register(DispatcherListener dispatcherListener) {
        String event = dispatcherListener.key().toUpperCase();
        if (KPStringUtil.isEmpty(event)) {
            log.info("注册分发器错误！event={}", event);
            return;
        }

        log.info("[监听器注册成功] {}", event);
        this.listener.put(event, dispatcherListener);
    }

    /**
     * 获取监听器。
     * @author lipeng
     * 2020/9/26
     * @param key key
     * @return com.kp.framework.listener.DispatcherListener
     */
    public DispatcherListener getListener(String key) {
        DispatcherListener dispatcherListener = this.listener.get(key);
        if (dispatcherListener == null) {
            log.info("未找到分发器！event={}", key);
        }
        return dispatcherListener;
    }


    @PostConstruct
    public void init() {
        //获取集成DispatcherListener 所有子类
        Map<String, DispatcherListener> dispatcherListeners = KPServiceUtil.getApplicationContext().getBeansOfType(DispatcherListener.class);
        // 输出所有子类
        for (Map.Entry<String, DispatcherListener> entry : dispatcherListeners.entrySet()) {
            this.register(entry.getValue());
        }
    }
}
