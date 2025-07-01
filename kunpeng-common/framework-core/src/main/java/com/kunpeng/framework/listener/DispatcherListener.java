package com.kunpeng.framework.listener;


import com.alibaba.fastjson2.JSONObject;

/**
 * @Author lipeng
 * @Description 分发器接口
 * @Date 2020/9/26 10:56
 * @return
 **/
public interface DispatcherListener {


    /**
     * @Author lipeng
     * @Description  分发器
     * @Date 2020/9/26 10:58
     * @param parameter
     * @return void
     **/
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


    default String key(){return "";}
}
