package com.kp.framework.modules.project.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.project.po.param.ProjectMenuInstallParamPO;
import com.kp.framework.modules.project.service.ProjectMenuService;
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
 * 项目菜单关联表相关接口。
 * @author lipeng
 * 2025-05-16
 */
@RestController
@RequestMapping("/auth/project/menu")
@Tag(name = "项目相关接口")
@ApiSupport(author = "lipeng", order = 28)
public class ProjectMenuController {

    @Autowired
    private ProjectMenuService projectMenuService;

    @PreAuthorize("hasPermission('/auth/project/menu/setting/install','auth:project:menu:setting:install')")
    @Operation(summary = "设置权限", description = "权限 auth:project:menu:setting:install")
    @PostMapping(value = "/setting/install")
    @KPVerifyNote
    public KPResult<Void> doMenuInstall(@RequestBody ProjectMenuInstallParamPO projectMenuInstallParamPO) {
        projectMenuService.doMenuInstall(projectMenuInstallParamPO);
        return KPResult.success();
    }


    @Operation(summary = "查询选中的权限")
    @PostMapping(value = "/query/setting/install")
    @KPApiJsonParam({
            @KPJsonField(name = "projectId", description = "项目Id", required = true, example = "af0ccec3d65f7571d75a0a4fdf597407"),
            @KPJsonField(name = "purviewProjectId", description = "权限项目Id", required = true, example = "af0ccec3d65f7571d75a0a4fdf597407")
    })
    public KPResult<List<String>> queryMenuInstall(@RequestBody JSONObject parameter) {
        return KPResult.success(projectMenuService.queryMenuInstall(parameter));
    }

}
