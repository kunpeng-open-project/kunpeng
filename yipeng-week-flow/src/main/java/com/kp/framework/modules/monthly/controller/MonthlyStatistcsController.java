package com.kp.framework.modules.monthly.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.KPApiJsonParamMode;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.monthly.po.customer.MonthlyReportStatisticsCustomerPO;
import com.kp.framework.modules.monthly.po.customer.WeeklyPalanSplitCustomerPO;
import com.kp.framework.modules.monthly.po.param.MonthlyReportListParamPO;
import com.kp.framework.modules.monthly.service.MonthlyStatistcsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 月度计划表相关接口。
 * @author lipeng
 * 2025-07-25
 */
@RestController
@RequestMapping("/week/monthly/statistics")
@Tag(name = "月度计划统计相关接口")
@ApiSupport(author = "lipeng", order = 13)
public class MonthlyStatistcsController {

    @Autowired
    private MonthlyStatistcsService monthlyStatistcsService;


    @Operation(summary = "查询月度计划审核统计信息")
    @PostMapping("/number")
    @KPApiJsonParamMode(component = MonthlyReportListParamPO.class, ignores = "pageNum,pageSize,orderBy")
    @KPVerifyNote
    public KPResult<MonthlyReportStatisticsCustomerPO> queryStatistics(@RequestBody MonthlyReportListParamPO monthlyReportListParamPO) {
        return KPResult.success(monthlyStatistcsService.queryReviewStatistics(monthlyReportListParamPO));
    }


    @Operation(summary = "查询本人月计划拆分数")
    @PostMapping("/my/split")
    @KPApiJsonParam({
            @KPJsonField(name = "planTime", description = "月计划时间 yyyy-mm", required = true),
            @KPJsonField(name = "monthlyIds", description = "月计划id集合", required = true, dataType = "array<string>")
    })
    public KPResult<List<WeeklyPalanSplitCustomerPO>> queryWeeklyPalanSplit(@RequestBody JSONObject parameter) {
        return KPResult.success(monthlyStatistcsService.queryWeeklyPalanSplit(parameter));
    }


    @Operation(summary = "查询月度计划看板统计信息")
    @PostMapping("/board/number")
    @KPApiJsonParam({
            @KPJsonField(name = "planTime", description = "月计划时间 yyyy-mm", required = true)
    })
    public KPResult<MonthlyReportStatisticsCustomerPO> queryStatisticsByBoard(@RequestBody JSONObject parameter) {
        return KPResult.success(monthlyStatistcsService.queryStatisticsByBoard(parameter));
    }
}
