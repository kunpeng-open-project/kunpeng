package com.kp.framework.entity.po.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="SysFilePO", description="SysFilePO")
public class SysFilePO {

    @ApiModelProperty(value = "盘符路径")
    private String dirName;

    @ApiModelProperty(value = "盘符类型")
    private String sysTypeName;

    @ApiModelProperty(value = "文件类型")
    private String typeName;

    @ApiModelProperty(value = "总大小")
    private String total;

    @ApiModelProperty(value = "剩余大小")
    private String free;

    @ApiModelProperty(value = "已经使用量")
    private String used;

    @ApiModelProperty(value = "资源的使用率")
    private String usage;
}
