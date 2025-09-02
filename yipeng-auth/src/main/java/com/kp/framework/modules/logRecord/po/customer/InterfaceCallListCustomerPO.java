package com.kp.framework.modules.logRecord.po.customer;


import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="InterfaceCallListCustomerPO", description="InterfaceCallListCustomerPO")
public class InterfaceCallListCustomerPO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目名称")
    @TableField("project_name")
    private String projectName;

    @ApiModelProperty(value = "请求uri")
    @TableField("uri")
    private String uri;

    @ApiModelProperty(value = "接口名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "请求方式")
    @TableField("method")
    private String method;

    @ApiModelProperty(value = "调用次数")
    private Integer num;

}
