package com.kunpeng.framework.modules.user.po;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kunpeng.framework.common.parent.ParentSecurityBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author lipeng
 * @since 2024-04-19
 */
@Data
@TableName("auth_user")
public class AuthUserPO extends ParentSecurityBO<AuthUserPO> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户Id")
    @TableId(value = "user_id", type = IdType.ASSIGN_UUID)
    private String userId;

    @ApiModelProperty("用户账号")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty("密码")
    @TableField("password")
    @JSONField(serialize = false)
    private String password;

    @ApiModelProperty("工号")
    @TableField("job_number")
    private String jobNumber;

    @ApiModelProperty("真实姓名")
    @TableField("real_name")
    private String realName;

    @ApiModelProperty("用户昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty("用户邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty("手机号码")
    @TableField("phone_number")
    private String phoneNumber;

    @ApiModelProperty("用户性别 1男 0女 2未知")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty("头像地址")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("帐号状态 1正常 2禁用 3 锁定 4注销")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("用户状态 1实习 2 转正 3 离职")
    @TableField("user_status")
    private Integer userStatus;

    @ApiModelProperty("身份证")
    @TableField("id_card")
    private String idCard;

    @ApiModelProperty("入职时间")
    @TableField("entry_date")
    private LocalDate entryDate;

    @ApiModelProperty("转正时间")
    @TableField("official_date")
    private LocalDate officialDate;

    @ApiModelProperty("离职时间")
    @TableField(value = "dimission_date", updateStrategy = FieldStrategy.IGNORED)
    private LocalDate dimissionDate;

    @ApiModelProperty("数据来源")
    @TableField("source")
    @JSONField(serialize = false)
    private String source;

    @ApiModelProperty("最后登陆IP")
    @TableField("login_ip")
    private String loginIp;

    @ApiModelProperty("最后登陆时间")
    @TableField("login_date")
    private LocalDateTime loginDate;

    @ApiModelProperty("锁定时间")
    @TableField("lock_date")
    @JSONField(serialize = false)
    private LocalDateTime lockDate;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
