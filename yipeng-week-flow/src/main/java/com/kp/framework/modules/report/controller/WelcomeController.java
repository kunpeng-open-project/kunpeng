package com.kp.framework.modules.report.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.monthly.po.customer.WeeklyPalanSplitCustomerPO;
import com.kp.framework.modules.report.po.customer.StatisticalCustomerPO;
import com.kp.framework.modules.report.service.WelcomeService;
import com.kp.framework.modules.week.po.customer.WeeklyPalanCustomerCustomerPO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/week/welcom")
@Api(tags = "首页相关接口", value = "首页相关接口")
@ApiSupport(order = 6)
public class WelcomeController {

    @Autowired
    private WelcomeService welcomeService;

    @ApiOperation(value = "查询首页看板统计信息")
    @PostMapping("/board/number")
    public KPResult<StatisticalCustomerPO> queryStatistical() {
        return KPResult.success(welcomeService.queryStatistical());
    }


    @ApiOperation(value = "查询首页月计划拆分完成度")
    @PostMapping("/monthly/completion")
    public KPResult<List<WeeklyPalanCustomerCustomerPO>> queryMonthlyCmpletion() {
        return KPResult.success(welcomeService.queryMonthlyCmpletion());
    }

    @ApiOperation(value = "查询首页月计划拆分数")
    @PostMapping("/monthly/split")
    public KPResult<List<WeeklyPalanSplitCustomerPO>> queryMonthlySplit() {
        return KPResult.success(welcomeService.queryMonthlySplit());
    }
}
