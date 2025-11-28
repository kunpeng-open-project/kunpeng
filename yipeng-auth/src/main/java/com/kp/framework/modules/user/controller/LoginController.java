package com.kp.framework.modules.user.controller;


import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonlParam;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.user.po.customer.AuthorizationCustomerPO;
import com.kp.framework.modules.user.po.customer.UserLoginCustomerPO;
import com.kp.framework.modules.user.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/user")
@Api(tags = "登录相关接口", value = "登录相关接口")
@ApiSupport(order = 0)
public class LoginController {


    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @ApiOperation(value = "普通登录")
    @KPApiJsonlParam({
        @ApiModelProperty(name = "userName", value = "用户账号", required = true, example = "admin1"),
        @ApiModelProperty(name = "password", value = "密码", required = true, example = "admin1admin1"),
        @ApiModelProperty(name = "projectCode", value = "项目编号", required = true, example = "authentication"),
    })
    public KPResult<UserLoginCustomerPO> login(@RequestBody JSONObject parameter) {
        return KPResult.success(loginService.login(parameter));
    }


    @PostMapping("/exempt/login")
    @ApiOperation(value = "免密登录(特殊场景使用)", notes = "特殊场景使用, 正常登录请使用普通登录接口，如果没有经过授权 直接使用该接口。所造成的后果自行承担")
    @KPApiJsonlParam({
        @ApiModelProperty(name = "userName", value = "账号(账号和工号二选一)", required = true),
        @ApiModelProperty(name = "jobNumber", value = "工号(账号和工号二选一)", required = true),
        @ApiModelProperty(name = "projectId", value = "项目id", required = true)
    })
//    @ApiIgnore
    public KPResult<UserLoginCustomerPO> exemptLogin(@RequestBody JSONObject parameter) {
        return KPResult.success(loginService.exemptLogin(parameter));
    }

    @PostMapping("/sso/login")
    @ApiOperation(value = "单点登录")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "accessToken", value = "请求登录系统的token", required = true),
            @ApiModelProperty(name = "projectCode", value = "项目编号", required = true, example = "authentication"),
    })
//    @ApiIgnore
    public KPResult<UserLoginCustomerPO> ssoLogin(@RequestBody JSONObject parameter) {
        return KPResult.success(loginService.ssoLogin(parameter));
    }


    @PostMapping("/authorization/login")
    @ApiOperation(value = "授权登录", notes = "★ 请注意 ★， <br/>&nbsp;&nbsp;&nbsp;&nbsp; token不允许频繁调用，请自己缓存有效token，请勿每次请求接口都获取token <br/>&nbsp;&nbsp;&nbsp;&nbsp; 获取授权token有调用限制，目前每天最多允许100次，超过100次将获取不到token， 为了您的业务正常进行，请本地缓存token <br/>&nbsp;&nbsp;&nbsp;&nbsp; 如果100次不够使用，请联系管理员重新设置最大调用次数")
    @KPApiJsonlParam({
        @ApiModelProperty(name = "appId", value = "appId", required = true),
        @ApiModelProperty(name = "appSecret", value = "appSecret", required = true)
    })
    public KPResult<AuthorizationCustomerPO> authorizationLogin(@RequestBody JSONObject parameter) {
        return KPResult.success(loginService.authorization(parameter));
    }


    @GetMapping("/logout")
    @ApiOperation(value = "退出登录")
    public KPResult logout() {
        return KPResult.success();
    }
}
