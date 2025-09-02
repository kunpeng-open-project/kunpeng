package com.kp.framework.modules.role.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonlParam;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.role.po.param.RoleMenuInstallParamPO;
import com.kp.framework.modules.role.service.RoleMenuService;
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
 * <p>
 * 角色菜单关联表 前端控制器
 * </p>
 *
 * @author lipeng
 * @since 2024-04-19
 */
@RestController
@RequestMapping("/auth/role/menu")
@Api(tags = "角色信息相关接口", value = "角色信息相关接口")
@ApiSupport(order = 2)
public class RoleMenuController {

    @Autowired
    private RoleMenuService roleMenuService;

    @PreAuthorize("hasPermission('/auth/role/menu/setting/install','auth:role:menu:setting:install')")
    @ApiOperation(value = "设置菜单权限", notes="权限 auth:role:menu:setting:install")
    @PostMapping(value = "/setting/install")
    @KPVerifyNote
    public KPResult doMenuInstall(@RequestBody RoleMenuInstallParamPO roleMenuInstallParamPO) {
        roleMenuService.doMenuInstall(roleMenuInstallParamPO);
        return KPResult.success();
    }


    @ApiOperation(value = "查询选中的菜单权限")
    @PostMapping(value = "/query/setting/install")
    @KPApiJsonlParam({
        @ApiModelProperty(name = "roleId", value = "角色Id", required = true, example = "e3ae1261c42dcb0e195fb9b9d9298bfe"),
        @ApiModelProperty(name = "projectId", value = "项目Id", required = true, example = "af0ccec3d65f7571d75a0a4fdf597407"),
    })
    public KPResult<List<String>> queryMenuInstall(@RequestBody JSONObject parameter) {
        return KPResult.success(roleMenuService.queryMenuInstall(parameter));
    }

}

