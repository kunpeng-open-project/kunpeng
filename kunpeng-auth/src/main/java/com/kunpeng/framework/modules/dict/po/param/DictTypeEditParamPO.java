package com.kunpeng.framework.modules.dict.po.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kunpeng.framework.annotation.verify.KPMaxLength;
import com.kunpeng.framework.annotation.verify.KPNotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author lipeng
 * @Description 字典类型编辑入参
 * @Date 2025-07-03
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "DictTypeEditParamPO对象", description = "字典类型编辑入参")
public class DictTypeEditParamPO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典类型ID", example = "字典类型ID", required = true)
    @TableId(value = "dict_type_id", type = IdType.ASSIGN_UUID)
    @KPNotNull(errMeg = "请输入字典类型ID")
    @KPMaxLength(max = 36, errMeg = "字典类型ID不能超过36个字符")
    private String dictTypeId;

    @ApiModelProperty("项目Id")
    @TableField("project_id")
    @KPNotNull(errMeg = "请选择项目")
    private List<String> projectIds;

    @ApiModelProperty(value = "字典名称", example = "字典名称", required = true)
    @TableField("dict_name")
    @KPNotNull(errMeg = "请输入字典名称")
    @KPMaxLength(max = 100, errMeg = "字典名称不能超过100个字符")
    private String dictName;

    @ApiModelProperty(value = "字典类型", example = "字典类型", required = true)
    @TableField("dict_type")
    @KPNotNull(errMeg = "请输入字典类型")
    @KPMaxLength(max = 100, errMeg = "字典类型不能超过100个字符")
    private String dictType;

    @ApiModelProperty(value = "状态 0 停用 1 正常", example = "0", required = true)
    @TableField("status")
    @KPNotNull(errMeg = "请选择状态 0 停用 1 正常")
    private Integer status;

    @ApiModelProperty(value = "备注", example = "备注", required = true)
    @TableField("remark")
    @KPMaxLength(max = 255, errMeg = "备注不能超过255个字符")
    private String remark;
}
