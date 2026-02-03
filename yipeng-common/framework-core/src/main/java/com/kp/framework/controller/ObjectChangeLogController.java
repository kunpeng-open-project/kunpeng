package com.kp.framework.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.controller.po.ObjectChangeLogPO;
import com.kp.framework.controller.po.param.ObjectChangeLogListParamPO;
import com.kp.framework.controller.server.ObjectChangeLogService;
import com.kp.framework.entity.bo.KPResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据库字段修改记录相关接口。
 * @author lipeng
 * 2025-11-10
 */
@RestController
@RequestMapping("/auth/object/change/log")
@Tag(name = "系统内置相关接口")
@ApiSupport(author = "lipeng", order = 3)
public class ObjectChangeLogController {

    @Autowired
    private ObjectChangeLogService objectChangeLogService;


    @PreAuthorize("hasPermission('/auth/object/change/log/page/list', 'auth:object:change:log:page:list')")
    @Operation(summary = "查询数据库字段修改记录分页列表", description = "权限 auth:object:change:log:page:list")
    @PostMapping("/page/list")
    @KPVerifyNote
    public KPResult<ObjectChangeLogPO> queryPageList(@RequestBody ObjectChangeLogListParamPO objectChangeLogListParamPO) {
        return objectChangeLogService.queryPageList(objectChangeLogListParamPO);
    }
}
