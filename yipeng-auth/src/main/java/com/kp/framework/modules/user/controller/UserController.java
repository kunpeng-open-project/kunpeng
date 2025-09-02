package com.kp.framework.modules.user.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonlParam;
import com.kp.framework.annotation.KPApiJsonlParamMode;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.constant.MinioConstant;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.user.po.UserPO;
import com.kp.framework.modules.user.po.customer.UserDetailsCustomerPO;
import com.kp.framework.modules.user.po.customer.UserListCustomerPO;
import com.kp.framework.modules.user.po.param.UserEditParamPO;
import com.kp.framework.modules.user.po.param.UserListParamPO;
import com.kp.framework.modules.user.service.UserService;
import com.kp.framework.utils.kptool.KPJSONFactoryUtil;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPMinioUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author lipeng
 * @Description 用户信息表相关接口
 * @Date 2025-04-21
 **/
@RestController
@RequestMapping("/auth/user")
@Api(tags = "用户信息相关接口", value = "用户信息相关接口")
@ApiSupport(order = 1)
public class UserController {

    @Autowired
    private UserService userService;


    @PreAuthorize("hasPermission('/auth/user/page/list', 'auth:user:page:list')")
    @ApiOperation(value = "查询用户信息分页列表", notes = "权限 auth:user:page:list")
    @PostMapping("/page/list")
    @KPVerifyNote
    public KPResult<UserListCustomerPO> queryPageList(@RequestBody UserListParamPO userListParamPO) {
        return KPResult.list(userService.queryPageList(userListParamPO));
    }

    @PreAuthorize("hasPermission('/auth/user/page/list', 'auth:user:page:list')")
    @ApiOperation(value = "查询用户信息-不带分页", notes = "权限 auth:user:page:list")
    @PostMapping(value = "/list")
    @KPApiJsonlParamMode(component = UserListParamPO.class, ignores = "pageNum,pageSize")
    public KPResult<List<UserListCustomerPO>> queryList(@RequestBody UserListParamPO userListParamPO) {
        return KPResult.success(userService.queryList(userListParamPO));
    }


    @PreAuthorize("hasPermission('/auth/user/details','auth:user:details')")
    @ApiOperation(value = "根据用户Id查询详情", notes = "权限 auth:user:details")
    @PostMapping("/details")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "userId", value = "用户Id", required = true)
    })
    public KPResult<UserDetailsCustomerPO> queryDetailsById(@RequestBody JSONObject parameter) {
        return KPResult.success(userService.queryDetailsById(parameter));
    }

    @ApiOperation(value = "修改个人信息时回显数据", response = UserDetailsCustomerPO.class)
    @PostMapping("/feedback/details")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "userId", value = "用户Id", required = true)
    })
    public KPResult feedbackDetails(@RequestBody JSONObject parameter) {
        UserDetailsCustomerPO userDetailsCustomerPO = userService.queryDetailsById(parameter);
        return KPResult.success(new KPJSONFactoryUtil(KPJsonUtil.toJson(userDetailsCustomerPO))
                .put("avatarShow", KPMinioUtil.getUrl(MinioConstant.AUTH_BUCKET_NAME, userDetailsCustomerPO.getAvatar(), 168)).build());
    }


    @PreAuthorize("hasPermission('/auth/user/save','auth:user:save')")
    @ApiOperation(value = "新增用户信息", notes = "权限 auth:user:save")
    @PostMapping("/save")
    @KPVerifyNote
    @KPApiJsonlParamMode(component = UserEditParamPO.class, ignores = "userId")
    public KPResult<UserPO> save(@RequestBody UserEditParamPO userEditParamPO) {
        userService.saveUser(userEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/user/update','auth:user:update')")
    @ApiOperation(value = "修改用户信息", notes = "权限 auth:user:update")
    @PostMapping("/update")
    @KPVerifyNote
    public KPResult<UserPO> update(@RequestBody UserEditParamPO userEditParamPO) {
        userService.updateUser(userEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/user/batch/remove','auth:user:batch:remove')")
    @ApiOperation(value = "批量删除用户信息", notes = "权限 auth:user:batch:remove")
    @PostMapping("/batch/remove")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "ids", value = "用户Id", required = true, dataType = "list")
    })
    public KPResult batchRemove(@RequestBody List<String> ids) {
        return KPResult.success(userService.batchRemove(ids));
    }


    @PreAuthorize("hasPermission('/auth/user/forbidden','auth:user:forbidden')")
    @ApiOperation(value = "禁用或者取消禁用", notes = "权限 auth:user:forbidden")
    @PostMapping(value = "/forbidden")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "userId", value = "用户Id", required = true, example = "5dbf7ae5e10272f8e1867cd25447720a")
    })
    public KPResult doForbidden(@RequestBody JSONObject parameter) {
        userService.doForbidden(parameter);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/user/batch/cancel','auth:user:batch:cancel')")
    @ApiOperation(value = "批量注销", notes = "权限 auth:user:batch:cancel")
    @PostMapping(value = "/batch/cancel")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "ids", value = "用户Id", required = true, dataType = "list")
    })
    public KPResult doCancel(@RequestBody List<String> ids) {
        return userService.doCancel(ids);
    }


    @PreAuthorize("hasPermission('/auth/user/reset','auth:user:reset')")
    @ApiOperation(value = "管理员密码重置", notes = "权限 auth:user:reset")
    @PostMapping(value = "/reset")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "userId", value = "用户Id", required = true, example = "5dbf7ae5e10272f8e1867cd25447720a"),
    })
    public KPResult doReset(@RequestBody JSONObject parameter) {
        userService.doReset(parameter);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/user/unlock','auth:user:unlock')")
    @ApiOperation(value = "管理员手动解锁", notes = "权限 auth:user:unlock")
    @PostMapping(value = "/unlock")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "userId", value = "用户Id", required = true, example = "5dbf7ae5e10272f8e1867cd25447720a")
    })
    public KPResult doUnlock(@RequestBody JSONObject parameter) {
        userService.doUnlock(parameter);
        return KPResult.success("解锁成功！");
    }


    @ApiOperation(value = "修改密码")
    @PostMapping(value = "/update/password")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "userId", value = "用户Id", required = true, example = "3830638e3a1f8771d96f71fce71e665b"),
            @ApiModelProperty(name = "oldPassword", value = "老密码", required = true),
            @ApiModelProperty(name = "newPassword", value = "新密码", required = true),
            @ApiModelProperty(name = "okPassword", value = "确认新密码", required = true)
    })
    public KPResult updatePassword(@RequestBody JSONObject parameter) {
        userService.updatePassword(parameter);
        return KPResult.success();
    }


    @ApiOperation(value = "用户个人修改数据")
    @PostMapping(value = "/update/message")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "userId", value = "用户ID", required = true),
            @ApiModelProperty(name = "nickName", value = "用户昵称", required = true),
            @ApiModelProperty(name = "phoneNumber", value = "手机号", required = true),
            @ApiModelProperty(name = "sex", value = "性别", required = true),
            @ApiModelProperty(name = "email", value = "邮箱"),
            @ApiModelProperty(name = "avatar", value = "头像"),
    })
    public KPResult updateMessage(@RequestBody JSONObject parameter) {
        userService.updateMessage(parameter);
        return KPResult.success();
    }
}
