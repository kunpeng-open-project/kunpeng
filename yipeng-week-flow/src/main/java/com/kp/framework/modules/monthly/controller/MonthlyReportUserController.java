package com.kp.framework.modules.monthly.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.modules.monthly.service.MonthlyReportUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @Author lipeng
* @Description  月度计划责任人信息表相关接口
* @Date 2025-07-25 15:09:06
**/
@RestController
@RequestMapping("/week/monthly/report/user")
@Api(tags = "月度计划责任人信息相关接口", value = "月度计划责任人信息相关接口")
@ApiSupport(order = 1)
public class MonthlyReportUserController {

    @Autowired
    private MonthlyReportUserService monthlyReportUserService;


//    @PreAuthorize("hasPermission('/week/monthly/report/user/page/list', 'week:monthly:report:user:page:list')")
//    @ApiOperation(value = "查询月度计划责任人信息分页列表", notes = "权限 week:monthly:report:user:page:list")
//    @PostMapping("/page/list")
//    @KPVerifyNote
//    public KPResult<MonthlyReportUserPO> queryPageList(@RequestBody MonthlyReportUserListParamPO monthlyReportUserListParamPO){
//        return KPResult.list(monthlyReportUserService.queryPageList(monthlyReportUserListParamPO));
//    }
//
//
//    @PreAuthorize("hasPermission('/week/monthly/report/user/details','week:monthly:report:user:details')")
//    @ApiOperation(value = "根据月度计划责任人Id查询详情", notes="权限 week:monthly:report:user:details")
//    @PostMapping("/details")
//    @KPApiJsonlParam({
//        @ApiModelProperty(name = "mruId", value = "月度计划责任人Id", required = true)
//    })
//    public KPResult<MonthlyReportUserPO> queryDetailsById(@RequestBody JSONObject parameter){
//        return KPResult.success(monthlyReportUserService.queryDetailsById(parameter));
//    }
//
//
//    @PreAuthorize("hasPermission('/week/monthly/report/user/save','week:monthly:report:user:save')")
//    @ApiOperation(value = "新增月度计划责任人信息", notes="权限 week:monthly:report:user:save")
//    @PostMapping("/save")
//    @KPVerifyNote
//    @KPApiJsonlParamMode(component = MonthlyReportUserEditParamPO.class, ignores = "mruId")
//    public KPResult<MonthlyReportUserPO> save(@RequestBody MonthlyReportUserEditParamPO monthlyReportUserEditParamPO){
//        monthlyReportUserService.saveMonthlyReportUser(monthlyReportUserEditParamPO);
//        return KPResult.success();
//    }
//
//
//    @PreAuthorize("hasPermission('/week/monthly/report/user/update','week:monthly:report:user:update')")
//    @ApiOperation(value = "修改月度计划责任人信息", notes="权限 week:monthly:report:user:update")
//    @PostMapping("/update")
//    @KPVerifyNote
//    public KPResult<MonthlyReportUserPO> update(@RequestBody MonthlyReportUserEditParamPO monthlyReportUserEditParamPO){
//        monthlyReportUserService.updateMonthlyReportUser(monthlyReportUserEditParamPO);
//        return KPResult.success();
//    }
//
//
//    @PreAuthorize("hasPermission('/week/monthly/report/user/batch/remove','week:monthly:report:user:batch:remove')")
//    @ApiOperation(value = "批量删除月度计划责任人信息", notes="权限 week:monthly:report:user:batch:remove")
//    @PostMapping("/batch/remove")
//    @KPApiJsonlParam({
//        @ApiModelProperty(name = "ids", value = "月度计划责任人Id", required = true, dataType = "list")
//    })
//    public KPResult batchRemove(@RequestBody List<String> ids){
//        return KPResult.success(monthlyReportUserService.batchRemove(ids));
//    }
}
