package com.kp.framework.modules.logRecord.po.customer;


import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(name = "InterfaceCallListCustomerPO", description = "InterfaceCallListCustomerPO")
public class InterfaceCallListCustomerPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目名称")
    @TableField("project_name")
    private String projectName;

    @Schema(description = "请求uri")
    @TableField("uri")
    private String uri;

    @Schema(description = "接口名称")
    @TableField("name")
    private String name;

    @Schema(description = "请求方式")
    @TableField("method")
    private String method;

    @Schema(description = "调用次数")
    private Integer num;
}
