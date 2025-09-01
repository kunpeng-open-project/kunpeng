package com.kunpeng.framework.microservices.auth.interfaces;

import com.alibaba.fastjson2.JSONObject;
import com.kunpeng.framework.configruation.FeignUserMessageConfigruation;
import com.kunpeng.framework.constant.ServerApplicationNameConConstant;
import com.kunpeng.framework.entity.bo.KPResult;
import com.kunpeng.framework.microservices.auth.fallback.PostFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Order(Integer.MAX_VALUE)
@FeignClient(name = ServerApplicationNameConConstant.AUTH_APPLICATION_NAME, contextId = "post", fallback = PostFallback.class, configuration = FeignUserMessageConfigruation.class)
public interface IPost {

    /**
     * @Author lipeng
     * @Description 根据岗位Id查询岗位信息
     * @Date 2025/7/31
     * @param parameter
     * @return com.kunpeng.framework.entity.bo.KPResult<com.alibaba.fastjson2.JSONObject>
     **/
    @PostMapping(value = "/api/post/query/post/id", headers = {"content-type=application/json"})
    KPResult<JSONObject> queryPostById(@RequestBody JSONObject parameter);
}
