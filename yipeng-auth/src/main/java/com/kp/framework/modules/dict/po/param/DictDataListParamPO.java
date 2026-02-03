package com.kp.framework.modules.dict.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.entity.bo.PageBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 字典数据列表查询入参。
 * @author lipeng
 * 2025-07-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "DictDataListParamPO对象", description = "字典数据列表查询入参")
public class DictDataListParamPO extends PageBO {

    @Schema(description = "字典类型ID")
    @TableField("dict_type_id")
    private String dictTypeId;

    @Schema(description = "字典标签")
    @TableField("label")
    private String label;

    @Schema(description = "状态 0 停用 1 正常")
    @TableField("status")
    private Integer status;
}
