package com.kp.framework.modules.project.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.KPApiJsonParamMode;
import com.kp.framework.annotation.KPObjectChangeLogNote;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.constant.ObjectChangeLogOperateType;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.project.mapper.ProjectMapper;
import com.kp.framework.modules.project.po.ProjectPO;
import com.kp.framework.modules.project.po.param.ProjectEditParamPO;
import com.kp.framework.modules.project.po.param.ProjectListParamPO;
import com.kp.framework.modules.project.service.ProjectService;
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
 * 项目表相关接口。
 * @author lipeng
 * 2025-03-14
 */
@RestController
@RequestMapping("/auth/project")
@Tag(name = "项目相关接口")
@ApiSupport(author = "lipeng", order = 28)
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    @PreAuthorize("hasPermission('/auth/project/page/list', 'auth:project:page:list')")
    @Operation(summary = "查询项目分页列表", description = "权限 auth:project:page:list")
    @PostMapping("/page/list")
    @KPVerifyNote
    public KPResult<ProjectPO> queryPageList(@RequestBody ProjectListParamPO projectListParamPO) {
        return projectService.queryPageList(projectListParamPO);
    }


    @PreAuthorize("hasPermission('/auth/project/details','auth:project:details')")
    @Operation(summary = "根据项目Id查询详情", description = "权限 auth:project:details")
    @PostMapping("/details")
    @KPApiJsonParam({
            @KPJsonField(name = "projectId", description = "项目Id", required = true)
    })
    public KPResult<ProjectPO> queryDetailsById(@RequestBody JSONObject parameter) {
        return KPResult.success(projectService.queryDetailsById(parameter));
    }


    @PreAuthorize("hasPermission('/auth/project/save','auth:project:save')")
    @Operation(summary = "新增项目", description = "权限 auth:project:save")
    @PostMapping("/save")
    @KPObjectChangeLogNote(parentMapper = ProjectMapper.class, identification = "projectId,project_id", operateType = ObjectChangeLogOperateType.ADD, businessType = "项目信息")
    @KPVerifyNote
    @KPApiJsonParamMode(component = ProjectEditParamPO.class, ignores = "projectId")
    public KPResult<ProjectPO> save(@RequestBody ProjectEditParamPO projectEditParamPO) {
        projectService.saveProject(projectEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/project/update','auth:project:update')")
    @Operation(summary = "修改项目", description = "权限 auth:project:update")
    @PostMapping("/update")
    @KPObjectChangeLogNote(parentMapper = ProjectMapper.class, identification = "projectId,project_id", businessType = "项目信息")
    @KPVerifyNote
    public KPResult<ProjectPO> update(@RequestBody ProjectEditParamPO projectEditParamPO) {
        projectService.updateProject(projectEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/project/batch/remove','auth:project:batch:remove')")
    @Operation(summary = "批量删除项目", description = "权限 auth:project:batch:remove")
    @PostMapping("/batch/remove")
    @KPApiJsonParam({
            @KPJsonField(name = "ids", description = "项目Id", required = true, dataType = "array<string>")
    })
    @KPObjectChangeLogNote(parentMapper = ProjectMapper.class, identification = "projectId,project_id", operateType = ObjectChangeLogOperateType.DELETE, businessType = "项目信息")
    public KPResult<String> batchRemove(@RequestBody List<String> ids) {
        return KPResult.success(projectService.batchRemove(ids));
    }


    @PreAuthorize("hasPermission('/auth/project/do/status','auth:project:do:status')")
    @Operation(summary = "设置项目状态", description = "权限 auth:project:do:status")
    @PostMapping(value = "/do/status")
    @KPApiJsonParam({
            @KPJsonField(name = "projectId", description = "项目Id", required = true, example = "af0ccec3d65f7571d75a0a4fdf597407"),
    })
    @KPObjectChangeLogNote(parentMapper = ProjectMapper.class, identification = "projectId,project_id", businessType = "项目信息")
    public KPResult<Void> doStatus(@RequestBody JSONObject parameter) {
        projectService.doStatus(parameter);
        return KPResult.success();
    }
}
