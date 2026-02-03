package com.kp.framework.modules.logRecord.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.KPExcludeInterfaceJournal;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.entity.bo.DictionaryBO;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.logRecord.po.customer.InterfaceCallListCustomerPO;
import com.kp.framework.modules.logRecord.service.InterfaceLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统内部接口调用记录相关接口。
 * @author lipeng
 * 2025-05-21
 */
@RestController
@RequestMapping("/auth/interface/call")
@Tag(name = "系统内部接口调用记录相关接口")
@ApiSupport(author = "lipeng", order = 36)
public class InterfaceCallController {

    @Autowired
    private InterfaceLogService interfaceLogService;

    @Operation(summary = "查询接口日志的项目名称")
    @PostMapping("/project/name")
    public KPResult<List<DictionaryBO>> queryProject() {
        return KPResult.success(interfaceLogService.queryProjectName());
    }

    @Operation(summary = "查询接口调用次数列表")
    @KPExcludeInterfaceJournal
    @KPApiJsonParam({
            @KPJsonField(name = "projectName", description = "项目名称(项目code)"),
    })
    @PostMapping("/interface/call/list")
    public KPResult<List<InterfaceCallListCustomerPO>> queryInterfaceCallList(@RequestBody JSONObject parameter) {
        return KPResult.success(interfaceLogService.queryInterfaceCallList(parameter));
    }
}
