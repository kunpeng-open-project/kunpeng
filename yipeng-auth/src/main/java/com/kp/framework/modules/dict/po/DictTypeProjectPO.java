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
 * 字典类型项目关联表。
 * @author lipeng
 * 2025-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("auth_dict_type_project")
@Schema(name = "DictTypeProjectPO对象", description = "字典类型表项目关联表")
public class DictTypeProjectPO extends ParentBO<DictTypeProjectPO> {

    @Schema(description = "字典类型表项目Id")
    @TableId(value = "adtp_id", type = IdType.ASSIGN_UUID)
    private String adtpId;

    @Schema(description = "字典类型ID")
    @TableField("dict_type_id")
    private String dictTypeId;

    @Schema(description = "项目Id")
    @TableField("project_id")
    private String projectId;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
