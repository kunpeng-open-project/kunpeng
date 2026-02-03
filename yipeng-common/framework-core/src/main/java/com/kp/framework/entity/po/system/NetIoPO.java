package com.kp.framework.entity.po.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 网络速率PO（单个有效接口）。
 * @author lipeng
 * 2025/10/17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "NetIo", description = "网络速率信息")
public class NetIoPO {

    // （MB/秒，所有接口接收数据总和）
    @Schema(description = "总下行速率")
    private String totalDownRate;
    // 总上行速率（MB/秒，所有接口发送数据总和）
    @Schema(description = "总上行速率")
    private String totalUpRate;
}