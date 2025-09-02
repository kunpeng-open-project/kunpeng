package com.kp.framework.modules.dept.po.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kp.framework.annotation.verify.KPMaxLength;
import com.kp.framework.annotation.verify.KPNotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "DeptSortParamPO", description = "DeptSortParamPO")
public class DeptSortParamPO {

    @ApiModelProperty(value = "部门Id", required = true)
    @TableId(value = "dept_id", type = IdType.ASSIGN_UUID)
    @KPNotNull(errMeg = "请输入部门Id")
    @KPMaxLength(max = 36, errMeg = "部门Id不能超过36个字符")
    private String deptId;

    @ApiModelProperty(value="显示顺序", required = true)
    @TableField("sort")
    @KPNotNull(errMeg = "请输入显示顺序")
    private Integer sort;
}
