package com.kunpeng.framework.modules.dict.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kunpeng.framework.entity.bo.PageBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 字典类型列表查询入参
 * @Date 2025-07-03
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "DictTypeListParamPO对象", description = "字典类型列表查询入参")
public class DictTypeListParamPO extends PageBO {

    @ApiModelProperty("项目Id")
    @TableField("project_id")
    private String projectId;

    @ApiModelProperty("字典名称")
    @TableField("dict_name")
    private String dictName;

    @ApiModelProperty("字典类型")
    @TableField("dict_type")
    private String dictType;

    @ApiModelProperty("状态 0 停用 1 正常")
    @TableField("status")
    private Integer status;
}
