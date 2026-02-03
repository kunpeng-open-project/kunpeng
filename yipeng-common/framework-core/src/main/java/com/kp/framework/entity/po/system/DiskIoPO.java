package com.kp.framework.entity.po.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 总磁盘吞吐PO。
 * @author lipeng
 * 2025/10/17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Disk", description = "总磁盘信息")
public class DiskIoPO {

    @Schema(description = "总读取速率")
    private String totalReadRate;

    @Schema(description = "总写入速率")
    private String totalWriteRate;
}