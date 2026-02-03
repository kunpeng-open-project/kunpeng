package com.kp.framework.modules.monthly.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.monthly.po.customer.MonthlyReportReviewListCustomerPO;
import com.kp.framework.modules.monthly.po.param.MonthlyReportListParamPO;
import com.kp.framework.modules.monthly.po.param.MonthlyReportReviewParamPO;
import com.kp.framework.modules.monthly.service.MonthlyReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 月度计划表相关接口。
 * @author lipeng
 * 2025-07-25
 */
@RestController
@RequestMapping("/week/monthly/review")
@Tag(name = "月度计划审核相关接口")
@ApiSupport(author = "lipeng", order = 12)
public class MonthlyReviewController {

    @Autowired
    private MonthlyReportService monthlyReportService;


    @PreAuthorize("hasPermission('/week/monthly/review/page/list', 'week:monthly:review:page:list')")
    @Operation(summary = "查询月度计划审核分页列表", description = "权限 week:monthly:review:page:list")
    @PostMapping("/page/list")
    @KPVerifyNote
    public KPResult<MonthlyReportReviewListCustomerPO> queryPageList(@RequestBody MonthlyReportListParamPO monthlyReportListParamPO) {
        return monthlyReportService.queryReviewPageList(monthlyReportListParamPO);
    }


    @PreAuthorize("hasPermission('/week/monthly/review/batch/submit','week:monthly:review:batch:submit')")
    @Operation(summary = "批量审核", description = "权限 week:monthly:review:batch:submit")
    @PostMapping("/batch/submit")
    @KPVerifyNote
    public KPResult<String> batchReview(@RequestBody MonthlyReportReviewParamPO monthlyReportReviewParamPO) {
        return KPResult.success(monthlyReportService.batchReview(monthlyReportReviewParamPO));
    }
}
