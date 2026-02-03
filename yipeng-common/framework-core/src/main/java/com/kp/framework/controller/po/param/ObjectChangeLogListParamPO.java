package com.kp.framework.controller.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.entity.bo.PageBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 列表查询入参。
 * @author lipeng
 * 2025-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "ObjectChangeLogListParamPO对象", description = "列表查询入参")
public class ObjectChangeLogListParamPO extends PageBO {

    @Schema(description = "项目名称")
    @TableField("project_name")
    private String projectName;

    @Schema(description = "业务类型")
    @TableField("business_type")
    private String businessType;

    @Schema(description = "标识 唯一外键（业务通过这个字段关联）")
    @TableField("identification")
    private String identification;
}
