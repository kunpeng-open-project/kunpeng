package com.kp.framework.api;


import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.menu.service.MenuPureAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/router")
@Tag(name = "API-路由相关接口")
@ApiSupport(author = "lipeng", order = 10)
public class RouterApiController {

    @Autowired
    private MenuPureAdminService menuPureAdminService;


    @Operation(summary = "查询Pure-Admin框架路由")
    @PostMapping("/pure/admin")
    @KPApiJsonParam({
            @KPJsonField(name = "projectCode", description = "项目编号", required = true, example = "XM001"),
    })
    public KPResult<List<JSONObject>> queryPureAdminRouters(@RequestBody JSONObject parameter) {
        return KPResult.success(menuPureAdminService.queryRouters(parameter));
    }
}
