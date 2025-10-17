package com.kp.framework.modules.monthly.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.monthly.po.customer.MonthlyReportReviewListCustomerPO;
import com.kp.framework.modules.monthly.po.param.MonthlyReportListParamPO;
import com.kp.framework.modules.monthly.po.param.MonthlyReportReviewParamPO;
import com.kp.framework.modules.monthly.service.MonthlyReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lipeng
 * @Description 月度计划表相关接口
 * @Date 2025-07-25
 **/
@RestController
@RequestMapping("/week/monthly/review")
@Api(tags = "月度计划审核相关接口", value = "月度计划审核相关接口")
@ApiSupport(order = 2)
public class MonthlyReviewController {

    @Autowired
    private MonthlyReportService monthlyReportService;


    @PreAuthorize("hasPermission('/week/monthly/review/page/list', 'week:monthly:review:page:list')")
    @ApiOperation(value = "查询月度计划审核分页列表", notes = "权限 week:monthly:review:page:list")
    @PostMapping("/page/list")
    @KPVerifyNote
    public KPResult<MonthlyReportReviewListCustomerPO> queryPageList(@RequestBody MonthlyReportListParamPO monthlyReportListParamPO) {
        return KPResult.list(monthlyReportService.queryReviewPageList(monthlyReportListParamPO));
    }


    @PreAuthorize("hasPermission('/week/monthly/review/batch/submit','week:monthly:review:batch:submit')")
    @ApiOperation(value = "批量审核", notes = "权限 week:monthly:review:batch:submit")
    @PostMapping("/batch/submit")
    @KPVerifyNote
    public KPResult batchReview(@RequestBody MonthlyReportReviewParamPO monthlyReportReviewParamPO) {
        return KPResult.success(monthlyReportService.batchReview(monthlyReportReviewParamPO));
    }
}
