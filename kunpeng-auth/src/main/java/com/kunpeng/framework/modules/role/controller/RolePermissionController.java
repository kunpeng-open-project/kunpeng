package com.kunpeng.framework.modules.role.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kunpeng.framework.annotation.KPApiJsonlParam;
import com.kunpeng.framework.annotation.verify.KPVerifyNote;
import com.kunpeng.framework.entity.bo.KPResult;
import com.kunpeng.framework.modules.role.po.customer.QueryPermissionCustomerPO;
import com.kunpeng.framework.modules.role.po.param.RolePermissionInstallParamPO;
import com.kunpeng.framework.modules.role.service.RolePermissionService;
import com.kunpeng.framework.modules.user.po.customer.QueryUserCustomerPO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * <p>
 * 角色权限关联表 前端控制器
 * </p>
 *
 * @author lipeng
 * @since 2024-04-19
 */
@RestController
@RequestMapping("/auth/role/permission")
@Api(tags = "角色信息相关接口", value = "角色信息相关接口")
@ApiSupport(order = 2)
public class RolePermissionController {

    @Autowired
    private RolePermissionService rolePermissionService;

    @PreAuthorize("hasPermission('/auth/role/permission/setting/install','auth:role:permission:setting:install')")
    @ApiOperation(value = "设置数据权限", notes="权限 auth:role:permission:setting:install")
    @PostMapping(value = "/setting/install")
    @KPVerifyNote
    public KPResult doPermissionInstall(@RequestBody RolePermissionInstallParamPO rolePermissionInstallParamPO) {
        rolePermissionService.doPermissionInstall(rolePermissionInstallParamPO);
        return KPResult.success();
    }


    @ApiOperation(value = "查询选中的数据权限")
    @PostMapping(value = "/query/setting/install")
    @KPApiJsonlParam({
        @ApiModelProperty(name = "roleId", value = "角色Id", required = true, example = "e3ae1261c42dcb0e195fb9b9d9298bfe"),
        @ApiModelProperty(name = "projectId", value = "项目Id", required = true, example = "af0ccec3d65f7571d75a0a4fdf597407")
    })
    public KPResult<QueryPermissionCustomerPO> queryPermissionInstall(@RequestBody JSONObject parameter) {
        return KPResult.success(rolePermissionService.queryPermissionInstall(parameter));
    }


    @ApiOperation(value = "查询选中的用户")
    @PostMapping(value = "/query/setting/user")
    @KPApiJsonlParam({
        @ApiModelProperty(name = "roleId", value = "角色Id", required = true, example = "e3ae1261c42dcb0e195fb9b9d9298bfe"),
        @ApiModelProperty(name = "projectId", value = "项目Id", required = true, example = "af0ccec3d65f7571d75a0a4fdf597407")
    })
    public KPResult<List<QueryUserCustomerPO>> queryPermissionUser(@RequestBody JSONObject parameter) {
        return KPResult.success(rolePermissionService.queryPermissionUser(parameter));
    }
}

