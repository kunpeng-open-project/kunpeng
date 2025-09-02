package com.kp.framework.modules.logRecord.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonlParam;
import com.kp.framework.annotation.KPExcludeInterfaceJournal;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.entity.bo.DictionaryBO;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.logRecord.po.InterfaceLogPO;
import com.kp.framework.modules.logRecord.po.param.InterfaceLogListParamPO;
import com.kp.framework.modules.logRecord.service.InterfaceLogService;
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

/**
* @Author lipeng
* @Description  系统内部接口调用记录相关接口
* @Date 2025-05-21
**/
@RestController
@RequestMapping("/auth/interface/log")
@Api(tags = "系统内部接口调用记录相关接口", value = "系统内部接口调用记录相关接口")
@ApiSupport(order = 10)
public class InterfaceLogController {

    @Autowired
    private InterfaceLogService interfaceLogService;


    @PreAuthorize("hasPermission('/auth/interface/log/page/list', 'auth:interface:log:page:list')")
    @ApiOperation(value = "查询系统内部接口调用记录分页列表", notes = "权限 auth:interface:log:page:list")
    @PostMapping("/page/list")
    @KPVerifyNote
    @KPExcludeInterfaceJournal
    public KPResult<InterfaceLogPO> queryPageList(@RequestBody InterfaceLogListParamPO interfaceLogListParamPO){
        return KPResult.list(interfaceLogService.queryPageList(interfaceLogListParamPO));
    }

    @PreAuthorize("hasPermission('/auth/interface/log/details','auth:interface:log:details')")
    @ApiOperation(value = "根据查询详情", notes="权限 auth:interface:log:details")
    @PostMapping("/details")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "uuid", value = "接口记录主键", required = true)
    })
    @KPExcludeInterfaceJournal
    public KPResult<InterfaceLogPO> queryDetailsById(@RequestBody JSONObject parameter){
        return KPResult.success(interfaceLogService.queryDetailsById(parameter));
    }

    @ApiOperation(value = "查询接口所属项目")
    @PostMapping("/project")
    @KPExcludeInterfaceJournal
    public KPResult<List<DictionaryBO>> queryProject(){
        return KPResult.success(interfaceLogService.queryProject());
    }

    @ApiOperation(value = "查询内部接口名称")
    @PostMapping("/name")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "projectName", value = "项目名称", required = true, example = "mdm"),
    })
    @KPExcludeInterfaceJournal
    public KPResult<List<DictionaryBO>> queryInterfaceName(@RequestBody JSONObject parameter){
        return KPResult.success(interfaceLogService.queryInterfaceName(parameter));
    }


    @PreAuthorize("hasPermission('/auth/interface/log/clear/cache','auth:interface:log:clear:cache')")
    @ApiOperation(value = "清空接口缓存", notes = "权限 auth:interface:log:clear:cache")
    @PostMapping("/clear/cache")
    public KPResult clearCache(){
        interfaceLogService.clearCache();
        return KPResult.success();
    }
}
