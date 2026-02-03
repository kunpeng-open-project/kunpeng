package com.kp.framework.modules.user.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kp.framework.entity.bo.ParentBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户登录记录表。
 * @author lipeng
 * 2025-07-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("auth_login_record")
@Schema(name = "LoginRecordPO", description = "用户登录记录表")
public class LoginRecordPO extends ParentBO<LoginRecordPO> {

    @Schema(description = "登录记录id")
    @TableId(value = "alr_id", type = IdType.ASSIGN_UUID)
    private String alrId;

    @Schema(description = "用户Id")
    @TableField("user_id")
    private String userId;

    @Schema(description = "用户账号 1 用户的工号 2 appid")
    @TableField("user_name")
    private String userName;

    @Schema(description = "登录类型 1账号登录 2 授权登录 3免密登录")
    @TableField("login_type")
    private Integer loginType;

    @Schema(description = "登录的项目")
    @TableField("project_id")
    private String projectId;

    @Schema(description = "登录浏览器信息")
    @TableField("user_agent")
    private String userAgent;

    @Schema(description = "用户操作来源")
    @TableField("user_referer")
    private String userReferer;

    @Schema(description = "用户代理平台")
    @TableField("user_plat_form")
    private String userPlatForm;

    @Schema(description = "登录IP")
    @TableField("login_ip")
    private String loginIp;

    @Schema(description = "登录IP地址")
    @TableField("login_ip_address")
    private String loginIpAddress;

    @Schema(description = "登录结果")
    @TableField("login_result")
    private String loginResult;
}
