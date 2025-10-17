package com.kp.framework.entity.po.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author lipeng
 * @Description 网络速率PO（单个有效接口）
 * @Date 2025/10/17
 * @return
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="NetIo", description="NetIo")
public class NetIoPO {

    // （MB/秒，所有接口接收数据总和）
    @ApiModelProperty(value = "总下行速率")
    private String totalDownRate;
    // 总上行速率（MB/秒，所有接口发送数据总和）
    @ApiModelProperty(value = "总上行速率")
    private String totalUpRate;
}
