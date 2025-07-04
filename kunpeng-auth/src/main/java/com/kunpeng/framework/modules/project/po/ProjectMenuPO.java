package com.kunpeng.framework.modules.project.po;

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
 * @Description 项目菜单关联表
 * @Date 2025-05-16
**/
@Data
@Accessors(chain = true)
@TableName("auth_project_menu")
@ApiModel(value = "ProjectMenuPO对象", description = "项目菜单关联表")
public class ProjectMenuPO extends ParentBO {

    @ApiModelProperty("项目菜单Id")
    @TableId(value = "apm_id", type = IdType.ASSIGN_UUID)
    private String apmId;

    @ApiModelProperty("菜单Id")
    @TableField("menu_id")
    private String menuId;

    @ApiModelProperty("项目Id")
    @TableField("project_id")
    private String projectId;

    @ApiModelProperty("权限项目Id")
    @TableField("purview_project_id")
    private String purviewProjectId;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
