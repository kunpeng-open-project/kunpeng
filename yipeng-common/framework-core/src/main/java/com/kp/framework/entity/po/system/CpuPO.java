package com.kp.framework.entity.po.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * cpu 统计信息。
 * @author lipeng
 * 2025/10/17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Cpu", description = "cpu信息")
public class CpuPO {

    @Schema(description = "核心数")
    private String cpuNum;

//    @Schema(description = "CPU总的使用")
//    private double total;

    @Schema(description = "CPU总使用率")
    private String all;

    @Schema(description = "CPU系统使用率")
    private String sys;

    @Schema(description = "CPU用户使用率")
    private String used;

    @Schema(description = "CPU当前等待率")
    private String wait;

    @Schema(description = "CPU当前空闲率")
    private String free;

//    public double getTotal() {
//        return new KPBigDecimalUtils(total).multiply(100).buildDouble(2, RoundingMode.HALF_UP);
////        return Arith.round(Arith.mul(total, 100), 2);
//    }

//    public double getSys() {
//        return new KPBigDecimalUtils(sys / total).multiply(100).buildDouble(2, RoundingMode.HALF_UP);
////        return Arith.round(Arith.mul(sys / total, 100), 2);
//    }

//    public double getUsed() {
//        return new KPBigDecimalUtils(used / total).multiply(100).buildDouble(2, RoundingMode.HALF_UP);
////        return Arith.round(Arith.mul(used / total, 100), 2);
//    }

//    public double getWait() {
//        return new KPBigDecimalUtils(wait / total).multiply(100).buildDouble(2, RoundingMode.HALF_UP);
////        return Arith.round(Arith.mul(wait / total, 100), 2);
//    }

//    public double getFree() {
//        return new KPBigDecimalUtils(free / total).multiply(100).buildDouble(2, RoundingMode.HALF_UP);
////        return Arith.round(Arith.mul(free / total, 100), 2);
//    }
}