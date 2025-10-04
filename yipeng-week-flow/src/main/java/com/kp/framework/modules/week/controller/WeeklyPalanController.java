package com.kp.framework.modules.week.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonlParam;
import com.kp.framework.annotation.KPApiJsonlParamMode;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.week.po.WeeklyPalanPO;
import com.kp.framework.modules.week.po.customer.WeeklyPalanListCustomerPO;
import com.kp.framework.modules.week.po.param.WeeklyPalanEditParamPO;
import com.kp.framework.modules.week.po.param.WeeklyPalanListParamPO;
import com.kp.framework.modules.week.service.WeeklyPalanService;
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
 * @Description 周计划表相关接口
 * @Date 2025-09-20 22:11:23
 **/
@RestController
@RequestMapping("/week/weekly/palan")
@Api(tags = "周计划相关接口", value = "周计划相关接口")
@ApiSupport(order = 4)
public class WeeklyPalanController {

    @Autowired
    private WeeklyPalanService weeklyPalanService;


//    @PreAuthorize("hasPermission('/week/weekly/palan/page/list', 'week:weekly:palan:page:list')")
//    @ApiOperation(value = "查询周计划分页列表", notes = "权限 week:weekly:palan:page:list")
//    @PostMapping("/page/list")
//    @KPVerifyNote
//    public KPResult<WeeklyPalanPO> queryPageList(@RequestBody WeeklyPalanListParamPO weeklyPalanListParamPO){
//        return KPResult.list(weeklyPalanService.queryPageList(weeklyPalanListParamPO));
//    }

    @PreAuthorize("hasPermission('/week/weekly/palan/my/list', 'week:weekly:palan:my:list')")
    @ApiOperation(value = "查询我的周计划列表(按周分组)", notes = "权限 week:weekly:palan:my:list")
    @PostMapping("/my/list")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "planTime", value = "月计划时间 yyyy-mm", required = true)
    })
    public KPResult<Map<String, List<WeeklyPalanListCustomerPO>>> queryMyList(@RequestBody JSONObject parameter) {
        return KPResult.success(weeklyPalanService.queryMyList(parameter));
    }


    @PreAuthorize("hasPermission('/week/weekly/palan/my/list/status', 'week:weekly:palan:my:list:status')")
    @ApiOperation(value = "查询我的周计划列表(按状态分组)", notes = "权限 week:weekly:palan:my:list:status")
    @PostMapping("/my/list/status")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "planTime", value = "月计划时间 yyyy-mm", required = true)
    })
    public KPResult<Map<Integer, List<WeeklyPalanListCustomerPO>>> queryMyListByStatus(@RequestBody JSONObject parameter) {
        return KPResult.success(weeklyPalanService.queryMyListByStatus(parameter));
    }


    @PreAuthorize("hasPermission('/week/weekly/palan/details','week:weekly:palan:details')")
    @ApiOperation(value = "根据周计划id查询详情", notes = "权限 week:weekly:palan:details", response = WeeklyPalanPO.class)
    @PostMapping("/details")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "weeklyId", value = "周计划id", required = true)
    })
    public KPResult<JSONObject> queryDetailsById(@RequestBody JSONObject parameter) {
        return KPResult.success(weeklyPalanService.queryDetailsById(parameter));
    }


    @PreAuthorize("hasPermission('/week/weekly/palan/save','week:weekly:palan:save')")
    @ApiOperation(value = "新增周计划", notes = "权限 week:weekly:palan:save")
    @PostMapping("/save")
    @KPVerifyNote
    @KPApiJsonlParamMode(component = WeeklyPalanEditParamPO.class, ignores = "weeklyId")
    public KPResult<WeeklyPalanPO> save(@RequestBody WeeklyPalanEditParamPO weeklyPalanEditParamPO) {
        weeklyPalanService.saveWeeklyPalan(weeklyPalanEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/week/weekly/palan/update','week:weekly:palan:update')")
    @ApiOperation(value = "修改周计划", notes = "权限 week:weekly:palan:update")
    @PostMapping("/update")
    @KPVerifyNote
    public KPResult<WeeklyPalanPO> update(@RequestBody WeeklyPalanEditParamPO weeklyPalanEditParamPO) {
        weeklyPalanService.updateWeeklyPalan(weeklyPalanEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/week/weekly/palan/remove','week:weekly:palan:remove')")
    @ApiOperation(value = "删除周计划", notes = "权限 week:weekly:palan:remove")
    @PostMapping("/remove")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "weeklyId", value = "周计划id", required = true)
    })
    public KPResult<Void> remove(@RequestBody JSONObject parameter) {
        weeklyPalanService.remove(parameter);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/week/weekly/palan/discard','week:weekly:palan:discard')")
    @ApiOperation(value = "废弃周计划", notes = "权限 week:weekly:palan:discard")
    @PostMapping("/discard")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "weeklyId", value = "周计划id", required = true)
    })
    public KPResult<Void> discard(@RequestBody JSONObject parameter) {
        weeklyPalanService.discard(parameter);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/week/weekly/palan/update/move/status','week:weekly:palan:update:move')")
    @ApiOperation(value = "移动设置周计划状态", notes = "权限 week:weekly:palan:update:move")
    @PostMapping("/update/move/status")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "weeklyId", value = "周计划id", required = true),
            @ApiModelProperty(name = "taskStatus", value = "周计划状态 1未开始 2进行中 3已完成", required = true)
    })
    public KPResult<WeeklyPalanPO> updateMoveStatus(@RequestBody JSONObject parameter) {
        weeklyPalanService.updateMoveStatus(parameter);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/week/weekly/palan/list', 'week:weekly:palan:list')")
    @ApiOperation(value = "查询周计划-不带分页", notes = "权限 week:weekly:palan:list")
    @PostMapping("/list")
    @KPApiJsonlParamMode(component = WeeklyPalanListParamPO.class, ignores = "pageNum,pageSize")
    @KPVerifyNote
    public KPResult<List<WeeklyPalanListCustomerPO>> queryList(@RequestBody WeeklyPalanListParamPO weeklyPalanListParamPO) {
        return KPResult.success(weeklyPalanService.queryList(weeklyPalanListParamPO));
    }


//    @PreAuthorize("hasPermission('/week/weekly/palan/batch/remove','week:weekly:palan:batch:remove')")
//    @ApiOperation(value = "批量删除周计划", notes="权限 week:weekly:palan:batch:remove")
//    @PostMapping("/batch/remove")
//    @KPApiJsonlParam({
//        @ApiModelProperty(name = "ids", value = "周计划id", required = true, dataType = "list")
//    })
//    public KPResult batchRemove(@RequestBody List<String> ids){
//        return KPResult.success(weeklyPalanService.batchRemove(ids));
//    }
}
