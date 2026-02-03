package com.kp.framework.api;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.dept.po.DeptPO;
import com.kp.framework.modules.dept.service.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dept")
@Tag(name = "API-部门相关接口")
@ApiSupport(author = "lipeng", order = 10)
public class DeptApiController {

    @Autowired
    private DeptService deptService;


    @Operation(summary = "根据部门Id查询部门信息")
    @PostMapping("/query/id")
    @KPApiJsonParam({
            @KPJsonField(name = "deptId", description = "部门Id", required = true)
    })
    public KPResult<DeptPO> queryDeptId(@RequestBody JSONObject parameter) {
        return KPResult.success(deptService.queryDetailsById(parameter));
    }


    @Operation(summary = "根据部门id集合查询部门列表")
    @PostMapping("/query/ids/list")
    @KPApiJsonParam({
            @KPJsonField(name = "deptId", description = "部门Id集合", required = true, dataType = "array<String>")
    })
    public KPResult<List<DeptPO>> queryDeptIdList(@RequestBody List<String> deptIds) {
        return KPResult.success(deptService.queryDeptIdList(deptIds));
    }
}
