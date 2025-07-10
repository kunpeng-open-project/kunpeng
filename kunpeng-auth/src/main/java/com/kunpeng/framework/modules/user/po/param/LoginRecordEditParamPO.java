package com.kunpeng.framework.modules.user.po.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kunpeng.framework.annotation.verify.KPMaxLength;
import com.kunpeng.framework.annotation.verify.KPNotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 用户登录记录编辑入参
 * @Date 2025-06-10
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "LoginRecordEditParamPO对象", description = "用户登录记录编辑入参")
public class LoginRecordEditParamPO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登录记录id", example = "登录记录id", required = true)
    @TableId(value = "alr_id", type = IdType.ASSIGN_UUID)
    @KPNotNull(errMeg = "请输入登录记录id")
    @KPMaxLength(max = 36, errMeg = "登录记录id不能超过36个字符")
    private String alrId;

    @ApiModelProperty(value = "用户Id", example = "用户Id", required = true)
    @TableField("user_id")
    @KPNotNull(errMeg = "请输入用户Id")
    @KPMaxLength(max = 36, errMeg = "用户Id不能超过36个字符")
    private String userId;

    @ApiModelProperty(value = "用户账号 1 用户的工号 2 appid", example = "用户账号 1 用户的工号 2 appid", required = true)
    @TableField("user_name")
    @KPNotNull(errMeg = "请输入用户账号 1 用户的工号 2 appid")
    @KPMaxLength(max = 68, errMeg = "用户账号 1 用户的工号 2 appid不能超过68个字符")
    private String userName;

    @ApiModelProperty(value = "登录类型 1账号登录 2 授权登录 3免密登录", example = "0", required = true)
    @TableField("login_type")
    @KPNotNull(errMeg = "请输入登录类型 1账号登录 2 授权登录 3免密登录")
    private Integer loginType;

    @ApiModelProperty(value = "登录的项目", example = "登录的项目", required = true)
    @TableField("project_id")
    @KPNotNull(errMeg = "请输入登录的项目")
    @KPMaxLength(max = 36, errMeg = "登录的项目不能超过36个字符")
    private String projectId;

    @ApiModelProperty(value = "登录浏览器信息", example = "登录浏览器信息", required = true)
    @TableField("user_agent")
    @KPNotNull(errMeg = "请输入登录浏览器信息")
    @KPMaxLength(max = 500, errMeg = "登录浏览器信息不能超过500个字符")
    private String userAgent;

    @ApiModelProperty(value = "用户操作来源", example = "用户操作来源", required = true)
    @TableField("user_referer")
    @KPNotNull(errMeg = "请输入用户操作来源")
    @KPMaxLength(max = 255, errMeg = "用户操作来源不能超过255个字符")
    private String userReferer;

    @ApiModelProperty(value = "用户代理平台", example = "用户代理平台", required = true)
    @TableField("user_plat_form")
    @KPNotNull(errMeg = "请输入用户代理平台")
    @KPMaxLength(max = 255, errMeg = "用户代理平台不能超过255个字符")
    private String userPlatForm;

    @ApiModelProperty(value = "登录IP", example = "登录IP", required = true)
    @TableField("login_ip")
    @KPNotNull(errMeg = "请输入登录IP")
    @KPMaxLength(max = 128, errMeg = "登录IP不能超过128个字符")
    private String loginIp;

    @ApiModelProperty(value = "登录结果", example = "登录结果", required = true)
    @TableField("login_result")
    @KPNotNull(errMeg = "请输入登录结果")
    @KPMaxLength(max = 68, errMeg = "登录结果不能超过68个字符")
    private String loginResult;
}
