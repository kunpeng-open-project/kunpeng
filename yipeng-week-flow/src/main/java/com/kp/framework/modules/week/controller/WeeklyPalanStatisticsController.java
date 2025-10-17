package com.kp.framework.modules.week.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonlParam;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.week.po.customer.WeeklyPalanCustomerCustomerPO;
import com.kp.framework.modules.week.po.customer.WeellyTaskSummaryCustomerPO;
import com.kp.framework.modules.week.service.WeeklyPalanService;
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
 * @Description 周计划表相关接口
 * @Date 2025-09-20
 **/
@RestController
@RequestMapping("/week/weekly/palan/statistics")
@Api(tags = "周计划统计相关接口", value = "周计划统计相关接口")
@ApiSupport(order = 5)
public class WeeklyPalanStatisticsController {

    @Autowired
    private WeeklyPalanService weeklyPalanService;

    @ApiOperation(value = "查询本人月计划拆分完成度")
    @PostMapping("/my/completion")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "planTime", value = "月计划时间 yyyy-mm", required = true)
    })
    public KPResult<List<WeeklyPalanCustomerCustomerPO>> queryWeeklyPalanCustomer(@RequestBody JSONObject parameter) {
        return KPResult.success(weeklyPalanService.queryWeeklyPalanCustomer(parameter));
    }

    @ApiOperation(value = "查询本人周计划统计数")
    @PostMapping("/my/number")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "planTime", value = "月计划时间 yyyy-mm", required = true)
    })
    public KPResult<WeellyTaskSummaryCustomerPO> queryWeeklyNumber(@RequestBody JSONObject parameter) {
        return KPResult.success(weeklyPalanService.queryWeeklyNumber(parameter));
    }
}
