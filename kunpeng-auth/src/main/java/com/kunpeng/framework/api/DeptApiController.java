package com.kunpeng.framework.api;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kunpeng.framework.annotation.KPApiJsonlParam;
import com.kunpeng.framework.entity.bo.KPResult;
import com.kunpeng.framework.modules.dept.po.DeptPO;
import com.kunpeng.framework.modules.dept.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dept")
@Api(tags = "API-部门相关接口", value = "API-部门相关接口")
@ApiSupport(order = 0)
public class DeptApiController {

    @Autowired
    private DeptService deptService;


    @ApiOperation(value = "根据部门Id查询部门信息")
    @PostMapping("/query/dept/id")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "deptId", value = "部门Id", required = true)
    })
    public KPResult<DeptPO> queryPostId(@RequestBody JSONObject parameter) {
        return KPResult.success(deptService.queryDetailsById(parameter));
    }
}
