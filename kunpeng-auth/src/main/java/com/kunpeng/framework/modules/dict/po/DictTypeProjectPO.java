package com.kunpeng.framework.modules.dict.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kunpeng.framework.entity.bo.ParentBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 字典类型项目关联表
 * @Date 2025-07-28 16:38:59
**/
@Data
@Accessors(chain = true)
@TableName("auth_dict_type_project")
@ApiModel(value = "DictTypeProjectPO对象", description = "字典类型表项目关联表")
public class DictTypeProjectPO extends ParentBO {

    @ApiModelProperty("字典类型表项目Id")
    @TableId(value = "adtp_id", type = IdType.ASSIGN_UUID)
    private String adtpId;

    @ApiModelProperty("字典类型ID")
    @TableField("dict_type_id")
    private String dictTypeId;

    @ApiModelProperty("项目Id")
    @TableField("project_id")
    private String projectId;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
