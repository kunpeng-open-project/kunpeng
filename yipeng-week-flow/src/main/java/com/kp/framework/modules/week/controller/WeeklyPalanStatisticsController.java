package com.kp.framework.modules.week.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.week.po.customer.WeeklyPalanCustomerCustomerPO;
import com.kp.framework.modules.week.po.customer.WeellyTaskSummaryCustomerPO;
import com.kp.framework.modules.week.service.WeeklyPalanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "周计划统计相关接口")
@ApiSupport(author = "lipeng", order = 31)
public class WeeklyPalanStatisticsController {

    @Autowired
    private WeeklyPalanService weeklyPalanService;

    @Operation(summary = "查询本人月计划拆分完成度")
    @PostMapping("/my/completion")
    @KPApiJsonParam({
            @KPJsonField(name = "planTime", description = "月计划时间 yyyy-mm", required = true)
    })
    public KPResult<List<WeeklyPalanCustomerCustomerPO>> queryWeeklyPalanCustomer(@RequestBody JSONObject parameter) {
        return KPResult.success(weeklyPalanService.queryWeeklyPalanCustomer(parameter));
    }

    @Operation(summary = "查询本人周计划统计数")
    @PostMapping("/my/number")
    @KPApiJsonParam({
            @KPJsonField(name = "planTime", description = "月计划时间 yyyy-mm", required = true)
    })
    public KPResult<WeellyTaskSummaryCustomerPO> queryWeeklyNumber(@RequestBody JSONObject parameter) {
        return KPResult.success(weeklyPalanService.queryWeeklyNumber(parameter));
    }
}
