package com.kp.framework.modules.logRecord.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.KPExcludeInterfaceJournal;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.logRecord.po.HttpLogPO;
import com.kp.framework.modules.logRecord.po.param.HttpLogListParamPO;
import com.kp.framework.modules.logRecord.service.HttpLogService;
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

/**
 * 系统外部接口调用记录相关接口。
 * @author lipeng
 * 2025-05-21
 */
@RestController
@RequestMapping("/auth/http/log")
@Tag(name = "系统外部接口调用记录相关接口")
@ApiSupport(author = "lipeng", order = 35)
public class HttpLogController {

    @Autowired
    private HttpLogService httpLogService;

    @PreAuthorize("hasPermission('/auth/http/log/page/list', 'auth:http:log:page:list')")
    @Operation(summary = "查询系统外部接口调用记录分页列表", description = "权限 auth:http:log:page:list")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = HttpLogPO.class)))
    @PostMapping("/page/list")
    @KPVerifyNote
    @KPExcludeInterfaceJournal
    public KPResult<?> queryPageList(@RequestBody HttpLogListParamPO httpLogListParamPO) {
        return httpLogService.queryPageList(httpLogListParamPO);
    }


    @PreAuthorize("hasPermission('/auth/http/log/details','auth:http:log:details')")
    @Operation(summary = "根据查询详情", description = "权限 auth:http:log:details")
    @PostMapping("/details")
    @KPApiJsonParam({
            @KPJsonField(name = "uuid", description = "接口记录主键", required = true)
    })
    @KPExcludeInterfaceJournal
    public KPResult<HttpLogPO> queryDetailsById(@RequestBody JSONObject parameter) {
        return KPResult.success(httpLogService.queryDetailsById(parameter));
    }
}
