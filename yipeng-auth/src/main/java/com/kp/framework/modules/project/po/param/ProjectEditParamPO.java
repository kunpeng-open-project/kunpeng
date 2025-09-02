package com.kp.framework.modules.project.po.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kp.framework.annotation.verify.KPMaxLength;
import com.kp.framework.annotation.verify.KPNotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 项目编辑入参
 * @Date 2025-03-14
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "ProjectEditParamPO对象", description = "项目编辑入参")
public class ProjectEditParamPO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目Id", example = "项目Id", required = true)
    @TableId(value = "project_id", type = IdType.ASSIGN_UUID)
    @KPNotNull(errMeg = "请输入项目Id")
    @KPMaxLength(max = 36, errMeg = "项目Id不能超过36个字符")
    private String projectId;

    @ApiModelProperty(value = "项目名称", example = "项目名称", required = true)
    @TableField("project_name")
    @KPNotNull(errMeg = "请输入项目名称")
    @KPMaxLength(max = 68, errMeg = "项目名称不能超过68个字符")
    private String projectName;

    @ApiModelProperty(value = "项目编号", example = "项目编号", required = true)
    @TableField("project_code")
    @KPNotNull(errMeg = "请输入项目编号")
    @KPMaxLength(max = 68, errMeg = "项目编号不能超过68个字符")
    private String projectCode;

    @ApiModelProperty(value = "项目地址", example = "项目地址")
    @TableField("project_url")
    @KPMaxLength(max = 255, errMeg = "项目地址不能超过255个字符")
    private String projectUrl;

    @ApiModelProperty(value = "项目状态 0停用 1正常", example = "0", required = true)
    @TableField("status")
    @KPNotNull(errMeg = "请输入项目状态 0停用 1正常")
    private Integer status;

    @ApiModelProperty(value = "管理状态 0不管理 1管理", example = "0", required = true)
    @TableField("manage")
    @KPNotNull(errMeg = "请输入管理状态 0不管理 1管理")
    private Integer manage;

    @ApiModelProperty(value = "token 过期时间 单位小时", example = "0", required = true)
    @TableField("token_failure")
    @KPNotNull(errMeg = "请输入token 过期时间 单位小时")
    private Integer tokenFailure;

    @ApiModelProperty(value = "token 获取最大次数", example = "0", required = true)
    @TableField("token_gain_max_num")
    @KPNotNull(errMeg = "请输入token 获取最大次数")
    private Integer tokenGainMaxNum;

    @ApiModelProperty("备注")
    @TableField("remark")
    @KPMaxLength(max = 255, errMeg = "备注不能超过255个字符")
    private String remark;
}
