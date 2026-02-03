package com.kp.framework.controller.po;

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
 * 数据库字段修改记录。
 * @author lipeng
 * 2025-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("auth_object_change_log")
@Schema(name = "ObjectChangeLogPO对象", description = "ObjectChangeLogPO对象")
public class ObjectChangeLogPO extends ParentBO<ObjectChangeLogPO> {

    @Schema(description = "主键")
    @TableId(value = "uuid", type = IdType.ASSIGN_UUID)
    private String uuid;

    @Schema(description = "项目名称")
    @TableField("project_name")
    private String projectName;

    @Schema(description = "操作类名和方法名")
    @TableField("class_name")
    private String className;

    @Schema(description = "标识 唯一外键（业务通过这个字段关联）")
    @TableField("identification")
    private String identification;

    @Schema(description = "操作类型")
    @TableField("operate_type")
    private String operateType;

    @Schema(description = "业务类型")
    @TableField("business_type")
    private String businessType;

    @Schema(description = "改变记录")
    @TableField("change_body")
    private String changeBody;

    @Schema(description = "请求url")
    @TableField("url")
    private String url;

    @Schema(description = "客户端IP")
    @TableField("clinet_ip")
    private String clinetIp;

    @Schema(description = "操作人手机号")
    @TableField("phone")
    private String phone;

    @Schema(description = "工号")
    @TableField("serial")
    private String serial;

    @Schema(description = "请求全部参数记录")
    @TableField("parameter")
    private String parameter;
}
