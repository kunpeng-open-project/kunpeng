package com.kunpeng.framework.modules.logRecord.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kunpeng.framework.annotation.KPApiJsonlParam;
import com.kunpeng.framework.annotation.KPExcludeInterfaceJournal;
import com.kunpeng.framework.entity.bo.DictionaryBO;
import com.kunpeng.framework.entity.bo.KPResult;
import com.kunpeng.framework.modules.logRecord.po.customer.InterfaceCallListCustomerPO;
import com.kunpeng.framework.modules.logRecord.service.InterfaceLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/auth/interface/call")
@Api(tags = "系统内部接口调用记录相关接口", value = "系统内部接口调用记录相关接口")
@ApiSupport(order = 10)
public class InterfaceCallController {

    @Autowired
    private InterfaceLogService interfaceLogService;


    @ApiOperation(value = "查询接口日志的项目名称")
    @PostMapping("/project/name")
    public KPResult<List<DictionaryBO>> queryProject(){
        return KPResult.success(interfaceLogService.queryProjectName());
    }

    @ApiOperation(value = "查询接口调用次数列表")
    @KPExcludeInterfaceJournal
    @KPApiJsonlParam({
        @ApiModelProperty(name = "projectName", value = "项目名称(项目code)"),
    })
    @PostMapping("/interface/call/list")
    public KPResult<List<InterfaceCallListCustomerPO>> queryInterfaceCallList(@RequestBody JSONObject parameter){
        return KPResult.success(interfaceLogService.queryInterfaceCallList(parameter));
    }
}
