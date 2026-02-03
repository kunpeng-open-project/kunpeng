package com.kp.framework.modules.dict.po.param;

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
 * 字典数据编辑入参。
 * @author lipeng
 * 2025-07-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "DictDataEditParamPO对象", description = "字典数据编辑入参")
public class DictDataEditParamPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "字典编码ID", example = "字典编码ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableId(value = "dict_data_id", type = IdType.ASSIGN_UUID)
    @KPNotNull(errMeg = "请输入字典编码ID")
    @KPMaxLength(max = 36, errMeg = "字典编码ID不能超过36个字符")
    private String dictDataId;

    @Schema(description = "字典类型ID", example = "字典类型ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("dict_type_id")
    @KPNotNull(errMeg = "请输入字典类型ID")
    @KPMaxLength(max = 36, errMeg = "字典类型ID不能超过36个字符")
    private String dictTypeId;

    @Schema(description = "字典标签", example = "字典标签", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("label")
    @KPNotNull(errMeg = "请输入字典标签")
    @KPMaxLength(max = 100, errMeg = "字典标签不能超过100个字符")
    private String label;

    @Schema(description = "字典键值", example = "字典键值", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("value")
    @KPNotNull(errMeg = "请输入字典键值")
    @KPMaxLength(max = 100, errMeg = "字典键值不能超过100个字符")
    private String value;

    @Schema(description = "是否默认选中 1是 0否", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("selected")
    @KPNotNull(errMeg = "请输入是否默认选中 1是 0否")
    private Integer selected;

    @Schema(description = "状态 0 停用 1 正常", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("status")
    @KPNotNull(errMeg = "请选择状态 0 停用 1 正常")
    private Integer status;

    @Schema(description = "备注", example = "备注", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("remark")
    @KPMaxLength(max = 255, errMeg = "备注不能超过255个字符")
    private String remark;
}
