package com.kp.framework.controller;


import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.controller.server.EhcacheService;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.entity.po.EhcacheHitListCustomerPO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author lipeng
 * @Description
 * @Date 2024/4/8 19:28
 * @return
 **/
@Controller
@RequestMapping("/open/ehcache/cache")
@Description("Ehcache二级缓存相关接口")
@Api(tags = "Ehcache二级缓存相关接口", value = "Ehcache二级缓存相关接口")
@ApiSupport(order = 9999)
public class EhcacheController {

    @Autowired
    private EhcacheService ehcacheService;

    @ApiOperation(value = "查询缓存记录列表", response = EhcacheHitListCustomerPO.class)
    @PostMapping(value = "/list")
    @KPVerifyNote
    @ResponseBody
    public KPResult queryList() {
        return KPResult.success(ehcacheService.queryList());
    }



    @ApiOperation(value = "清空所有缓存")
    @PostMapping(value = "/clear/all")
    @KPVerifyNote
    @ResponseBody
    public KPResult clearAll() {
        ehcacheService.clearAll();
        return KPResult.success();
    }
}
