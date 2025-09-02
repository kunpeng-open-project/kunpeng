package com.kp.framework.modules.dict.po.param;

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
 * @Description 字典数据编辑入参
 * @Date 2025-07-03
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "DictDataEditParamPO对象", description = "字典数据编辑入参")
public class DictDataEditParamPO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典编码ID", example = "字典编码ID", required = true)
    @TableId(value = "dict_data_id", type = IdType.ASSIGN_UUID)
    @KPNotNull(errMeg = "请输入字典编码ID")
    @KPMaxLength(max = 36, errMeg = "字典编码ID不能超过36个字符")
    private String dictDataId;

    @ApiModelProperty(value = "字典类型ID", example = "字典类型ID", required = true)
    @TableField("dict_type_id")
    @KPNotNull(errMeg = "请输入字典类型ID")
    @KPMaxLength(max = 36, errMeg = "字典类型ID不能超过36个字符")
    private String dictTypeId;

    @ApiModelProperty(value = "字典标签", example = "字典标签", required = true)
    @TableField("label")
    @KPNotNull(errMeg = "请输入字典标签")
    @KPMaxLength(max = 100, errMeg = "字典标签不能超过100个字符")
    private String label;

    @ApiModelProperty(value = "字典键值", example = "字典键值", required = true)
    @TableField("value")
    @KPNotNull(errMeg = "请输入字典键值")
    @KPMaxLength(max = 100, errMeg = "字典键值不能超过100个字符")
    private String value;

    @ApiModelProperty(value = "是否默认选中 1是 0否", example = "0", required = true)
    @TableField("selected")
    @KPNotNull(errMeg = "请输入是否默认选中 1是 0否")
    private Integer selected;

    @ApiModelProperty(value = "状态 0 停用 1 正常", example = "0", required = true)
    @TableField("status")
    @KPNotNull(errMeg = "请选择状态 0 停用 1 正常")
    private Integer status;

    @ApiModelProperty(value = "备注", example = "备注", required = true)
    @TableField("remark")
    @KPMaxLength(max = 255, errMeg = "备注不能超过255个字符")
    private String remark;
}
