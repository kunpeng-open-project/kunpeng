package com.kp.framework.modules.user.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.entity.bo.PageBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 用户信息列表查询入参。
 * @author lipeng
 * 2025-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "UserListParamPO", description = "用户信息表列表查询入参")
public class UserListParamPO extends PageBO {

    @Schema(description = "用户账号")
    @TableField("user_name")
    private String userName;

    @Schema(description = "屏蔽的用户id集合")
    private List<String> neUserIds;

    @Schema(description = "查询的用户id集合")
    private List<String> eqUserIds;

    @Schema(description = "工号")
    @TableField("job_number")
    private String jobNumber;

    @Schema(description = "姓名或昵称")
    @TableField("name")
    private String name;

    @Schema(description = "手机号码")
    @TableField("phone_number")
    private String phoneNumber;

    @Schema(description = "用户性别 1男 0女 2未知")
    @TableField("sex")
    private Integer sex;

    @Schema(description = "帐号状态 1正常 2禁用 3 锁定 4注销")
    @TableField("status")
    private Integer status;

    @Schema(description = "用户状态 1实习 2 转正 3 离职")
    @TableField("user_status")
    private Integer userStatus;

    @Schema(description = "身份证")
    @TableField("id_card")
    private String idCard;

    @Schema(description = "部门Id")
    @TableField("dept_id")
    private String deptId;

    @Schema(description = "角色Id")
    @TableField(value = "role_id")
    private String roleId;
}
