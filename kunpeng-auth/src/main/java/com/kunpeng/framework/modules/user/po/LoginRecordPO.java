package com.kunpeng.framework.modules.user.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kunpeng.framework.entity.bo.ParentBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 用户登录记录表
 * @Date 2025-07-15
**/
@Data
@Accessors(chain = true)
@TableName("auth_login_record")
@ApiModel(value = "LoginRecordPO对象", description = "用户登录记录表")
public class LoginRecordPO extends ParentBO {

    @ApiModelProperty("登录记录id")
    @TableId(value = "alr_id", type = IdType.ASSIGN_UUID)
    private String alrId;

    @ApiModelProperty("用户Id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("用户账号 1 用户的工号 2 appid")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty("登录类型 1账号登录 2 授权登录 3免密登录")
    @TableField("login_type")
    private Integer loginType;

    @ApiModelProperty("登录的项目")
    @TableField("project_id")
    private String projectId;

    @ApiModelProperty("登录浏览器信息")
    @TableField("user_agent")
    private String userAgent;

    @ApiModelProperty("用户操作来源")
    @TableField("user_referer")
    private String userReferer;

    @ApiModelProperty("用户代理平台")
    @TableField("user_plat_form")
    private String userPlatForm;

    @ApiModelProperty("登录IP")
    @TableField("login_ip")
    private String loginIp;

    @ApiModelProperty("登录IP地址")
    @TableField("login_ip_address")
    private String loginIpAddress;

    @ApiModelProperty("登录结果")
    @TableField("login_result")
    private String loginResult;
}
