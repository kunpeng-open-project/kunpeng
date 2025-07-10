package com.kunpeng.framework.modules.project.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kunpeng.framework.annotation.KPApiJsonlParam;
import com.kunpeng.framework.annotation.KPApiJsonlParamMode;
import com.kunpeng.framework.annotation.verify.KPVerifyNote;
import com.kunpeng.framework.entity.bo.KPResult;
import com.kunpeng.framework.modules.project.po.ProjectPO;
import com.kunpeng.framework.modules.project.po.param.ProjectEditParamPO;
import com.kunpeng.framework.modules.project.po.param.ProjectListParamPO;
import com.kunpeng.framework.modules.project.service.ProjectService;
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
* @Description  项目表相关接口
* @Date 2025-03-14
**/
@RestController
@RequestMapping("/auth/project")
@Api(tags = "项目相关接口", value = "项目相关接口")
@ApiSupport(order = 6)
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    @PreAuthorize("hasPermission('/auth/project/page/list', 'auth:project:page:list')")
    @ApiOperation(value = "查询项目分页列表", notes = "权限 auth:project:page:list")
    @PostMapping("/page/list")
    @KPVerifyNote
    public KPResult<ProjectPO> queryPageList(@RequestBody ProjectListParamPO projectListParamPO){
        return KPResult.list(projectService.queryPageList(projectListParamPO));
    }


    @PreAuthorize("hasPermission('/auth/project/details','auth:project:details')")
    @ApiOperation(value = "根据项目Id查询详情", notes="权限 auth:project:details")
    @PostMapping("/details")
    @KPApiJsonlParam({
        @ApiModelProperty(name = "projectId", value = "项目Id", required = true)
    })
    public KPResult<ProjectPO> queryDetailsById(@RequestBody JSONObject parameter){
        return KPResult.success(projectService.queryDetailsById(parameter));
    }


    @PreAuthorize("hasPermission('/auth/project/save','auth:project:save')")
    @ApiOperation(value = "新增项目", notes="权限 auth:project:save")
    @PostMapping("/save")
    @KPVerifyNote
    @KPApiJsonlParamMode(component = ProjectEditParamPO.class, ignores = "projectId")
    public KPResult<ProjectPO> save(@RequestBody ProjectEditParamPO projectEditParamPO){
        projectService.saveProject(projectEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/project/update','auth:project:update')")
    @ApiOperation(value = "修改项目", notes="权限 auth:project:update")
    @PostMapping("/update")
    @KPVerifyNote
    public KPResult<ProjectPO> update(@RequestBody ProjectEditParamPO projectEditParamPO){
        projectService.updateProject(projectEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/project/batch/remove','auth:project:batch:remove')")
    @ApiOperation(value = "批量删除项目", notes="权限 auth:project:batch:remove")
    @PostMapping("/batch/remove")
    @KPApiJsonlParam({
        @ApiModelProperty(name = "ids", value = "项目Id", required = true, dataType = "list")
    })
    public KPResult batchRemove(@RequestBody List<String> ids){
        return KPResult.success(projectService.batchRemove(ids));
    }


    @PreAuthorize("hasPermission('/auth/project/do/status','auth:project:do:status')")
    @ApiOperation(value = "设置项目状态", notes="权限 auth:project:do:status")
    @PostMapping(value = "/do/status")
    @KPApiJsonlParam({
        @ApiModelProperty(name = "projectId", value = "项目Id", required = true, example = "af0ccec3d65f7571d75a0a4fdf597407"),
    })
    public KPResult doStatus(@RequestBody JSONObject parameter) {
        projectService.doStatus(parameter);
        return KPResult.success();
    }
}
