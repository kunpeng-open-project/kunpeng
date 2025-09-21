package com.kp.framework.microservices.auth.fallback;


import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.microservices.auth.interfaces.IUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class UserFallback implements IUser {

    @Override
    public KPResult<List<JSONObject>> queryUserListByIds(List<String> userIds) {
        log.error("从鉴权系统查询用户列表信息失败！：{}", userIds);
        return KPResult.fail("【调用鉴权系统接口失败】从鉴权系统查询用户列表信息失败，请稍后再试");
    }
}
