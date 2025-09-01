package com.kunpeng.framework.microservices.auth.interfaces;

import com.alibaba.fastjson2.JSONObject;
import com.kunpeng.framework.configruation.FeignUserMessageConfigruation;
import com.kunpeng.framework.constant.ServerApplicationNameConConstant;
import com.kunpeng.framework.entity.bo.KPResult;
import com.kunpeng.framework.microservices.auth.fallback.UserFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Order(Integer.MAX_VALUE)
@FeignClient(name = ServerApplicationNameConConstant.AUTH_APPLICATION_NAME, contextId = "user", fallback = UserFallback.class, configuration = FeignUserMessageConfigruation.class)
public interface IUser {

    /**
     * @Author lipeng
     * @Description 根据用户id集合查询用户列表
     * @Date 2025/8/26
     * @param userIds
     * @return com.kunpeng.framework.entity.bo.KPResult<com.alibaba.fastjson2.JSONObject>
     **/
    @PostMapping(value = "/api/user/user/ids/list", headers = {"content-type=application/json"})
    KPResult<JSONObject> queryUserListByIds(@RequestBody List<String> userIds);
}
