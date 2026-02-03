package com.kp.framework.microservices.auth.interfaces;

import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.configruation.FeignUserMessageConfigruation;
import com.kp.framework.constant.ServerApplicationNameConConstant;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.microservices.auth.fallback.UserFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Order(Integer.MAX_VALUE)
@FeignClient(name = ServerApplicationNameConConstant.AUTH_APPLICATION_NAME, contextId = "user", fallback = UserFallback.class, configuration = FeignUserMessageConfigruation.class)
public interface IUser {

    /**
     * 根据用户id集合查询用户列表。
     * @author lipeng
     * 2025/8/26
     * @param userIds 用户id集合
     * @return com.kp.framework.entity.bo.KPResult<java.util.List<com.alibaba.fastjson2.JSONObject>>
     */
    @PostMapping(value = "/api/user/ids/list", headers = {"content-type=application/json"})
    KPResult<List<JSONObject>> queryUserListByIds(@RequestBody List<String> userIds);
}
