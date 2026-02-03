package com.kp.framework.modules.project.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.entity.bo.PageBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 项目列表查询入参。
 * @author lipeng
 * 2025-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "ProjectListParamPO对象", description = "项目列表查询入参")
public class ProjectListParamPO extends PageBO {

    @Schema(description = "项目名称")
    @TableField("project_name")
    private String projectName;

    @Schema(description = "项目编号")
    @TableField("project_code")
    private String projectCode;

    @Schema(description = "项目状态 0停用 1正常")
    @TableField("status")
    private Integer status;

    @Schema(description = "管理状态 0不管理 1管理")
    @TableField("manage")
    private Integer manage;

    @Schema(description = "appId")
    @TableField("app_id")
    private String appId;
}
