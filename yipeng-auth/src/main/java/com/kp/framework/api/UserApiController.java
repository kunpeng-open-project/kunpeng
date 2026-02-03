package com.kp.framework.api;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.user.po.UserPO;
import com.kp.framework.modules.user.po.customer.UserListCustomerPO;
import com.kp.framework.modules.user.po.param.UserListParamPO;
import com.kp.framework.modules.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/user")
@Tag(name = "API-用户相关接口")
@ApiSupport(author = "lipeng", order = 10)
public class UserApiController {

    @Autowired
    private UserService userService;

    @Operation(summary = "查询用户信息分页列表")
    @PostMapping("/page/list")
    @KPVerifyNote
    public KPResult<UserListCustomerPO> queryPageList(@RequestBody UserListParamPO userListParamPO) {
        return userService.queryPageList(userListParamPO);
    }


    @Operation(summary = "根据用户id集合查询用户列表")
    @PostMapping("/ids/list")
    @KPApiJsonParam({
            @KPJsonField(name = "userIds", description = "用户Id集合", required = true, dataType = "array<String>")
    })
    public KPResult<List<UserPO>> queryUserIdList(@RequestBody List<String> userIds) {
        return KPResult.success(userService.queryUserIdList(userIds));
    }
}
