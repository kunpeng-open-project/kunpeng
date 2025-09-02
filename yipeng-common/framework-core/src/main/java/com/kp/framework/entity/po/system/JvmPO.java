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
@ApiModel(value="Jvm", description="Jvm")
public class JvmPO {

    @ApiModelProperty(value = "当前JVM占用的内存总数(M)")
    private String total;

    @ApiModelProperty(value = "JVM最大可用内存总数(M)")
    private String max;

    @ApiModelProperty(value = "JVM空闲内存(M)")
    private String free;

    @ApiModelProperty(value = "使用率")
    private String usedRate;

    @ApiModelProperty(value = "JVM使用内存(M)")
    private String used;

    @ApiModelProperty(value = "JDK路径")
    private String javaPath;

    @ApiModelProperty(value = "JDK版本")
    private String javaVersion;

    @ApiModelProperty(value = "时区")
    private String timeZone;

    @ApiModelProperty(value = "java名称")
    private String javaName;

    @ApiModelProperty(value = "java启动时间")
    private String javaStartTime;

    @ApiModelProperty(value = "java运行时间")
    private String javaRunTime;




//    public double getTotal() {
//        return new KPBigDecimalUtils(total).divide(1024*1024, 2, RoundingMode.HALF_UP).buildDouble();
////        return Arith.div(total, (1024 * 1024), 2);
//    }
//
//    public double getMax() {
//        return new KPBigDecimalUtils(max).divide(1024 * 1024, 2, RoundingMode.HALF_UP).buildDouble();
////        return Arith.div(max, (1024 * 1024), 2);
//    }
//
//    public double getFree() {
//        return new KPBigDecimalUtils(free).divide(1024 * 1024, 2, RoundingMode.HALF_UP).buildDouble();
////        return Arith.div(free, (1024 * 1024), 2);
//    }
//
//    public double getUsed() {
//        return new KPBigDecimalUtils(total - free).divide(1024 * 1024, 2, RoundingMode.HALF_UP).buildDouble();
////        return Arith.div(total - free, (1024 * 1024), 2);
//    }
//
//    public double getUsage() {
//        return new KPBigDecimalUtils(total - free).divide(total, 4).multiply(100).buildDouble();
////        return Arith.mul(Arith.div(total - free, total, 4), 100);
//    }
//
//    /**
//     * 获取JDK名称
//     */
//    public String getName() {
//        return ManagementFactory.getRuntimeMXBean().getVmName();
//    }
//
//    /**
//     * JDK启动时间
//     */
//    public String getStartTime() {
//        return KPDateUtil.dateFormat(KPDateUtil.getServerStartDate(), KPDateUtil.DATE_TIME_PATTERN);
//    }
//
//    /**
//     * JDK运行时间
//     */
//    public String getRunTime() {
//        return KPDateUtil.getDatePoor(new Date(), KPDateUtil.getServerStartDate());
//    }
//
//    /**
//     * 运行参数
//     */
//    public String getInputArgs() {
//        return ManagementFactory.getRuntimeMXBean().getInputArguments().toString();
//    }
}
