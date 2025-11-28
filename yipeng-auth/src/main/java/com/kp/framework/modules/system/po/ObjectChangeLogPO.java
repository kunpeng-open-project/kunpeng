package com.kp.framework.modules.system.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kp.framework.entity.bo.ParentBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 
 * @Date 2025-11-10 16:34:05
**/
@Data
@Accessors(chain = true)
@TableName("auth_object_change_log")
@ApiModel(value = "ObjectChangeLogPO对象", description = "")
public class ObjectChangeLogPO extends ParentBO {

    @ApiModelProperty("主键")
    @TableId(value = "uuid", type = IdType.ASSIGN_UUID)
    private String uuid;

    @ApiModelProperty("项目名称")
    @TableField("project_name")
    private String projectName;

    @ApiModelProperty("操作类名和方法名")
    @TableField("class_name")
    private String className;

    @ApiModelProperty("标识 唯一外键（业务通过这个字段关联）")
    @TableField("identification")
    private String identification;

    @ApiModelProperty("操作类型")
    @TableField("operate_type")
    private String operateType;

    @ApiModelProperty("业务类型")
    @TableField("business_type")
    private String businessType;

    @ApiModelProperty("改变记录")
    @TableField("change_body")
    private String changeBody;

    @ApiModelProperty("请求url")
    @TableField("url")
    private String url;

    @ApiModelProperty("客户端IP")
    @TableField("clinet_ip")
    private String clinetIp;

    @ApiModelProperty("操作人手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("工号")
    @TableField("serial")
    private String serial;

    @ApiModelProperty("请求全部参数记录")
    @TableField("parameter")
    private String parameter;
}
