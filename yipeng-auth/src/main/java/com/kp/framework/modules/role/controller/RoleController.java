package com.kp.framework.modules.role.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonlParam;
import com.kp.framework.annotation.KPApiJsonlParamMode;
import com.kp.framework.annotation.KPObjectChangeLogListNote;
import com.kp.framework.annotation.KPObjectChangeLogNote;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.constant.ObjectChangeLogOperateType;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.role.mapper.RoleMapper;
import com.kp.framework.modules.role.mapper.RoleProjectRelevanceMapper;
import com.kp.framework.modules.role.po.RolePO;
import com.kp.framework.modules.role.po.customer.RoleDetailsCustomerPO;
import com.kp.framework.modules.role.po.customer.RoleListCustomerPO;
import com.kp.framework.modules.role.po.param.RoleEditParamPO;
import com.kp.framework.modules.role.po.param.RoleListParamPO;
import com.kp.framework.modules.role.service.RoleService;
import com.kp.framework.modules.user.service.UserRoleService;
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
 * @Description 角色信息表相关接口
 * @Date 2025-03-31
 **/
@RestController
@RequestMapping("/auth/role")
@Api(tags = "角色信息相关接口", value = "角色信息相关接口")
@ApiSupport(order = 2)
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;


    @PreAuthorize("hasPermission('/auth/role/page/list', 'auth:role:page:list')")
    @ApiOperation(value = "查询角色信息分页列表", notes = "权限 auth:role:page:list")
    @PostMapping("/page/list")
    @KPVerifyNote
    public KPResult<RoleListCustomerPO> queryPageList(@RequestBody RoleListParamPO roleListParamPO) {
        return KPResult.list(roleService.queryPageList(roleListParamPO));
    }


    @PreAuthorize("hasPermission('/auth/role/details','auth:role:details')")
    @ApiOperation(value = "根据角色Id查询详情", notes = "权限 auth:role:details")
    @PostMapping("/details")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "roleId", value = "角色Id", required = true)
    })
    public KPResult<RoleDetailsCustomerPO> queryDetailsById(@RequestBody JSONObject parameter) {
        return KPResult.success(roleService.queryDetailsById(parameter));
    }


    @PreAuthorize("hasPermission('/auth/role/save','auth:role:save')")
    @ApiOperation(value = "新增角色信息", notes = "权限 auth:role:save")
    @PostMapping("/save")
    @KPObjectChangeLogListNote({
            @KPObjectChangeLogNote(parentMapper = RoleMapper.class, identification = "roleId,role_id", operateType = ObjectChangeLogOperateType.ADD, businessType = "角色信息"),
            @KPObjectChangeLogNote(parentMapper = RoleProjectRelevanceMapper.class, identification = "roleId,role_id", operateType = ObjectChangeLogOperateType.ADD_BATCH, businessType = "角色关联项目信息")
    })
    @KPVerifyNote
    @KPApiJsonlParamMode(component = RoleEditParamPO.class, ignores = "roleId")
    public KPResult<RolePO> save(@RequestBody RoleEditParamPO roleEditParamPO) {
        roleService.saveRole(roleEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/role/update','auth:role:update')")
    @ApiOperation(value = "修改角色信息", notes = "权限 auth:role:update")
    @PostMapping("/update")
    @KPObjectChangeLogListNote({
            @KPObjectChangeLogNote(parentMapper = RoleMapper.class, identification = "roleId,role_id", businessType = "角色信息"),
            @KPObjectChangeLogNote(parentMapper = RoleProjectRelevanceMapper.class, identification = "roleId,role_id", operateType = ObjectChangeLogOperateType.DELETE_ADD, businessType = "角色关联项目信息")
    })
    @KPVerifyNote
    public KPResult<RolePO> update(@RequestBody RoleEditParamPO roleEditParamPO) {
        roleService.updateRole(roleEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/role/batch/remove','auth:role:batch:remove')")
    @ApiOperation(value = "批量删除角色信息", notes = "权限 auth:role:batch:remove")
    @PostMapping("/batch/remove")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "ids", value = "角色Id", required = true, dataType = "list")
    })
    @KPObjectChangeLogNote(parentMapper = RoleMapper.class, identification = "roleId,role_id", operateType = ObjectChangeLogOperateType.DELETE, businessType = "角色信息")
    public KPResult<String> batchRemove(@RequestBody List<String> ids) {
        return KPResult.success(roleService.batchRemove(ids));
    }


    @PreAuthorize("hasPermission('/auth/role/update/status','auth:role:update:status')")
    @ApiOperation(value = "修改角色状态", notes = "权限 auth:role:update:status")
    @PostMapping(value = "/update/status")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "roleId", value = "角色Id", required = true, example = "4c2943e45aa513c079045020b0d1bd8e")
    })
    @KPObjectChangeLogNote(parentMapper = RoleMapper.class, identification = "roleId,role_id", businessType = "角色信息")
    public KPResult updateStatus(@RequestBody JSONObject parameter) {
        roleService.updateStatus(parameter);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/role/add/user','auth:role:add:user')")
    @ApiOperation(value = "设置用户", notes = "权限 auth:role:add:user")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "roleId", value = "角色Id", required = true),
            @ApiModelProperty(name = "userIds", value = "用户id集合", required = true, dataType = "list")
    })
    @PostMapping(value = "/add/user")
    public KPResult roleAddUser(@RequestBody JSONObject parameter) {
        userRoleService.userRoleService(parameter);
        return KPResult.success();
    }
}
