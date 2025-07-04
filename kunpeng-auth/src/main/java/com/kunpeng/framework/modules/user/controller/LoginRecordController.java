package com.kunpeng.framework.modules.user.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kunpeng.framework.annotation.KPApiJsonlParam;
import com.kunpeng.framework.annotation.KPApiJsonlParamMode;
import com.kunpeng.framework.annotation.verify.KPVerifyNote;
import com.kunpeng.framework.entity.bo.KPResult;
import com.kunpeng.framework.modules.user.po.LoginRecordPO;
import com.kunpeng.framework.modules.user.po.param.LoginRecordEditParamPO;
import com.kunpeng.framework.modules.user.po.param.LoginRecordListParamPO;
import com.kunpeng.framework.modules.user.service.LoginRecordService;
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
* @Description  用户登录记录表相关接口
* @Date 2025-06-10
**/
@RestController
@RequestMapping("/auth/login/record")
@Api(tags = "用户登录记录相关接口", value = "用户登录记录相关接口")
@ApiSupport(order = 1)
public class LoginRecordController {

    @Autowired
    private LoginRecordService loginRecordService;


    @PreAuthorize("hasPermission('/auth/login/record/page/list', 'auth:login:record:page:list')")
    @ApiOperation(value = "查询用户登录记录分页列表", notes = "权限 auth:login:record:page:list")
    @PostMapping("/page/list")
    @KPVerifyNote
    public KPResult<LoginRecordPO> queryPageList(@RequestBody LoginRecordListParamPO loginRecordListParamPO){
        return KPResult.list(loginRecordService.queryPageList(loginRecordListParamPO));
    }


    @PreAuthorize("hasPermission('/auth/login/record/details','auth:login:record:details')")
    @ApiOperation(value = "根据登录记录id查询详情", notes="权限 auth:login:record:details")
    @PostMapping("/details")
    @KPApiJsonlParam({
        @ApiModelProperty(name = "alrId", value = "登录记录id", required = true)
    })
    public KPResult<LoginRecordPO> queryDetailsById(@RequestBody JSONObject parameter){
        return KPResult.success(loginRecordService.queryDetailsById(parameter));
    }


    @PreAuthorize("hasPermission('/auth/login/record/save','auth:login:record:save')")
    @ApiOperation(value = "新增用户登录记录", notes="权限 auth:login:record:save")
    @PostMapping("/save")
    @KPVerifyNote
    @KPApiJsonlParamMode(component = LoginRecordEditParamPO.class, ignores = "alrId")
    public KPResult<LoginRecordPO> save(@RequestBody LoginRecordEditParamPO loginRecordEditParamPO){
        loginRecordService.saveLoginRecord(loginRecordEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/login/record/update','auth:login:record:update')")
    @ApiOperation(value = "修改用户登录记录", notes="权限 auth:login:record:update")
    @PostMapping("/update")
    @KPVerifyNote
    public KPResult<LoginRecordPO> update(@RequestBody LoginRecordEditParamPO loginRecordEditParamPO){
        loginRecordService.updateLoginRecord(loginRecordEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/login/record/batch/remove','auth:login:record:batch:remove')")
    @ApiOperation(value = "批量删除用户登录记录", notes="权限 auth:login:record:batch:remove")
    @PostMapping("/batch/remove")
    @KPApiJsonlParam({
        @ApiModelProperty(name = "ids", value = "登录记录id", required = true, dataType = "list")
    })
    public KPResult batchRemove(@RequestBody List<String> ids){
        return KPResult.success(loginRecordService.batchRemove(ids));
    }
}
