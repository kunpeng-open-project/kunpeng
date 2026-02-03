package com.kp.framework.modules.week.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.KPApiJsonParamMode;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.week.po.WeeklyPalanPO;
import com.kp.framework.modules.week.po.customer.WeeklyPalanListCustomerPO;
import com.kp.framework.modules.week.po.param.WeeklyPalanEditParamPO;
import com.kp.framework.modules.week.po.param.WeeklyPalanListParamPO;
import com.kp.framework.modules.week.service.WeeklyPalanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 周计划表相关接口。
 * @author lipeng
 * 2025-09-20
 */
@RestController
@RequestMapping("/week/weekly/palan")
@Tag(name = "周计划相关接口")
@ApiSupport(author = "lipeng", order = 30)
public class WeeklyPalanController {

    @Autowired
    private WeeklyPalanService weeklyPalanService;


//    @PreAuthorize("hasPermission('/week/weekly/palan/page/list', 'week:weekly:palan:page:list')")
//    @Operation(summary = "查询周计划分页列表", notes = "权限 week:weekly:palan:page:list")
//    @PostMapping("/page/list")
//    @KPVerifyNote
//    public KPResult<WeeklyPalanPO> queryPageList(@RequestBody WeeklyPalanListParamPO weeklyPalanListParamPO){
//        return KPResult.list(weeklyPalanService.queryPageList(weeklyPalanListParamPO));
//    }

    @PreAuthorize("hasPermission('/week/weekly/palan/my/list', 'week:weekly:palan:my:list')")
    @Operation(summary = "查询我的周计划列表(按周分组)", description = "权限 week:weekly:palan:my:list")
    @PostMapping("/my/list")
    @KPApiJsonParam({
            @KPJsonField(name = "planTime", description = "月计划时间 yyyy-mm", required = true)
    })
    public KPResult<Map<String, List<WeeklyPalanListCustomerPO>>> queryMyList(@RequestBody JSONObject parameter) {
        return KPResult.success(weeklyPalanService.queryMyList(parameter));
    }


    @PreAuthorize("hasPermission('/week/weekly/palan/my/list/status', 'week:weekly:palan:my:list:status')")
    @Operation(summary = "查询我的周计划列表(按状态分组)", description = "权限 week:weekly:palan:my:list:status")
    @PostMapping("/my/list/status")
    @KPApiJsonParam({
            @KPJsonField(name = "planTime", description = "月计划时间 yyyy-mm", required = true)
    })
    public KPResult<Map<Integer, List<WeeklyPalanListCustomerPO>>> queryMyListByStatus(@RequestBody JSONObject parameter) {
        return KPResult.success(weeklyPalanService.queryMyListByStatus(parameter));
    }


    @PreAuthorize("hasPermission('/week/weekly/palan/details','week:weekly:palan:details')")
    @Operation(summary = "根据周计划id查询详情", description = "权限 week:weekly:palan:details")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = WeeklyPalanPO.class)))
    @PostMapping("/details")
    @KPApiJsonParam({
            @KPJsonField(name = "weeklyId", description = "周计划id", required = true)
    })
    public KPResult<JSONObject> queryDetailsById(@RequestBody JSONObject parameter) {
        return KPResult.success(weeklyPalanService.queryDetailsById(parameter));
    }


    @PreAuthorize("hasPermission('/week/weekly/palan/save','week:weekly:palan:save')")
    @Operation(summary = "新增周计划", description = "权限 week:weekly:palan:save")
    @PostMapping("/save")
    @KPVerifyNote
    @KPApiJsonParamMode(component = WeeklyPalanEditParamPO.class, ignores = "weeklyId")
    public KPResult<WeeklyPalanPO> save(@RequestBody WeeklyPalanEditParamPO weeklyPalanEditParamPO) {
        weeklyPalanService.saveWeeklyPalan(weeklyPalanEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/week/weekly/palan/update','week:weekly:palan:update')")
    @Operation(summary = "修改周计划", description = "权限 week:weekly:palan:update")
    @PostMapping("/update")
    @KPVerifyNote
    public KPResult<WeeklyPalanPO> update(@RequestBody WeeklyPalanEditParamPO weeklyPalanEditParamPO) {
        weeklyPalanService.updateWeeklyPalan(weeklyPalanEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/week/weekly/palan/remove','week:weekly:palan:remove')")
    @Operation(summary = "删除周计划", description = "权限 week:weekly:palan:remove")
    @PostMapping("/remove")
    @KPApiJsonParam({
            @KPJsonField(name = "weeklyId", description = "周计划id", required = true)
    })
    public KPResult<Void> remove(@RequestBody JSONObject parameter) {
        weeklyPalanService.remove(parameter);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/week/weekly/palan/discard','week:weekly:palan:discard')")
    @Operation(summary = "废弃周计划", description = "权限 week:weekly:palan:discard")
    @PostMapping("/discard")
    @KPApiJsonParam({
            @KPJsonField(name = "weeklyId", description = "周计划id", required = true)
    })
    public KPResult<Void> discard(@RequestBody JSONObject parameter) {
        weeklyPalanService.discard(parameter);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/week/weekly/palan/update/move/status','week:weekly:palan:update:move')")
    @Operation(summary = "移动设置周计划状态", description = "权限 week:weekly:palan:update:move")
    @PostMapping("/update/move/status")
    @KPApiJsonParam({
            @KPJsonField(name = "weeklyId", description = "周计划id", required = true),
            @KPJsonField(name = "taskStatus", description = "周计划状态 1未开始 2进行中 3已完成", required = true)
    })
    public KPResult<WeeklyPalanPO> updateMoveStatus(@RequestBody JSONObject parameter) {
        weeklyPalanService.updateMoveStatus(parameter);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/week/weekly/palan/list', 'week:weekly:palan:list')")
    @Operation(summary = "查询周计划-不带分页", description = "权限 week:weekly:palan:list")
    @PostMapping("/list")
    @KPApiJsonParamMode(component = WeeklyPalanListParamPO.class, ignores = "pageNum,pageSize")
    @KPVerifyNote
    public KPResult<List<WeeklyPalanListCustomerPO>> queryList(@RequestBody WeeklyPalanListParamPO weeklyPalanListParamPO) {
        return KPResult.success(weeklyPalanService.queryList(weeklyPalanListParamPO));
    }


//    @PreAuthorize("hasPermission('/week/weekly/palan/batch/remove','week:weekly:palan:batch:remove')")
//    @Operation(summary = "批量删除周计划", notes="权限 week:weekly:palan:batch:remove")
//    @PostMapping("/batch/remove")
//    @KPApiJsonlParam({
//        @ApiModelProperty(name = "ids", value = "周计划id", required = true, dataType = "list")
//    })
//    public KPResult batchRemove(@RequestBody List<String> ids){
//        return KPResult.success(weeklyPalanService.batchRemove(ids));
//    }
}
