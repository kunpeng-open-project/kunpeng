package com.kp.framework.api;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonlParam;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.dept.po.DeptPO;
import com.kp.framework.modules.dept.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dept")
@Api(tags = "API-部门相关接口", value = "API-部门相关接口")
@ApiSupport(order = 0)
public class DeptApiController {

    @Autowired
    private DeptService deptService;



    @ApiOperation(value = "根据部门Id查询部门信息")
    @PostMapping("/query/id")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "deptId", value = "部门Id", required = true)
    })
    public KPResult<DeptPO> queryDeptId(@RequestBody JSONObject parameter) {
        return KPResult.success(deptService.queryDetailsById(parameter));
    }


    @ApiOperation(value = "根据部门id集合查询部门列表")
    @PostMapping("/query/ids/list")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "deptId", value = "部门Id集合", required = true, dataType = "list")
    })
    public KPResult<List<DeptPO>> queryDeptIdList(@RequestBody List<String> deptIds) {
        return KPResult.success(deptService.queryDeptIdList(deptIds));
    }
}
