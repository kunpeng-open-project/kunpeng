package com.kp.framework.microservices.auth.interfaces;

import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.configruation.FeignUserMessageConfigruation;
import com.kp.framework.constant.ServerApplicationNameConConstant;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.microservices.auth.fallback.PostFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Order(Integer.MAX_VALUE)
@FeignClient(name = ServerApplicationNameConConstant.AUTH_APPLICATION_NAME, contextId = "post", fallback = PostFallback.class, configuration = FeignUserMessageConfigruation.class)
public interface IPost {

    /**
     * 根据岗位Id查询岗位信息。
     * @author lipeng
     * 2025/7/31
     * @param parameter 查询参数
     * @return com.kp.framework.entity.bo.KPResult<com.alibaba.fastjson2.JSONObject>
     */
    @PostMapping(value = "/api/post/query/id", headers = {"content-type=application/json"})
    KPResult<JSONObject> queryPostById(@RequestBody JSONObject parameter);

    /**
     * 根据岗位id集合查询岗位列表。
     * @author lipeng
     * 2025/9/16
     * @param postIds 岗位id集合
     * @return com.kp.framework.entity.bo.KPResult<java.util.List<com.alibaba.fastjson2.JSONObject>>
     */
    @PostMapping(value = "/api/post/query/ids/list", headers = {"content-type=application/json"})
    KPResult<List<JSONObject>> queryPostIdList(@RequestBody List<String> postIds);
}
