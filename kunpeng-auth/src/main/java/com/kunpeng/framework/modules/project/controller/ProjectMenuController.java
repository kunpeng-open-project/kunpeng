package com.kunpeng.framework.modules.project.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kunpeng.framework.annotation.KPApiJsonlParam;
import com.kunpeng.framework.annotation.verify.KPVerifyNote;
import com.kunpeng.framework.entity.bo.KPResult;
import com.kunpeng.framework.modules.project.po.param.ProjectMenuInstallParamPO;
import com.kunpeng.framework.modules.project.service.ProjectMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* @Author lipeng
* @Description  项目菜单关联表相关接口
* @Date 2025-05-16 10:07:47
**/
@RestController
@RequestMapping("/auth/project/menu")
@Api(tags = "项目相关接口", value = "项目相关接口")
@ApiSupport(order = 6)
public class ProjectMenuController {

    @Autowired
    private ProjectMenuService projectMenuService;

    @PreAuthorize("hasPermission('/auth/project/menu/setting/install','auth:project:menu:setting:install')")
    @ApiOperation(value = "设置权限", notes="权限 auth:project:menu:setting:install")
    @PostMapping(value = "/setting/install")
    @KPVerifyNote
    @ResponseBody
    public KPResult doMenuInstall(@RequestBody ProjectMenuInstallParamPO projectMenuInstallParamPO) {
        projectMenuService.doMenuInstall(projectMenuInstallParamPO);
        return KPResult.success();
    }


    @ApiOperation(value = "查询选中的权限")
    @PostMapping(value = "/query/setting/install")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "projectId", value = "项目Id", required = true, example = "af0ccec3d65f7571d75a0a4fdf597407"),
            @ApiModelProperty(name = "purviewProjectId", value = "权限项目Id", required = true, example = "af0ccec3d65f7571d75a0a4fdf597407")
    })
    @ResponseBody
    public KPResult<List<String>> queryMenuInstall(@RequestBody JSONObject parameter) {
        return KPResult.success(projectMenuService.queryMenuInstall(parameter));
    }

}
