package com.kp.framework.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPExcludeInterfaceJournal;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.entity.bo.ServerBO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控。
 * @author lipeng
 * 2024/12/21
 */
@RestController
@RequestMapping("/open/server")
@Tag(name = "系统内置相关接口")
@ApiSupport(author = "lipeng", order = 3)
public class ServerController {


    @PostMapping("/system/message")
    @KPExcludeInterfaceJournal
    @Operation(summary = "获取服务器相关信息")
    public KPResult<ServerBO> getServer() {
        ServerBO server = new ServerBO();
        server.setValueTo();
        return KPResult.success(server);
    }

}
