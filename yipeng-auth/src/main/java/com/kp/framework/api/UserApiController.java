package com.kp.framework.api;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonlParam;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.user.po.UserPO;
import com.kp.framework.modules.user.po.customer.UserListCustomerPO;
import com.kp.framework.modules.user.po.param.UserListParamPO;
import com.kp.framework.modules.user.service.UserService;
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
@RequestMapping("/api/user")
@Api(tags = "API-用户相关接口", value = "API-用户相关接口")
@ApiSupport(order = 0)
public class UserApiController {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "查询用户信息分页列表")
    @PostMapping("/page/list")
    @KPVerifyNote
    public KPResult<UserListCustomerPO> queryPageList(@RequestBody UserListParamPO userListParamPO) {
        return KPResult.list(userService.queryPageList(userListParamPO));
    }


    @ApiOperation(value = "根据用户id集合查询用户列表")
    @PostMapping("/user/ids/list")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "userIds", value = "用户Id集合", required = true, dataType = "list")
    })
    public KPResult<UserPO> queryUserIdList(@RequestBody List<String> userIds) {
        return KPResult.list(userService.queryUserIdList(userIds));
    }
}
