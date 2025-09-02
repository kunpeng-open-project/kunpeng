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
@ApiModel(value="Mem", description="Mem")
public class MemPO {

    @ApiModelProperty(value = "内存总量G")
    private String total;

    @ApiModelProperty(value = "已用内存")
    private String used;

    @ApiModelProperty(value = "剩余内存")
    private String free;


    @ApiModelProperty(value = "使用率")
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
