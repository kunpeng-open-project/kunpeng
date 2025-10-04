package com.kp.framework.modules.monthly.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonlParam;
import com.kp.framework.annotation.KPApiJsonlParamMode;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.monthly.po.customer.MonthlyReportStatisticsCustomerPO;
import com.kp.framework.modules.monthly.po.customer.WeeklyPalanSplitCustomerPO;
import com.kp.framework.modules.monthly.po.param.MonthlyReportListParamPO;
import com.kp.framework.modules.monthly.service.MonthlyStatistcsService;
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
 * @Description 月度计划表相关接口
 * @Date 2025-07-25 15:09:06
 **/
@RestController
@RequestMapping("/week/monthly/statistics")
@Api(tags = "月度计划统计相关接口", value = "月度计划统计相关接口")
@ApiSupport(order = 3)
public class MonthlyStatistcsController {

    @Autowired
    private MonthlyStatistcsService monthlyStatistcsService;


    @ApiOperation(value = "查询月度计划审核统计信息")
    @PostMapping("/number")
    @KPApiJsonlParamMode(component = MonthlyReportListParamPO.class, ignores = "pageNum,pageSize,orderBy")
    @KPVerifyNote
    public KPResult<MonthlyReportStatisticsCustomerPO> queryStatistics(@RequestBody MonthlyReportListParamPO monthlyReportListParamPO) {
        return KPResult.success(monthlyStatistcsService.queryReviewStatistics(monthlyReportListParamPO));
    }


    @ApiOperation(value = "查询本人月计划拆分数")
    @PostMapping("/my/split")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "planTime", value = "月计划时间 yyyy-mm", required = true),
            @ApiModelProperty(name = "monthlyIds", value = "月计划id集合", required = true, dataType = "list")
    })
    public KPResult<List<WeeklyPalanSplitCustomerPO>> queryWeeklyPalanSplit(@RequestBody JSONObject parameter) {
        return KPResult.success(monthlyStatistcsService.queryWeeklyPalanSplit(parameter));
    }


    @ApiOperation(value = "查询月度计划看板统计信息")
    @PostMapping("/board/number")
    @KPApiJsonlParamMode(component = MonthlyReportListParamPO.class, ignores = "pageNum,pageSize,orderBy")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "planTime", value = "月计划时间 yyyy-mm", required = true)
    })
    public KPResult<MonthlyReportStatisticsCustomerPO> queryStatisticsByBoard(@RequestBody JSONObject parameter) {
        return KPResult.success(monthlyStatistcsService.queryStatisticsByBoard(parameter));
    }
}
