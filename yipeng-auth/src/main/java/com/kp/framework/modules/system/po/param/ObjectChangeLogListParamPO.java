package com.kp.framework.modules.system.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.entity.bo.PageBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 列表查询入参
 * @Date 2025-11-10 16:34:05
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "ObjectChangeLogListParamPO对象", description = "列表查询入参")
public class ObjectChangeLogListParamPO extends PageBO {

    @ApiModelProperty("项目名称")
    @TableField("project_name")
    private String projectName;

    @ApiModelProperty("业务类型")
    @TableField("business_type")
    private String businessType;

    @ApiModelProperty("标识 唯一外键（业务通过这个字段关联）")
    @TableField("identification")
    private String identification;
}
