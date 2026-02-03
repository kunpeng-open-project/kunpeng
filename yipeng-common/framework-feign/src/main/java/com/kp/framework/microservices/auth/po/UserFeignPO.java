package com.kp.framework.microservices.auth.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kp.framework.entity.bo.ParentBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户信息表。
 * @author lipeng
 * 2025-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "UserFeignPO", description = "用户信息表")
public class UserFeignPO extends ParentBO<UserFeignPO> {

    @Schema(description = "用户Id")
    @TableId(value = "user_id", type = IdType.ASSIGN_UUID)
    private String userId;

    @Schema(description = "用户账号")
    @TableField("user_name")
    private String userName;

    @Schema(description = "密码")
    @TableField("password")
    private String password;

    @Schema(description = "工号")
    @TableField("job_number")
    private String jobNumber;

    @Schema(description = "真实姓名")
    @TableField("real_name")
    private String realName;

    @Schema(description = "用户昵称")
    @TableField("nick_name")
    private String nickName;

    @Schema(description = "用户邮箱")
    @TableField("email")
    private String email;

    @Schema(description = "手机号码")
    @TableField("phone_number")
    private String phoneNumber;

    @Schema(description = "用户性别 1男 0女 2未知")
    @TableField("sex")
    private Integer sex;

    @Schema(description = "头像地址")
    @TableField("avatar")
    private String avatar;

    @Schema(description = "帐号状态 1正常 2禁用 3 锁定 4注销")
    @TableField("status")
    private Integer status;

    @Schema(description = "用户状态 1实习 2 转正 3 离职")
    @TableField("user_status")
    private Integer userStatus;

    @Schema(description = "身份证")
    @TableField("id_card")
    private String idCard;

    @Schema(description = "入职时间")
    @TableField("entry_date")
    private LocalDate entryDate;

    @Schema(description = "转正时间")
    @TableField("official_date")
    private LocalDate officialDate;

    @Schema(description = "离职时间")
    @TableField(value = "dimission_date", updateStrategy = FieldStrategy.IGNORED)
    private LocalDate dimissionDate;

    @Schema(description = "数据来源")
    @TableField("source")
    private String source;

    @Schema(description = "最后登陆IP")
    @TableField("login_ip")
    private String loginIp;

    @Schema(description = "最后登陆时间")
    @TableField("login_date")
    private LocalDateTime loginDate;

    @Schema(description = "锁定时间")
    @TableField("lock_date")
    private LocalDateTime lockDate;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
