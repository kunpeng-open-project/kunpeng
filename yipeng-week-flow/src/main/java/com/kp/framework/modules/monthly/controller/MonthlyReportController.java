package com.kp.framework.modules.monthly.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonlParam;
import com.kp.framework.annotation.KPApiJsonlParamMode;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.monthly.po.MonthlyReportPO;
import com.kp.framework.modules.monthly.po.customer.MonthlyReportDetailsCustomerPO;
import com.kp.framework.modules.monthly.po.customer.MonthlyReportListCustomerPO;
import com.kp.framework.modules.monthly.po.customer.MonthlyReportReviewListCustomerPO;
import com.kp.framework.modules.monthly.po.param.MonthlyReportEditParamPO;
import com.kp.framework.modules.monthly.po.param.MonthlyReportListParamPO;
import com.kp.framework.modules.monthly.service.MonthlyReportService;
import com.kp.framework.modules.week.po.WeeklyPalanPO;
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
import java.util.Map;

/**
 * @Author lipeng
 * @Description 月度计划表相关接口
 * @Date 2025-07-25
 **/
@RestController
@RequestMapping("/week/monthly/report")
@Api(tags = "月度计划相关接口", value = "月度计划相关接口")
@ApiSupport(order = 1)
public class MonthlyReportController {

    @Autowired
    private MonthlyReportService monthlyReportService;


    @PreAuthorize("hasPermission('/week/monthly/report/page/list', 'week:monthly:report:page:list')")
    @ApiOperation(value = "查询月度计划分页列表", notes = "权限 week:monthly:report:page:list")
    @PostMapping("/page/list")
    @KPVerifyNote
    public KPResult<MonthlyReportListCustomerPO> queryPageList(@RequestBody MonthlyReportListParamPO monthlyReportListParamPO) {
        return KPResult.list(monthlyReportService.queryPageList(monthlyReportListParamPO));
    }


    @PreAuthorize("hasPermission('/week/monthly/report/details','week:monthly:report:details')")
    @ApiOperation(value = "根据月度计划Id查询详情", notes = "权限 week:monthly:report:details")
    @PostMapping("/details")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "monthlyId", value = "月度计划Id", required = true)
    })
    public KPResult<MonthlyReportDetailsCustomerPO> queryDetailsById(@RequestBody JSONObject parameter) {
        return KPResult.success(monthlyReportService.queryDetailsById(parameter));
    }


    @PreAuthorize("hasPermission('/week/monthly/report/save','week:monthly:report:save')")
    @ApiOperation(value = "新增月度计划", notes = "权限 week:monthly:report:save")
    @PostMapping("/save")
    @KPVerifyNote
    @KPApiJsonlParamMode(component = MonthlyReportEditParamPO.class, ignores = "monthlyId")
    public KPResult<MonthlyReportPO> save(@RequestBody MonthlyReportEditParamPO monthlyReportEditParamPO) {
        monthlyReportService.saveMonthlyReport(monthlyReportEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/week/monthly/report/update','week:monthly:report:update')")
    @ApiOperation(value = "修改月度计划", notes = "权限 week:monthly:report:update")
    @PostMapping("/update")
    @KPVerifyNote
    public KPResult<MonthlyReportPO> update(@RequestBody MonthlyReportEditParamPO monthlyReportEditParamPO) {
        monthlyReportService.updateMonthlyReport(monthlyReportEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/week/monthly/report/batch/remove','week:monthly:report:batch:remove')")
    @ApiOperation(value = "批量删除月度计划", notes = "权限 week:monthly:report:batch:remove")
    @PostMapping("/batch/remove")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "ids", value = "月度计划Id", required = true, dataType = "list")
    })
    public KPResult batchRemove(@RequestBody List<String> ids) {
        return KPResult.success(monthlyReportService.batchRemove(ids));
    }


    @PreAuthorize("hasPermission('/week/monthly/report/submit/review','week:monthly:report:submit:review')")
    @ApiOperation(value = "提交审核", notes = "权限 week:monthly:report:submit:review")
    @PostMapping("/submit/review")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "ids", value = "月度计划Id", required = true, dataType = "list")
    })
    public KPResult doSubmitReview(@RequestBody List<String> ids) {
        return KPResult.success(monthlyReportService.doSubmitReview(ids));
    }


    @ApiOperation(value = "查询我的月度计划")
    @PostMapping("/my/list")
    @KPApiJsonlParamMode(component = MonthlyReportListParamPO.class, includes = "planTimes")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "planTime", value = "月计划时间 yyyy-mm", required = true)
    })
    public KPResult<List<MonthlyReportPO>> queryMyList(@RequestBody JSONObject parameter) {
        return KPResult.success(monthlyReportService.queryMyList(parameter));
    }


    @PreAuthorize("hasPermission('/week/monthly/report/list/group/status', 'week:monthly:report:list:group:status')")
    @ApiOperation(value = "查询指定月份月计划列表(按状态分组)", notes = "权限 week:monthly:report:list:group:status")
    @PostMapping("/list/group/status")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "planTime", value = "月计划时间 yyyy-mm", required = true)
    })
    public KPResult<Map<Integer, List<MonthlyReportReviewListCustomerPO>>> queryListByStatus(@RequestBody JSONObject parameter) {
        return KPResult.success(monthlyReportService.queryListByStatus(parameter));
    }


    @PreAuthorize("hasPermission('/week/monthly/report/update/move/status','week:monthly:report:update:move:status')")
    @ApiOperation(value = "移动设置月计划状态", notes = "权限 week:monthly:report:update:move:status")
    @PostMapping("/update/move/status")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "monthlyId", value = "月计划id", required = true),
            @ApiModelProperty(name = "status", value = "月计划状态 3未开始 5进行中 6已完成 7逾期 ", required = true)
    })
    public KPResult<WeeklyPalanPO> updateMoveStatus(@RequestBody JSONObject parameter) {
        monthlyReportService.updateMoveStatus(parameter);
        return KPResult.success();
    }
}
