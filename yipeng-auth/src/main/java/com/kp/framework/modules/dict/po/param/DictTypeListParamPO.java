package com.kp.framework.modules.dict.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.entity.bo.PageBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 字典类型列表查询入参。
 * @author lipeng
 * 2025-07-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "DictTypeListParamPO对象", description = "字典类型列表查询入参")
public class DictTypeListParamPO extends PageBO {

    @Schema(description = "项目Id")
    @TableField("project_id")
    private String projectId;

    @Schema(description = "字典名称")
    @TableField("dict_name")
    private String dictName;

    @Schema(description = "字典类型")
    @TableField("dict_type")
    private String dictType;

    @Schema(description = "状态 0 停用 1 正常")
    @TableField("status")
    private Integer status;
}
