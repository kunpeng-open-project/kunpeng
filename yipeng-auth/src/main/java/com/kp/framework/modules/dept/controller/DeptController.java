package com.kp.framework.modules.dept.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.KPApiJsonParamMode;
import com.kp.framework.annotation.KPObjectChangeLogNote;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.constant.ObjectChangeLogOperateType;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.dept.mapper.DeptMapper;
import com.kp.framework.modules.dept.po.DeptPO;
import com.kp.framework.modules.dept.po.customer.DeptCustomerPO;
import com.kp.framework.modules.dept.po.param.DeptEditParamPO;
import com.kp.framework.modules.dept.po.param.DeptListParamPO;
import com.kp.framework.modules.dept.po.param.DeptSortParamPO;
import com.kp.framework.modules.dept.service.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门信息表相关接口。
 * @author lipeng
 * 2025-04-08
 */
@RestController
@RequestMapping("/auth/dept")
@Tag(name = "部门信息相关接口")
@ApiSupport(author = "lipeng", order = 26)
public class DeptController {

    @Autowired
    private DeptService deptService;


    @PreAuthorize("hasPermission('/auth/dept/list', 'auth:dept:list')")
    @Operation(summary = "查询部门信息分页列表", description = "权限 auth:dept:list")
    @PostMapping("/list")
    @KPVerifyNote
    public KPResult<List<DeptCustomerPO>> queryList(@RequestBody DeptListParamPO deptListParamPO) {
        return KPResult.success(deptService.queryList(deptListParamPO));
    }


    @PreAuthorize("hasPermission('/auth/dept/details','auth:dept:details')")
    @Operation(summary = "根据部门Id查询详情", description = "权限 auth:dept:details")
    @PostMapping("/details")
    @KPApiJsonParam({
            @KPJsonField(name = "deptId", description = "部门Id", required = true)
    })
    public KPResult<DeptPO> queryDetailsById(@RequestBody JSONObject parameter) {
        return KPResult.success(deptService.queryDetailsById(parameter));
    }


    @PreAuthorize("hasPermission('/auth/dept/save','auth:dept:save')")
    @Operation(summary = "新增部门信息", description = "权限 auth:dept:save")
    @PostMapping("/save")
    @KPObjectChangeLogNote(parentMapper = DeptMapper.class, identification = "deptId,dept_id", operateType = ObjectChangeLogOperateType.ADD, businessType = "部门信息")
    @KPVerifyNote
    @KPApiJsonParamMode(component = DeptEditParamPO.class, ignores = "deptId")
    public KPResult<DeptPO> save(@RequestBody DeptEditParamPO deptEditParamPO) {
        deptService.saveDept(deptEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/dept/update','auth:dept:update')")
    @Operation(summary = "修改部门信息", description = "权限 auth:dept:update")
    @PostMapping("/update")
    @KPObjectChangeLogNote(parentMapper = DeptMapper.class, identification = "deptId,dept_id", businessType = "部门信息")
    @KPVerifyNote
    public KPResult<DeptPO> update(@RequestBody DeptEditParamPO deptEditParamPO) {
        deptService.updateDept(deptEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/dept/batch/remove','auth:dept:batch:remove')")
    @Operation(summary = "批量删除部门信息", description = "权限 auth:dept:batch:remove")
    @PostMapping("/batch/remove")
    @KPApiJsonParam({
            @KPJsonField(name = "ids", description = "部门Id", required = true, dataType = "array<string>")
    })
    @KPObjectChangeLogNote(parentMapper = DeptMapper.class, identification = "deptId,dept_id", operateType = ObjectChangeLogOperateType.DELETE, businessType = "部门信息")
    public KPResult<String> batchRemove(@RequestBody List<String> ids) {
        return KPResult.success(deptService.batchRemove(ids));
    }


    @PreAuthorize("hasPermission('/auth/dept/do/status','auth:dept:do:status')")
    @Operation(summary = "设置部门状态", description = "权限 auth:dept:do:status")
    @PostMapping(value = "/do/status")
    @KPApiJsonParam({
            @KPJsonField(name = "deptId", description = "部门Id", required = true),
    })
    @KPObjectChangeLogNote(parentMapper = DeptMapper.class, identification = "deptId,dept_id", businessType = "部门信息")
    public KPResult<Void> doStatus(@RequestBody JSONObject parameter) {
        deptService.doStatus(parameter);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/dept/do/set/sort','auth:dept:do:set:sort')")
    @Operation(summary = "设置排序", description = "权限 auth:dept:do:set:sort")
    @PostMapping(value = "/do/set/sort")
    @KPObjectChangeLogNote(parentMapper = DeptMapper.class, identification = "deptId,dept_id", operateType = ObjectChangeLogOperateType.UPDATE_BATCH, businessType = "部门信息")
    @KPVerifyNote
    public KPResult<Void> doSetSort(@RequestBody List<DeptSortParamPO> deptSortParamList) {
        deptService.doSetSort(deptSortParamList);
        return KPResult.success();
    }
}
