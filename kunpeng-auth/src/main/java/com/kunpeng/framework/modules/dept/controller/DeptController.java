package com.kunpeng.framework.modules.dept.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kunpeng.framework.annotation.KPApiJsonlParam;
import com.kunpeng.framework.annotation.KPApiJsonlParamMode;
import com.kunpeng.framework.annotation.verify.KPVerifyNote;
import com.kunpeng.framework.entity.bo.KPResult;
import com.kunpeng.framework.modules.dept.po.DeptPO;
import com.kunpeng.framework.modules.dept.po.customer.DeptCustomerPO;
import com.kunpeng.framework.modules.dept.po.param.DeptEditParamPO;
import com.kunpeng.framework.modules.dept.po.param.DeptListParamPO;
import com.kunpeng.framework.modules.dept.po.param.DeptSortParamPO;
import com.kunpeng.framework.modules.dept.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* @Author lipeng
* @Description  部门信息表相关接口
* @Date 2025-04-08
**/
@RestController
@RequestMapping("/auth/dept")
@Api(tags = "部门信息相关接口", value = "部门信息相关接口")
@ApiSupport(order = 3)
public class DeptController {

    @Autowired
    private DeptService deptService;


    @PreAuthorize("hasPermission('/auth/dept/list', 'auth:dept:list')")
    @ApiOperation(value = "查询部门信息分页列表", notes = "权限 auth:dept:list")
    @PostMapping("/list")
    @KPVerifyNote
    public KPResult<DeptCustomerPO> queryList(@RequestBody DeptListParamPO deptListParamPO){
        return KPResult.list(deptService.queryList(deptListParamPO));
    }


    @PreAuthorize("hasPermission('/auth/dept/details','auth:dept:details')")
    @ApiOperation(value = "根据部门Id查询详情", notes="权限 auth:dept:details")
    @PostMapping("/details")
    @KPApiJsonlParam({
        @ApiModelProperty(name = "deptId", value = "部门Id", required = true)
    })
    public KPResult<DeptPO> queryDetailsById(@RequestBody JSONObject parameter){
        return KPResult.success(deptService.queryDetailsById(parameter));
    }


    @PreAuthorize("hasPermission('/auth/dept/save','auth:dept:save')")
    @ApiOperation(value = "新增部门信息", notes="权限 auth:dept:save")
    @PostMapping("/save")
    @KPVerifyNote
    @KPApiJsonlParamMode(component = DeptEditParamPO.class, ignores = "deptId")
    public KPResult<DeptPO> save(@RequestBody DeptEditParamPO deptEditParamPO){
        deptService.saveDept(deptEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/dept/update','auth:dept:update')")
    @ApiOperation(value = "修改部门信息", notes="权限 auth:dept:update")
    @PostMapping("/update")
    @KPVerifyNote
    public KPResult<DeptPO> update(@RequestBody DeptEditParamPO deptEditParamPO){
        deptService.updateDept(deptEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/dept/batch/remove','auth:dept:batch:remove')")
    @ApiOperation(value = "批量删除部门信息", notes="权限 auth:dept:batch:remove")
    @PostMapping("/batch/remove")
    @KPApiJsonlParam({
        @ApiModelProperty(name = "ids", value = "部门Id", required = true, dataType = "list")
    })
    public KPResult batchRemove(@RequestBody List<String> ids){
        return KPResult.success(deptService.batchRemove(ids));
    }



    @PreAuthorize("hasPermission('/auth/dept/do/status','auth:dept:do:status')")
    @ApiOperation(value = "设置部门状态", notes = "权限 auth:dept:do:status")
    @PostMapping(value = "/do/status")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "deptId", value = "部门Id", required = true),
    })
    public KPResult doStatus(@RequestBody JSONObject parameter) {
        deptService.doStatus(parameter);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/dept/do/set/sort','auth:dept:do:set:sort')")
    @ApiOperation(value = "设置排序", notes="权限 auth:dept:do:set:sort")
    @PostMapping(value = "/do/set/sort")
    @KPVerifyNote
    public KPResult doSetSort(@RequestBody List<DeptSortParamPO> deptSortParamList) {
        deptService.doSetSort(deptSortParamList);
        return KPResult.success();
    }
}
