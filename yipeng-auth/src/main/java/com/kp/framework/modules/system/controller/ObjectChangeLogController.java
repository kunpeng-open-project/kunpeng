package com.kp.framework.modules.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.system.po.ObjectChangeLogPO;
import com.kp.framework.modules.system.po.param.ObjectChangeLogListParamPO;
import com.kp.framework.modules.system.service.ObjectChangeLogService;
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
* @Description  相关接口
* @Date 2025-11-10 16:34:05
**/
@RestController
@RequestMapping("/auth/object/change/log")
@Api(tags = "数据库字段修改记录相关接口", value = "数据库字段修改记录相关接口")
@ApiSupport(order = 30)
public class ObjectChangeLogController {

    @Autowired
    private ObjectChangeLogService objectChangeLogService;


    @PreAuthorize("hasPermission('/auth/object/change/log/page/list', 'auth:object:change:log:page:list')")
    @ApiOperation(value = "查询分页列表", notes = "权限 auth:object:change:log:page:list")
    @PostMapping("/page/list")
    @KPVerifyNote
    public KPResult<ObjectChangeLogPO> queryPageList(@RequestBody ObjectChangeLogListParamPO objectChangeLogListParamPO){
        return KPResult.list(objectChangeLogService.queryPageList(objectChangeLogListParamPO));
    }

}
