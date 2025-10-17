package com.kp.framework.entity.po.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Author lipeng
 * @Description 总磁盘吞吐PO
 * @Date 2025/10/17
 * @return
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="Disk", description="Disk")
public class DiskIoPO {

    @ApiModelProperty(value = "总读取速率")
    private String totalReadRate;

    @ApiModelProperty(value = "总写入速率")
    private String totalWriteRate;
}
