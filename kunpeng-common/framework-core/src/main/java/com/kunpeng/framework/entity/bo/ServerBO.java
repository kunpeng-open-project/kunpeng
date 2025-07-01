package com.kunpeng.framework.entity.bo;

import com.kunpeng.framework.entity.po.system.CpuPO;
import com.kunpeng.framework.entity.po.system.JvmPO;
import com.kunpeng.framework.entity.po.system.MemPO;
import com.kunpeng.framework.entity.po.system.SysFilePO;
import com.kunpeng.framework.entity.po.system.SysPO;
import com.kunpeng.framework.utils.kptool.KPBigDecimalUtils;
import com.kunpeng.framework.utils.kptool.KPDateUtil;
import com.kunpeng.framework.utils.kptool.KPIPUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

import java.lang.management.ManagementFactory;
import java.math.RoundingMode;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;


/**
 * @Author lipeng
 * @Description 服务器内容
 * @Date 2024/9/2 15:54
 * @return
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServerBO {
    private static final int OSHI_WAIT_SECOND = 1000;

    @ApiModelProperty(value = "CPU相关信息")
    private CpuPO cpu = new CpuPO();

    @ApiModelProperty(value = "內存相关信息")
    private MemPO mem = new MemPO();

    @ApiModelProperty(value = "JVM相关信息")
    private JvmPO jvm = new JvmPO();

    @ApiModelProperty(value = "服务器相关信息")
    private SysPO sys = new SysPO();

    @ApiModelProperty(value = "磁盘相关信息")
    private List<SysFilePO> sysFiles = new LinkedList<>();

    public void setValueTo() {
        //解决Oshi获取CPU使用率与Windows任务管理器显示不匹配的问题
//        Windows 任务管理器测量‘已完成的工作’而不是‘CPU 使用率’
//        如果想要Oshi返回的值和Windows任务管理器显示的值一致，则应该修改Oshi配置或者在Central Processor类实例化之前调用
//        GlobalConfig.set(GlobalConfig.OSHI_OS_WINDOWS_CPU_UTILITY, true);
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hal = systemInfo.getHardware();
        // cup信息
        setCpuInfo(hal.getProcessor());
        // 内存信息
        setMemInfo(hal.getMemory());
        // 服务器信息
        setSysInfo();
        // JVM信息
        setJvmInfo();
        // 磁盘信息
        setSysFiles(systemInfo.getOperatingSystem());
    }

    /**
     * 设置CPU信息
     */
    private void setCpuInfo(CentralProcessor processor) {
        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();

        Util.sleep(OSHI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;

        cpu.setCpuNum(processor.getPhysicalProcessorCount() + "核" + processor.getLogicalProcessorCount() + "线程");
        cpu.setSys(new KPBigDecimalUtils(cSys).divide(totalCpu, 2, RoundingMode.HALF_UP).multiply(100).buildString("%"));
        cpu.setUsed(new KPBigDecimalUtils(user).divide(totalCpu, 2, RoundingMode.HALF_UP).multiply(100).buildString("%"));
        cpu.setWait(new KPBigDecimalUtils(iowait).divide(totalCpu, 2, RoundingMode.HALF_UP).multiply(100).buildString("%"));
        cpu.setFree(new KPBigDecimalUtils(idle).divide(totalCpu, 2, RoundingMode.HALF_UP).multiply(100).buildString("%"));
        cpu.setAll(new KPBigDecimalUtils(100).sub(cpu.getFree().substring(0, cpu.getFree().length() - 1)).buildString(2, RoundingMode.HALF_UP, "%"));
//        new KPBigDecimalUtils(cSys).divide(totalCpu,2).build()

//        cpuList.addAll(Arrays.asList(
//                DictionaryStringBO.builder().key("核心数").value(cpuNum).build(),
//                DictionaryStringBO.builder().key("总使用率").value(all).build(),
//                DictionaryStringBO.builder().key("系统使用率").value(sys).build(),
//                DictionaryStringBO.builder().key("用户使用率").value(used).build(),
//                DictionaryStringBO.builder().key("当前等待率").value(wait).build(),
//                DictionaryStringBO.builder().key("当前空闲率").value(free).build()
//        ));
    }

    /**
     * 设置内存信息
     */
    private void setMemInfo(GlobalMemory memory) {
        //总内存
        String total = new KPBigDecimalUtils(memory.getTotal()).divide(1024 * 1024 * 1024, 2, RoundingMode.HALF_UP).buildString("G");
        //已用内存
        String used = new KPBigDecimalUtils(memory.getTotal() - memory.getAvailable()).divide(1024 * 1024 * 1024, 2, RoundingMode.HALF_UP).buildString("G");
        //剩余内存
        String free = new KPBigDecimalUtils(memory.getAvailable()).divide(1024 * 1024 * 1024, 2, RoundingMode.HALF_UP).buildString("G");
        //内存使用率
        String usedRate = new KPBigDecimalUtils(100).sub(new KPBigDecimalUtils(memory.getAvailable()).divide(memory.getTotal(), 2, RoundingMode.HALF_UP).multiply(100).build()).buildString("%");
        mem.setTotal(total);
        mem.setUsed(used);
        mem.setFree(free);
        mem.setUsedRate(usedRate);

//        memList.addAll(Arrays.asList(
//                DictionaryStringBO.builder().key("总内存").value(total).build(),
//                DictionaryStringBO.builder().key("已用内存").value(used).build(),
//                DictionaryStringBO.builder().key("剩余内存").value(free).build(),
//                DictionaryStringBO.builder().key("使用率").value(usedRate).build()
//        ));
    }

    /**
     * 设置服务器信息
     */
    private void setSysInfo() {
        Properties props = System.getProperties();
        sys.setComputerName(KPIPUtil.getHostName());
        sys.setComputerIp(KPIPUtil.getHostIp());
        sys.setOsName(props.getProperty("os.name"));
        sys.setOsArch(props.getProperty("os.arch"));
        sys.setUserDir(props.getProperty("user.dir"));
    }

    /**
     * 设置Java虚拟机
     */
    private void setJvmInfo() {
        Properties props = System.getProperties();

        jvm.setTotal(new KPBigDecimalUtils(Runtime.getRuntime().totalMemory()).divide(1024*1024, 2, RoundingMode.HALF_UP).buildString("M"));
        jvm.setMax(new KPBigDecimalUtils(Runtime.getRuntime().maxMemory()).divide(1024*1024, 2, RoundingMode.HALF_UP).buildString("M"));
        jvm.setFree(new KPBigDecimalUtils(Runtime.getRuntime().freeMemory()).divide(1024*1024, 2, RoundingMode.HALF_UP).buildString("M"));
        jvm.setUsed(new KPBigDecimalUtils(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()).divide(1024*1024, 2, RoundingMode.HALF_UP).buildString("M"));
        jvm.setUsedRate(new KPBigDecimalUtils(100).sub(new KPBigDecimalUtils(Runtime.getRuntime().freeMemory()).divide(Runtime.getRuntime().totalMemory(), 2, RoundingMode.HALF_UP).multiply(100).build()).buildString("%"));
        jvm.setJavaName(ManagementFactory.getRuntimeMXBean().getVmName());
        jvm.setJavaStartTime(KPDateUtil.dateFormat(KPDateUtil.getServerStartDate(), KPDateUtil.DATE_TIME_PATTERN));
        jvm.setJavaRunTime(KPDateUtil.getDatePoor(new Date(), KPDateUtil.getServerStartDate()));
        jvm.setJavaVersion(props.getProperty("java.version"));
        jvm.setJavaPath(props.getProperty("java.home"));
        jvm.setTimeZone(props.getProperty("user.timezone"));
    }

    /**
     * 设置磁盘信息
     */
    private void setSysFiles(OperatingSystem os) {
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            SysFilePO sysFile = new SysFilePO();
            sysFile.setDirName(fs.getMount());
            sysFile.setSysTypeName(fs.getType());
            sysFile.setTypeName(fs.getName());
            sysFile.setTotal(convertFileSize(total));
            sysFile.setFree(convertFileSize(free));
            sysFile.setUsed(convertFileSize(used));
            sysFile.setUsage(new KPBigDecimalUtils(used).divide(total, 4).multiply(100).buildString("%") );
//            sysFile.setUsage(Arith.mul(Arith.div(used, total, 4), 100));
            sysFiles.add(sysFile);
        }
    }

    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值
     */
    public String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }
}
