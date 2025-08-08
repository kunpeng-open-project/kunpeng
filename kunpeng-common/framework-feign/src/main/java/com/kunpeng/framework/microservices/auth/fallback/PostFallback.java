package com.kunpeng.framework.microservices.auth.fallback;


import com.alibaba.fastjson2.JSONObject;
import com.kunpeng.framework.entity.bo.KPResult;
import com.kunpeng.framework.microservices.auth.interfaces.IPost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PostFallback implements IPost {
    @Override
    public KPResult<JSONObject> queryPostById(JSONObject parameter) {
        log.error("从鉴权系统查询岗位信息失败！：{}", parameter);
        return KPResult.fail("【调用鉴权系统接口失败】从鉴权系统查询岗位信息失败，请稍后再试");
    }
}
