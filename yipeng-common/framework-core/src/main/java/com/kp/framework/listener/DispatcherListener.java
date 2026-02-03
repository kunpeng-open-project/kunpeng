package com.kp.framework.listener;

import com.alibaba.fastjson2.JSONObject;


/**
 * 分发器接口。
 * @author lipeng
 * 2020/9/26
 */
public interface DispatcherListener {


    /**
     * 分发器。
     * @author lipeng
     * 2020/9/26
     * @param parameter 参数
     */
    public void execute(JSONObject parameter);


    //DispatcherListenerManager 中的 init 替代了
//    /**
//     * @Author lipeng
//     * @Description 初始化加载
//     * @Date 2020/9/26 10:59
//     * @param
//     * @return void
//     **/
//    @PostConstruct
//    public void construct();


    default String key() {
        return "";
    }
}
