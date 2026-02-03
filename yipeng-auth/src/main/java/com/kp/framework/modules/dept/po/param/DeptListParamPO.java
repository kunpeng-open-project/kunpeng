package com.kp.framework.modules.dept.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 部门信息列表查询入参。
 * @author lipeng
 * 2025-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "DeptListParamPO对象", description = "部门信息列表查询入参")
public class DeptListParamPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "部门名称")
    @TableField("dept_name")
    private String deptName;

    @Schema(description = "部门状态 0停用 1正常")
    @TableField("status")
    private Integer status;

    @Schema(description = "数据来源")
    @TableField("source")
    private String source;

    @Schema(description = "是否树形结构 1 树形 2 列表", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer isTree = 2;

    @Schema(description = "排序规则 如 id desc, name asc")
    @TableField(exist = false)
    private String orderBy;
}
