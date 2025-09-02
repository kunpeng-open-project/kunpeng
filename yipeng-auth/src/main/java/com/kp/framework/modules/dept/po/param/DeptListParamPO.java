package com.kp.framework.modules.dept.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 部门信息列表查询入参
 * @Date 2025-04-08
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "DeptListParamPO对象", description = "部门信息列表查询入参")
public class DeptListParamPO {

    @ApiModelProperty("部门名称")
    @TableField("dept_name")
    private String deptName;

    @ApiModelProperty("部门状态 0停用 1正常")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("数据来源")
    @TableField("source")
    private String source;

    @ApiModelProperty(value = "是否树形结构 1 树形 2 列表", required = true, example = "1")
    private Integer isTree = 2;

    @ApiModelProperty(value = "排序规则 如 id desc, name asc", required = false, example = "")
    @TableField(exist = false)
    private String orderBy;
}
