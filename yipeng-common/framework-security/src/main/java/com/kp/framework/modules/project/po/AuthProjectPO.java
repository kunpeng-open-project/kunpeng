package com.kp.framework.modules.project.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kp.framework.common.parent.ParentSecurityBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 项目表
 * </p>
 *
 * @author lipeng
 * @since 2024-07-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("auth_project")
@Schema(name = "AuthProjectPO", description = "AuthProjectPO")
public class AuthProjectPO extends ParentSecurityBO<AuthProjectPO> {

    @Schema(description = "项目Id")
    @TableId(value = "project_id", type = IdType.ASSIGN_UUID)
    private String projectId;

    @Schema(description = "项目名称")
    @TableField("project_name")
    private String projectName;

    @Schema(description = "项目编号")
    @TableField("project_code")
    private String projectCode;

    @Schema(description = "项目地址")
    @TableField("project_url")
    private String projectUrl;

    @Schema(description = "项目状态 0停用 1正常")
    @TableField("status")
    private Integer status;

    @Schema(description = "管理状态 0不管理 1管理")
    @TableField("manage")
    private Integer manage;

    @Schema(description = "appId")
    @TableField("app_id")
    private String appId;

    @Schema(description = "appSecret")
    @TableField("app_secret")
    private String appSecret;

    @Schema(description = "token 过期时间 单位小时")
    @TableField("token_failure")
    private Integer tokenFailure;

    @Schema(description = "token 获取最大次数")
    @TableField("token_gain_max_num")
    private Integer tokenGainMaxNum;

    @Schema(description = "认证凭证")
    @TableField("voucher")
    private String voucher;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
