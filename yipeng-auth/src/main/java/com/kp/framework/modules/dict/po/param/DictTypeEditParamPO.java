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
import java.util.List;

/**
 * 字典类型编辑入参。
 * @author lipeng
 * 2025-07-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "DictTypeEditParamPO对象", description = "字典类型编辑入参")
public class DictTypeEditParamPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "字典类型ID", example = "字典类型ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableId(value = "dict_type_id", type = IdType.ASSIGN_UUID)
    @KPNotNull(errMeg = "请输入字典类型ID")
    @KPMaxLength(max = 36, errMeg = "字典类型ID不能超过36个字符")
    private String dictTypeId;

    @Schema(description = "项目Id")
    @TableField("project_id")
    @KPNotNull(errMeg = "请选择项目")
    private List<String> projectIds;

    @Schema(description = "字典名称", example = "字典名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("dict_name")
    @KPNotNull(errMeg = "请输入字典名称")
    @KPMaxLength(max = 100, errMeg = "字典名称不能超过100个字符")
    private String dictName;

    @Schema(description = "字典类型", example = "字典类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("dict_type")
    @KPNotNull(errMeg = "请输入字典类型")
    @KPMaxLength(max = 100, errMeg = "字典类型不能超过100个字符")
    private String dictType;

    @Schema(description = "状态 0 停用 1 正常", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("status")
    @KPNotNull(errMeg = "请选择状态 0 停用 1 正常")
    private Integer status;

    @Schema(description = "备注", example = "备注", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("remark")
    @KPMaxLength(max = 255, errMeg = "备注不能超过255个字符")
    private String remark;
}
