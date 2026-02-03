package com.kp.framework.modules.welcome.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.KPExcludeInterfaceJournal;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.welcome.po.customer.InterfaceCallStatisticsCustomerPO;
import com.kp.framework.modules.welcome.po.customer.LoginNumberCustomerPO;
import com.kp.framework.modules.welcome.po.customer.LoginRecordCustomerPO;
import com.kp.framework.modules.welcome.po.customer.LoginRecordStatisticsCustomerPO;
import com.kp.framework.modules.welcome.service.WelcomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 开发首页相关接口。
 * @author lipeng
 * 2024/8/5
 */
@RestController
@RequestMapping("/auth/welcome")
@Tag(name = "首页相关接口")
@ApiSupport(author = "lipeng", order = 40)
public class WelcomeController {

    @Autowired
    private WelcomeService welcomeService;


    @Operation(summary = "查询首页用户登录数")
    @KPExcludeInterfaceJournal
    @PostMapping("/login/number")
    public KPResult<List<LoginNumberCustomerPO>> queryLoginNumber(@RequestBody List<String> projectCodes) {
        return KPResult.success(welcomeService.queryLoginNumber(projectCodes));
    }


    @Operation(summary = "查询首页登录记录")
    @KPExcludeInterfaceJournal
    @KPApiJsonParam({
            @KPJsonField(name = "projectCode", description = "项目code"),
    })
    @PostMapping("/login/record")
    public KPResult<List<LoginRecordCustomerPO>> queryLoginRecord(@RequestBody JSONObject parameter) {
        return KPResult.success(welcomeService.queryLoginRecord(parameter));
    }


    @Operation(summary = "查询首页用户登录次数统计")
    @KPExcludeInterfaceJournal
    @KPApiJsonParam({
            @KPJsonField(name = "projectCode", description = "项目code"),
    })
    @PostMapping("/login/record/statistics")
    public KPResult<List<LoginRecordStatisticsCustomerPO>> queryLoginRecordStatistics(@RequestBody JSONObject parameter) {
        return KPResult.success(welcomeService.queryLoginRecordStatistics(parameter));
    }


    @Operation(summary = "查询首页接口调用次数统计")
    @KPExcludeInterfaceJournal
    @KPApiJsonParam({
            @KPJsonField(name = "projectName", description = "项目名称(项目code)"),
    })
    @PostMapping("/interface/call/statistics")
    public KPResult<List<InterfaceCallStatisticsCustomerPO>> queryInterfaceCallStatistics(@RequestBody JSONObject parameter) {
        return KPResult.success(welcomeService.queryInterfaceCallStatistics(parameter));
    }
}
