package com.kunpeng.framework.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kunpeng.framework.annotation.KPExcludeInterfaceJournal;
import com.kunpeng.framework.entity.bo.KPResult;
import com.kunpeng.framework.entity.bo.ServerBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open/server")
@Description("服务器相关信息")
@Api(tags = "服务器相关信息", value = "服务器相关信息")
@ApiSupport(order = 9999)
public class ServerController {


    @PostMapping("/system/message")
    @KPExcludeInterfaceJournal
    @ApiOperation(value = "获取服务器相关信息")
    public KPResult getServer() {
        ServerBO server = new ServerBO();
        server.setValueTo();
        return KPResult.success(server);
    }

}
