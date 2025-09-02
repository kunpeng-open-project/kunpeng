package com.kp.framework.modules.welcome.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonlParam;
import com.kp.framework.annotation.KPExcludeInterfaceJournal;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.welcome.po.customer.InterfaceCallStatisticsCustomerPO;
import com.kp.framework.modules.welcome.po.customer.LoginNumberCustomerPO;
import com.kp.framework.modules.welcome.po.customer.LoginRecordCustomerPO;
import com.kp.framework.modules.welcome.po.customer.LoginRecordStatisticsCustomerPO;
import com.kp.framework.modules.welcome.service.WelcomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @Author lipeng
 * @Description 开发首页相关接口
 * @Date 2024/8/5
 * @return
 **/
@RestController
@RequestMapping("/auth/welcome")
@Api(tags = "首页相关接口", value = "首页相关接口")
@ApiSupport(order = 999)
public class WelcomeController {

    @Autowired
    private WelcomeService welcomeService;



    @ApiOperation(value = "查询首页用户登录数")
    @KPExcludeInterfaceJournal
    @PostMapping("/login/number")
    public KPResult<List<LoginNumberCustomerPO>> queryLoginNumber(@RequestBody List<String> projectCodes){
        return KPResult.success(welcomeService.queryLoginNumber(projectCodes));
    }


    @ApiOperation(value = "查询首页登录记录")
    @KPExcludeInterfaceJournal
    @KPApiJsonlParam({
        @ApiModelProperty(name = "projectCode", value = "项目code"),
    })
    @PostMapping("/login/record")
    public KPResult<List<LoginRecordCustomerPO>> queryLoginRecord(@RequestBody JSONObject parameter){
        return KPResult.success(welcomeService.queryLoginRecord(parameter));
    }


    @ApiOperation(value = "查询首页用户登录次数统计")
    @KPExcludeInterfaceJournal
    @KPApiJsonlParam({
        @ApiModelProperty(name = "projectCode", value = "项目code"),
    })
    @PostMapping("/login/record/statistics")
    public KPResult<List<LoginRecordStatisticsCustomerPO>> queryLoginRecordStatistics(@RequestBody JSONObject parameter){
        return KPResult.success(welcomeService.queryLoginRecordStatistics(parameter));
    }


    @ApiOperation(value = "查询首页接口调用次数统计")
    @KPExcludeInterfaceJournal
    @KPApiJsonlParam({
        @ApiModelProperty(name = "projectName", value = "项目名称(项目code)"),
    })
    @PostMapping("/interface/call/statistics")
    public KPResult<List<InterfaceCallStatisticsCustomerPO>> queryInterfaceCallStatistics(@RequestBody JSONObject parameter){
        return KPResult.success(welcomeService.queryInterfaceCallStatistics(parameter));
    }

}
