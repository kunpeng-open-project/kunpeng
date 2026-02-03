package com.kp.framework.modules.user.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.user.po.LoginRecordPO;
import com.kp.framework.modules.user.po.param.LoginRecordListParamPO;
import com.kp.framework.modules.user.service.LoginRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录记录表相关接口。
 * @author lipeng
 * 2025-06-10
 */
@RestController
@RequestMapping("/auth/login/record")
@Tag(name = "用户登录记录相关接口")
@ApiSupport(author = "lipeng", order = 21)
public class LoginRecordController {

    @Autowired
    private LoginRecordService loginRecordService;


    @PreAuthorize("hasPermission('/auth/login/record/page/list', 'auth:login:record:page:list')")
    @Operation(summary = "查询用户登录记录分页列表", description = "权限 auth:login:record:page:list")
    @PostMapping("/page/list")
    @KPVerifyNote
    public KPResult<LoginRecordPO> queryPageList(@RequestBody LoginRecordListParamPO loginRecordListParamPO) {
        return loginRecordService.queryPageList(loginRecordListParamPO);
    }


    @PreAuthorize("hasPermission('/auth/login/record/details','auth:login:record:details')")
    @Operation(summary = "根据登录记录id查询详情", description = "权限 auth:login:record:details")
    @PostMapping("/details")
    @KPApiJsonParam({
            @KPJsonField(name = "alrId", description = "登录记录id", required = true)
    })
    public KPResult<LoginRecordPO> queryDetailsById(@RequestBody JSONObject parameter) {
        return KPResult.success(loginRecordService.queryDetailsById(parameter));
    }

    @Operation(summary = "查询本人登录记录列表")
    @PostMapping(value = "/oneself/list")
    @KPApiJsonParam({
            @KPJsonField(name = "projectCode", description = "项目编号", required = true, example = "XM001"),
            @KPJsonField(name = "pageNum", description = "当前页", required = true, example = "1"),
            @KPJsonField(name = "pageSize", description = "条数", required = true, example = "10"),
            @KPJsonField(name = "orderBy", description = "排序规则 如 id desc name asc")
    })
    public KPResult<LoginRecordPO> queryOneselfList(@RequestBody JSONObject parameter) {
        return loginRecordService.queryOneselfList(parameter);
    }
}
