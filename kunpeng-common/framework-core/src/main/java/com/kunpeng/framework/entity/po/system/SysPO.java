package com.kunpeng.framework.entity.po.system;

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
@ApiModel(value="Sys", description="Sys")
public class SysPO {

    @ApiModelProperty(value = "服务器名称")
    private String computerName;

    @ApiModelProperty(value = "服务器IP")
    private String computerIp;

    @ApiModelProperty(value = "项目路径")
    private String userDir;

    @ApiModelProperty(value = "操作系统")
    private String osName;

    @ApiModelProperty(value = "系统架构")
    private String osArch;
}
