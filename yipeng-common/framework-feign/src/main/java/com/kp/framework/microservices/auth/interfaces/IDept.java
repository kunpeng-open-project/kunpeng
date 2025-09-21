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

import java.util.List;

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
    @PostMapping(value = "/api/dept/query/id", headers = {"content-type=application/json"})
    KPResult<JSONObject> queryDeptById(@RequestBody JSONObject parameter);


    /**
     * @Author lipeng
     * @Description 根据部门id集合查询部门列表
     * @Date 2025/9/16
     * @param deptIds
     * @return com.kp.framework.entity.bo.KPResult<java.util.List<com.alibaba.fastjson2.JSONObject>>
     **/
    @PostMapping(value = "/api/dept/query/ids/list", headers = {"content-type=application/json"})
    KPResult<List<JSONObject>> queryDeptIdList(@RequestBody List<String> deptIds);
}
