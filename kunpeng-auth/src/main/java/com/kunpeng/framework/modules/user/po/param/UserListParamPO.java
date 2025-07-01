package com.kunpeng.framework.modules.user.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kunpeng.framework.entity.bo.PageBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 用户信息列表查询入参
 * @Date 2025-04-21
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "UserListParamPO对象", description = "用户信息列表查询入参")
public class UserListParamPO extends PageBO {

    @ApiModelProperty("用户账号")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty("工号")
    @TableField("job_number")
    private String jobNumber;

    @ApiModelProperty("姓名或昵称")
    @TableField("name")
    private String name;

    @ApiModelProperty("手机号码")
    @TableField("phone_number")
    private String phoneNumber;

    @ApiModelProperty("用户性别 1男 0女 2未知")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty("帐号状态 1正常 2禁用 3 锁定 4注销")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("用户状态 1实习 2 转正 3 离职")
    @TableField("user_status")
    private Integer userStatus;

    @ApiModelProperty("身份证")
    @TableField("id_card")
    private String idCard;

    @ApiModelProperty(value = "部门Id")
    @TableField("dept_id")
    private String deptId;

    @ApiModelProperty(value = "角色Id")
    @TableField(value = "role_id")
    private String roleId;
}
