package com.kp.framework.modules.role.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.role.po.customer.QueryPermissionCustomerPO;
import com.kp.framework.modules.role.po.param.RolePermissionInstallParamPO;
import com.kp.framework.modules.role.service.RolePermissionService;
import com.kp.framework.modules.user.po.customer.QueryUserCustomerPO;
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
 * 角色权限关联表 前端控制器
 * @author lipeng
 * 2024-04-19
 */
@RestController
@RequestMapping("/auth/role/permission")
@Tag(name = "角色信息相关接口")
@ApiSupport(author = "lipeng", order = 25)
public class RolePermissionController {

    @Autowired
    private RolePermissionService rolePermissionService;

    @PreAuthorize("hasPermission('/auth/role/permission/setting/install','auth:role:permission:setting:install')")
    @Operation(summary = "设置数据权限", description = "权限 auth:role:permission:setting:install")
    @PostMapping(value = "/setting/install")
    @KPVerifyNote
    public KPResult<Void> doPermissionInstall(@RequestBody RolePermissionInstallParamPO rolePermissionInstallParamPO) {
        rolePermissionService.doPermissionInstall(rolePermissionInstallParamPO);
        return KPResult.success();
    }


    @Operation(summary = "查询选中的数据权限")
    @PostMapping(value = "/query/setting/install")
    @KPApiJsonParam({
            @KPJsonField(name = "roleId", description = "角色Id", required = true, example = "e3ae1261c42dcb0e195fb9b9d9298bfe"),
            @KPJsonField(name = "projectId", description = "项目Id", required = true, example = "af0ccec3d65f7571d75a0a4fdf597407")
    })
    public KPResult<QueryPermissionCustomerPO> queryPermissionInstall(@RequestBody JSONObject parameter) {
        return KPResult.success(rolePermissionService.queryPermissionInstall(parameter));
    }


    @Operation(summary = "查询选中的用户")
    @PostMapping(value = "/query/setting/user")
    @KPApiJsonParam({
            @KPJsonField(name = "roleId", description = "角色Id", required = true, example = "e3ae1261c42dcb0e195fb9b9d9298bfe"),
            @KPJsonField(name = "projectId", description = "项目Id", required = true, example = "af0ccec3d65f7571d75a0a4fdf597407")
    })
    public KPResult<List<QueryUserCustomerPO>> queryPermissionUser(@RequestBody JSONObject parameter) {
        return KPResult.success(rolePermissionService.queryPermissionUser(parameter));
    }
}

