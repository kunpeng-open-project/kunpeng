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
@ApiModel(value="Cpu", description="Cpu")
public class CpuPO {

    @ApiModelProperty(value = "核心数")
    private String cpuNum;

//    @ApiModelProperty(value = "CPU总的使用")
//    private double total;

    @ApiModelProperty(value = "CPU总使用率")
    private String all;

    @ApiModelProperty(value = "CPU系统使用率")
    private String sys;

    @ApiModelProperty(value = "CPU用户使用率")
    private String used;

    @ApiModelProperty(value = "CPU当前等待率")
    private String wait;

    @ApiModelProperty(value = "CPU当前空闲率")
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
