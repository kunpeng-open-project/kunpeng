package com.kunpeng.framework.modules.project.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kunpeng.framework.common.parent.ParentSecurityBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="AuthProjectPO", description="AuthProjectPO")
public class AuthProjectPO extends ParentSecurityBO<AuthProjectPO> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("项目Id")
    @TableId(value = "project_id", type = IdType.ASSIGN_UUID)
    private String projectId;

    @ApiModelProperty("项目名称")
    @TableField("project_name")
    private String projectName;

    @ApiModelProperty("项目编号")
    @TableField("project_code")
    private String projectCode;

    @ApiModelProperty("项目地址")
    @TableField("project_url")
    private String projectUrl;

    @ApiModelProperty("项目状态 0停用 1正常")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("管理状态 0不管理 1管理")
    @TableField("manage")
    private Integer manage;

    @ApiModelProperty("appId")
    @TableField("app_id")
    private String appId;

    @ApiModelProperty("appSecret")
    @TableField("app_secret")
    private String appSecret;

    @ApiModelProperty("token 过期时间 单位小时")
    @TableField("token_failure")
    private Integer tokenFailure;

    @ApiModelProperty("token 获取最大次数")
    @TableField("token_gain_max_num")
    private Integer tokenGainMaxNum;

    @ApiModelProperty("认证凭证")
    @TableField("voucher")
    private String voucher;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
