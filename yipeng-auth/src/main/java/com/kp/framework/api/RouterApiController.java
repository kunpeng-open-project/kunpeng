package com.kp.framework.api;


import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonlParam;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.menu.service.MenuPureAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/router")
@Api(tags = "API-路由相关接口", value = "API-路由相关接口")
@ApiSupport(order = 0)
public class RouterApiController {

    @Autowired
    private MenuPureAdminService menuPureAdminService;


    @ApiOperation(value = "查询Pure-Admin框架路由")
    @PostMapping("/pure/admin")
    @KPApiJsonlParam({
        @ApiModelProperty(name = "projectCode", value = "项目编号", required = true, example = "XM001"),
    })
    public KPResult<List<JSONObject>> queryPureAdminRouters(@RequestBody JSONObject parameter){
        return KPResult.success(menuPureAdminService.queryRouters(parameter));
    }
}
