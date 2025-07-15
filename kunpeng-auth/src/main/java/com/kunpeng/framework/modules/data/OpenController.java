package com.kunpeng.framework.modules.data;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open")
@Api(tags = "开放接口", value = "开放接口")
@ApiSupport(order = 99)
public class OpenController {

    @ApiOperation(value = "查询项目下拉框")
    @GetMapping("/aaaa")
    public void queryProjectSelect() {


        System.out.println("!11111111111");
    }
}
