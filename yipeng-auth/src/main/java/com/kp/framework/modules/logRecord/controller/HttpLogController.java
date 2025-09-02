package com.kp.framework.modules.logRecord.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonlParam;
import com.kp.framework.annotation.KPExcludeInterfaceJournal;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.logRecord.po.HttpLogPO;
import com.kp.framework.modules.logRecord.po.param.HttpLogListParamPO;
import com.kp.framework.modules.logRecord.service.HttpLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @Author lipeng
* @Description  系统外部接口调用记录相关接口
* @Date 2025-05-21
**/
@RestController
@RequestMapping("/auth/http/log")
@Api(tags = "系统外部接口调用记录相关接口", value = "系统外部接口调用记录相关接口")
@ApiSupport(order = 11)
public class HttpLogController {

    @Autowired
    private HttpLogService httpLogService;


    @PreAuthorize("hasPermission('/auth/http/log/page/list', 'auth:http:log:page:list')")
    @ApiOperation(value = "查询系统外部接口调用记录分页列表", notes = "权限 auth:http:log:page:list")
    @PostMapping("/page/list")
    @KPVerifyNote
    @KPExcludeInterfaceJournal
    public KPResult<HttpLogPO> queryPageList(@RequestBody HttpLogListParamPO httpLogListParamPO){
        return KPResult.list(httpLogService.queryPageList(httpLogListParamPO));
    }


    @PreAuthorize("hasPermission('/auth/http/log/details','auth:http:log:details')")
    @ApiOperation(value = "根据查询详情", notes="权限 auth:http:log:details")
    @PostMapping("/details")
    @KPApiJsonlParam({
        @ApiModelProperty(name = "uuid", value = "接口记录主键", required = true)
    })
    @KPExcludeInterfaceJournal
    public KPResult<HttpLogPO> queryDetailsById(@RequestBody JSONObject parameter){
        return KPResult.success(httpLogService.queryDetailsById(parameter));
    }
}
