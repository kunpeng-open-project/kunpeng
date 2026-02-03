package com.kp.framework.modules.project.po.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kp.framework.annotation.verify.KPMaxLength;
import com.kp.framework.annotation.verify.KPNotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 项目编辑入参。
 * @author lipeng
 * 2025-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "ProjectEditParamPO对象", description = "项目编辑入参")
public class ProjectEditParamPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目Id", example = "项目Id", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableId(value = "project_id", type = IdType.ASSIGN_UUID)
    @KPNotNull(errMeg = "请输入项目Id")
    @KPMaxLength(max = 36, errMeg = "项目Id不能超过36个字符")
    private String projectId;

    @Schema(description = "项目名称", example = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("project_name")
    @KPNotNull(errMeg = "请输入项目名称")
    @KPMaxLength(max = 68, errMeg = "项目名称不能超过68个字符")
    private String projectName;

    @Schema(description = "项目编号", example = "项目编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("project_code")
    @KPNotNull(errMeg = "请输入项目编号")
    @KPMaxLength(max = 68, errMeg = "项目编号不能超过68个字符")
    private String projectCode;

    @Schema(description = "项目地址", example = "项目地址")
    @TableField("project_url")
    @KPMaxLength(max = 255, errMeg = "项目地址不能超过255个字符")
    private String projectUrl;

    @Schema(description = "项目状态 0停用 1正常", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("status")
    @KPNotNull(errMeg = "请输入项目状态 0停用 1正常")
    private Integer status;

    @Schema(description = "管理状态 0不管理 1管理", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("manage")
    @KPNotNull(errMeg = "请输入管理状态 0不管理 1管理")
    private Integer manage;

    @Schema(description = "token 过期时间 单位小时", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("token_failure")
    @KPNotNull(errMeg = "请输入token 过期时间 单位小时")
    private Integer tokenFailure;

    @Schema(description = "token 获取最大次数", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("token_gain_max_num")
    @KPNotNull(errMeg = "请输入token 获取最大次数")
    private Integer tokenGainMaxNum;

    @Schema(description = "备注")
    @TableField("remark")
    @KPMaxLength(max = 255, errMeg = "备注不能超过255个字符")
    private String remark;
}
