package com.kp.framework.microservices.auth.interfaces;

import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.configruation.FeignUserMessageConfigruation;
import com.kp.framework.constant.ServerApplicationNameConConstant;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.microservices.auth.fallback.DeptFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Order(Integer.MAX_VALUE)
@FeignClient(name = ServerApplicationNameConConstant.AUTH_APPLICATION_NAME, contextId = "auth", fallback = DeptFallback.class, configuration = FeignUserMessageConfigruation.class)
public interface IDept {

    /**
     * @Author lipeng
     * @Description 根据部门Id查询部门信息
     * @Date 2025/7/31
     * @param parameter
     * @return com.kp.framework.entity.bo.KPResult<com.alibaba.fastjson2.JSONObject>
     **/
    @PostMapping(value = "/api/dept/query/dept/id", headers = {"content-type=application/json"})
    KPResult<JSONObject> queryDeptById(@RequestBody JSONObject parameter);
}
