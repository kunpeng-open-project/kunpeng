package com.kp.framework.modules.dict.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kp.framework.entity.bo.ParentBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 字典数据表。
 * @author lipeng
 * 2025-07-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("auth_dict_data")
@Schema(name = "DictDataPO对象", description = "字典数据表")
public class DictDataPO extends ParentBO<DictDataPO> {

    @Schema(description = "字典编码ID")
    @TableId(value = "dict_data_id", type = IdType.ASSIGN_UUID)
    private String dictDataId;

    @Schema(description = "字典类型ID")
    @TableField("dict_type_id")
    private String dictTypeId;

    @Schema(description = "字典排序")
    @TableField("sort")
    private Integer sort;

    @Schema(description = "字典标签")
    @TableField("label")
    private String label;

    @Schema(description = "字典键值")
    @TableField("value")
    private String value;

    @Schema(description = "是否默认选中 1是 0否")
    @TableField("selected")
    private Integer selected;

    @Schema(description = "状态 0 停用 1 正常")
    @TableField("status")
    private Integer status;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
