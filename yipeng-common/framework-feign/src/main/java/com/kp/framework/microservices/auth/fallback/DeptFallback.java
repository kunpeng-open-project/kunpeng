package com.kp.framework.microservices.auth.fallback;

import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.microservices.auth.interfaces.IDept;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DeptFallback implements IDept {

    @Override
    public KPResult<JSONObject> queryDeptById(JSONObject parameter) {
        log.error("从鉴权系统查询部门信息失败！：{}", parameter);
        return KPResult.fail("【调用鉴权系统接口失败】从鉴权系统查询部门信息失败，请稍后再试");
    }

    @Override
    public KPResult<List<JSONObject>> queryDeptIdList(List<String> deptIds) {
        log.error("从鉴权系统查询部门列表信息失败！：{}", deptIds);
        return KPResult.fail("【调用鉴权系统接口失败】从鉴权系统查询部门列表信息失败，请稍后再试");
    }
}
