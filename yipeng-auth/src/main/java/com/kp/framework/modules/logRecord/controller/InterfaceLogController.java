package com.kp.framework.modules.logRecord.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.KPExcludeInterfaceJournal;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.entity.bo.DictionaryBO;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.logRecord.po.InterfaceLogPO;
import com.kp.framework.modules.logRecord.po.param.InterfaceLogListParamPO;
import com.kp.framework.modules.logRecord.service.InterfaceLogService;
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

/**
 * 系统内部接口调用记录相关接口。
 * @author lipeng
 * 2025-05-21
 */
@RestController
@RequestMapping("/auth/interface/log")
@Tag(name = "系统内部接口调用记录相关接口")
@ApiSupport(author = "lipeng", order = 36)
public class InterfaceLogController {

    @Autowired
    private InterfaceLogService interfaceLogService;

    @PreAuthorize("hasPermission('/auth/interface/log/page/list', 'auth:interface:log:page:list')")
    @Operation(summary = "查询系统内部接口调用记录分页列表", description = "权限 auth:interface:log:page:list")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = InterfaceLogPO.class)))
    @PostMapping("/page/list")
    @KPVerifyNote
    @KPExcludeInterfaceJournal
    public KPResult<?> queryPageList(@RequestBody InterfaceLogListParamPO interfaceLogListParamPO) {
        return interfaceLogService.queryPageList(interfaceLogListParamPO);
    }


    @PreAuthorize("hasPermission('/auth/interface/log/details','auth:interface:log:details')")
    @Operation(summary = "根据查询详情", description = "权限 auth:interface:log:details")
    @PostMapping("/details")
    @KPApiJsonParam({
            @KPJsonField(name = "uuid", description = "接口记录主键", required = true)
    })
    @KPExcludeInterfaceJournal
    public KPResult<InterfaceLogPO> queryDetailsById(@RequestBody JSONObject parameter) {
        return KPResult.success(interfaceLogService.queryDetailsById(parameter));
    }


    @Operation(summary = "查询接口所属项目")
    @PostMapping("/project")
    @KPExcludeInterfaceJournal
    public KPResult<List<DictionaryBO>> queryProject() {
        return KPResult.success(interfaceLogService.queryProject());
    }


    @Operation(summary = "查询内部接口名称")
    @PostMapping("/name")
    @KPApiJsonParam({
            @KPJsonField(name = "projectName", description = "项目名称", required = true, example = "mdm"),
    })
    @KPExcludeInterfaceJournal
    public KPResult<List<DictionaryBO>> queryInterfaceName(@RequestBody JSONObject parameter) {
        return KPResult.success(interfaceLogService.queryInterfaceName(parameter));
    }


    @PreAuthorize("hasPermission('/auth/interface/log/clear/cache','auth:interface:log:clear:cache')")
    @Operation(summary = "清空接口缓存", description = "权限 auth:interface:log:clear:cache")
    @PostMapping("/clear/cache")
    public KPResult<Void> clearCache() {
        interfaceLogService.clearCache();
        return KPResult.success();
    }
}
