package com.kunpeng.framework.modules.project.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kunpeng.framework.entity.bo.PageBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 项目列表查询入参
 * @Date 2025-03-14 17:19:50
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "ProjectListParamPO对象", description = "项目列表查询入参")
public class ProjectListParamPO extends PageBO {

    @ApiModelProperty("项目名称")
    @TableField("project_name")
    private String projectName;

    @ApiModelProperty("项目编号")
    @TableField("project_code")
    private String projectCode;

    @ApiModelProperty("项目状态 0停用 1正常")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("管理状态 0不管理 1管理")
    @TableField("manage")
    private Integer manage;

    @ApiModelProperty("appId")
    @TableField("app_id")
    private String appId;
}
