package com.kp.framework.modules.user.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.KPApiJsonParamMode;
import com.kp.framework.annotation.KPObjectChangeLogListNote;
import com.kp.framework.annotation.KPObjectChangeLogNote;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.constant.ObjectChangeLogOperateType;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.user.mapper.UserDeptMapper;
import com.kp.framework.modules.user.mapper.UserMapper;
import com.kp.framework.modules.user.mapper.UserPostMapper;
import com.kp.framework.modules.user.mapper.UserProjectMapper;
import com.kp.framework.modules.user.mapper.UserRoleMapper;
import com.kp.framework.modules.user.po.UserPO;
import com.kp.framework.modules.user.po.customer.UserDetailsCustomerPO;
import com.kp.framework.modules.user.po.customer.UserListCustomerPO;
import com.kp.framework.modules.user.po.param.UserEditParamPO;
import com.kp.framework.modules.user.po.param.UserListParamPO;
import com.kp.framework.modules.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户信息表相关接口。
 * @author lipeng
 * 2025-04-21
 */
@RestController
@RequestMapping("/auth/user")
@Tag(name = "用户信息相关接口")
@ApiSupport(author = "lipeng", order = 20)
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasPermission('/auth/user/page/list', 'auth:user:page:list')")
    @Operation(summary = "查询用户信息分页列表", description = "权限 auth:user:page:list")
    @PostMapping("/page/list")
    @KPVerifyNote
    public KPResult<UserListCustomerPO> queryPageList(@RequestBody UserListParamPO userListParamPO) {
        return userService.queryPageList(userListParamPO);
    }


    @Operation(summary = "查询用户信息-不带分页")
    @PostMapping(value = "/list")
    @KPApiJsonParamMode(component = UserListParamPO.class, ignores = "pageNum,pageSize")
    public KPResult<List<UserListCustomerPO>> queryList(@RequestBody UserListParamPO userListParamPO) {
        return KPResult.success(userService.queryList(userListParamPO));
    }


    @PreAuthorize("hasPermission('/auth/user/details','auth:user:details')")
    @Operation(summary = "根据用户Id查询详情", description = "权限 auth:user:details")
    @PostMapping("/details")
    @KPApiJsonParam({
            @KPJsonField(name = "userId", description = "用户Id", required = true)
    })
    public KPResult<UserDetailsCustomerPO> queryDetailsById(@RequestBody JSONObject parameter) {
        return KPResult.success(userService.queryDetailsById(parameter));
    }

    @Operation(summary = "修改个人信息时回显数据")
    @PostMapping("/feedback/details")
    @KPApiJsonParam({
            @KPJsonField(name = "userId", description = "用户Id", required = true)
    })
//    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserDetailsCustomerPO.class)))
    public KPResult<UserDetailsCustomerPO> feedbackDetails(@RequestBody JSONObject parameter) {
        return KPResult.success(userService.queryDetailsById(parameter));
//        return KPResult.success(new KPJSONFactoryUtil(KPJsonUtil.toJson(userDetailsCustomerPO))
//                .put("avatarShow", KPMinioUtil.getUrl(userDetailsCustomerPO.getAvatar(), 24)).build());
    }


    @PreAuthorize("hasPermission('/auth/user/save','auth:user:save')")
    @Operation(summary = "新增用户信息", description = "权限 auth:user:save")
    @PostMapping("/save")
    @KPApiJsonParamMode(component = UserEditParamPO.class, ignores = "userId")
    @KPObjectChangeLogListNote({
            @KPObjectChangeLogNote(parentMapper = UserMapper.class, identification = "userId,user_id", operateType = ObjectChangeLogOperateType.ADD, businessType = "用户信息"),
            @KPObjectChangeLogNote(parentMapper = UserDeptMapper.class, identification = "userId,user_id", operateType = ObjectChangeLogOperateType.ADD_BATCH, businessType = "用户所属部门"),
            @KPObjectChangeLogNote(parentMapper = UserRoleMapper.class, identification = "userId,user_id", operateType = ObjectChangeLogOperateType.ADD_BATCH, businessType = "用户所属角色"),
            @KPObjectChangeLogNote(parentMapper = UserPostMapper.class, identification = "userId,user_id", operateType = ObjectChangeLogOperateType.ADD_BATCH, businessType = "用户所属岗位"),
            @KPObjectChangeLogNote(parentMapper = UserProjectMapper.class, identification = "userId,user_id", operateType = ObjectChangeLogOperateType.ADD_BATCH, businessType = "用户可操作项目")
    })
    @KPVerifyNote
    public KPResult<UserPO> save(@RequestBody UserEditParamPO userEditParamPO) {
        userService.saveUser(userEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/user/update','auth:user:update')")
    @Operation(summary = "修改用户信息", description = "权限 auth:user:update")
    @PostMapping("/update")
    @KPObjectChangeLogListNote({
            @KPObjectChangeLogNote(parentMapper = UserMapper.class, identification = "userId,user_id", businessType = "用户信息"),
            @KPObjectChangeLogNote(parentMapper = UserDeptMapper.class, identification = "userId,user_id", operateType = ObjectChangeLogOperateType.DELETE_ADD, businessType = "用户所属部门"),
            @KPObjectChangeLogNote(parentMapper = UserRoleMapper.class, identification = "userId,user_id", operateType = ObjectChangeLogOperateType.DELETE_ADD, businessType = "用户所属角色"),
            @KPObjectChangeLogNote(parentMapper = UserPostMapper.class, identification = "userId,user_id", operateType = ObjectChangeLogOperateType.DELETE_ADD, businessType = "用户所属岗位"),
            @KPObjectChangeLogNote(parentMapper = UserProjectMapper.class, identification = "userId,user_id", operateType = ObjectChangeLogOperateType.DELETE_ADD, businessType = "用户可操作项目")
    })
    @KPVerifyNote
    public KPResult<UserPO> update(@RequestBody UserEditParamPO userEditParamPO) {
        userService.updateUser(userEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/user/batch/remove','auth:user:batch:remove')")
    @Operation(summary = "批量删除用户信息", description = "权限 auth:user:batch:remove")
    @PostMapping("/batch/remove")
    @KPApiJsonParam({
            @KPJsonField(name = "ids", description = "用户Id", required = true, dataType = "array<string>")
    })
    @KPObjectChangeLogNote(parentMapper = UserMapper.class, identification = "userId,user_id", operateType = ObjectChangeLogOperateType.DELETE, businessType = "用户信息")
    public KPResult<String> batchRemove(@RequestBody List<String> ids) {
        return KPResult.success(userService.batchRemove(ids));
    }


    @PreAuthorize("hasPermission('/auth/user/forbidden','auth:user:forbidden')")
    @Operation(summary = "禁用或者取消禁用", description = "权限 auth:user:forbidden")
    @PostMapping(value = "/forbidden")
    @KPApiJsonParam({
            @KPJsonField(name = "userId", description = "用户Id", required = true, example = "5dbf7ae5e10272f8e1867cd25447720a")
    })
    @KPObjectChangeLogNote(parentMapper = UserMapper.class, identification = "userId,user_id", businessType = "用户信息")
    public KPResult<Void> doForbidden(@RequestBody JSONObject parameter) {
        userService.doForbidden(parameter);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/user/batch/cancel','auth:user:batch:cancel')")
    @Operation(summary = "批量注销", description = "权限 auth:user:batch:cancel")
    @PostMapping(value = "/batch/cancel")
    @KPApiJsonParam({
            @KPJsonField(name = "ids", description = "用户Id", required = true, dataType = "array<string>")
    })
    @KPObjectChangeLogNote(parentMapper = UserMapper.class, identification = "userId,user_id", operateType = ObjectChangeLogOperateType.UPDATE_BATCH, businessType = "用户信息")
    public KPResult<String> doCancel(@RequestBody List<String> ids) {
        return KPResult.success(userService.doCancel(ids));
    }


    @PreAuthorize("hasPermission('/auth/user/reset','auth:user:reset')")
    @Operation(summary = "管理员密码重置", description = "权限 auth:user:reset")
    @PostMapping(value = "/reset")
    @KPApiJsonParam({
            @KPJsonField(name = "userId", description = "用户Id", required = true, example = "5dbf7ae5e10272f8e1867cd25447720a"),
    })
    @KPObjectChangeLogNote(parentMapper = UserMapper.class, identification = "userId,user_id", businessType = "用户信息")
    public KPResult<Void> doReset(@RequestBody JSONObject parameter) {
        userService.doReset(parameter);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/user/unlock','auth:user:unlock')")
    @Operation(summary = "管理员手动解锁", description = "权限 auth:user:unlock")
    @PostMapping(value = "/unlock")
    @KPApiJsonParam({
            @KPJsonField(name = "userId", description = "用户Id", required = true, example = "5dbf7ae5e10272f8e1867cd25447720a")
    })
    @KPObjectChangeLogNote(parentMapper = UserMapper.class, identification = "userId,user_id", businessType = "用户信息")
    public KPResult<String> doUnlock(@RequestBody JSONObject parameter) {
        userService.doUnlock(parameter);
        return KPResult.success("解锁成功！");
    }


    @Operation(summary = "修改密码")
    @PostMapping(value = "/update/password")
    @KPApiJsonParam({
            @KPJsonField(name = "userId", description = "用户Id", required = true, example = "3830638e3a1f8771d96f71fce71e665b"),
            @KPJsonField(name = "oldPassword", description = "老密码", required = true),
            @KPJsonField(name = "newPassword", description = "新密码", required = true),
            @KPJsonField(name = "okPassword", description = "确认新密码", required = true)
    })
    @KPObjectChangeLogNote(parentMapper = UserMapper.class, identification = "userId,user_id", businessType = "用户信息")
    public KPResult<Void> updatePassword(@RequestBody JSONObject parameter) {
        userService.updatePassword(parameter);
        return KPResult.success();
    }


    @Operation(summary = "用户个人修改数据")
    @PostMapping(value = "/update/message")
    @KPApiJsonParam({
            @KPJsonField(name = "userId", description = "用户ID", required = true),
            @KPJsonField(name = "nickName", description = "用户昵称", required = true),
            @KPJsonField(name = "phoneNumber", description = "手机号", required = true),
            @KPJsonField(name = "sex", description = "性别", required = true),
            @KPJsonField(name = "email", description = "邮箱"),
            @KPJsonField(name = "avatar", description = "头像"),
    })
    @KPObjectChangeLogNote(parentMapper = UserMapper.class, identification = "userId,user_id", businessType = "用户信息")
    public KPResult<Void> updateMessage(@RequestBody JSONObject parameter) {
        userService.updateMessage(parameter);
        return KPResult.success();
    }
}
