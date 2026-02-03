package com.kp.framework.entity.po.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 内存统计信息。
 * @author lipeng
 * 2025/10/17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Mem", description = "内存信息")
public class MemPO {

    @Schema(description = "内存总量G")
    private String total;

    @Schema(description = "已用内存")
    private String used;

    @Schema(description = "剩余内存")
    private String free;

    @Schema(description = "使用率")
    private String usedRate;

//    public double getTotal() {
//        return new KPBigDecimalUtils(total).divide(1024 * 1024 * 1024, 2, RoundingMode.HALF_UP).buildDouble();
////        return Arith.div(total, (1024 * 1024 * 1024), 2);
//    }
//
//    public double getUsed() {
//        return new KPBigDecimalUtils(used).divide(1024 * 1024 * 1024, 2, RoundingMode.HALF_UP).buildDouble();
////        return Arith.div(used, (1024 * 1024 * 1024), 2);
//    }
//
//    public double getFree() {
//        return new KPBigDecimalUtils(free).divide(1024 * 1024 * 1024, 2, RoundingMode.HALF_UP).buildDouble();
////        return Arith.div(free, (1024 * 1024 * 1024), 2);
//    }
//
//    public double getUsage() {
//        return new KPBigDecimalUtils(used).divide(total, 4).multiply(100).buildDouble();
////        return Arith.mul(Arith.div(used, total, 4), 100);
//    }
}