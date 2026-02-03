package com.kp.framework.utils.kptool;

import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.exception.KPUtilException;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 操作命令窗口。
 * @author lipeng
 * 2020/9/22
 */
@UtilityClass
public class KPCmdUtil {

    private static final Logger log = LoggerFactory.getLogger(KPCmdUtil.class);
    // 命令执行超时时间(毫秒)
    private static final long COMMAND_TIMEOUT = 30000;
    // 系统编码
    private static final String SYSTEM_ENCODING = System.getProperty("sun.jnu.encoding", "UTF-8");
    // MAC地址正则
    private static final Pattern MAC_PATTERN = Pattern.compile("([0-9A-Fa-f]{2})([-:][0-9A-Fa-f]{2}){5}");
    // IP地址正则
    private static final Pattern IP_PATTERN = Pattern.compile("((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))");

    /**
     * 执行cmd命令。
     * @author lipeng
     * 2020/9/22
     * @param cmd [cmd] cmd 命令
     * @return java.lang.String
     */
    public static String command(String cmd) {
        if (cmd == null || cmd.trim().isEmpty()) {
            throw new KPUtilException("命令不能为空");
        }

        try {
            Process process = Runtime.getRuntime().exec(cmd);

            // 等待进程完成，设置超时
            boolean finished = process.waitFor(COMMAND_TIMEOUT, TimeUnit.MILLISECONDS);
            if (!finished) {
                process.destroyForcibly(); // 超时强制销毁进程
                throw new KPUtilException("命令执行超时: " + cmd);
            }

            // 同时处理输入流和错误流，避免缓冲区满导致阻塞
            String output = readStream(process.getInputStream());
            String error = readStream(process.getErrorStream());

            if (!error.isEmpty()) {
                log.warn("[命令执行警告] 命令: {}, 错误输出: {}", cmd, error);
            }

            // 检查进程退出码
            int exitCode = process.exitValue();
            if (exitCode != 0) {
                log.error("[命令执行失败] 命令: {}, 退出码: {}, 错误信息: {}", cmd, exitCode, error);
                throw new KPUtilException("命令执行失败，退出码: " + exitCode);
            }

            return output;
        } catch (Exception ex) {
            throw new KPUtilException(ex.getMessage());
        }
    }

    /**
     * 根据指定ip获取对应的mac地址（支持本机IP）。
     * @author lipeng
     * 2020/9/22
     * @param ip [ip] ip地址
     * @return java.lang.String
     */
    public static String getMac(String ip) {
        if (!isValidIp(ip)) {
            throw new KPUtilException("无效的IP地址: " + ip);
        }

        // 若为本地IP，通过KPIPUtil的网络接口方式获取MAC
        if (isLocalIp(ip)) {
            return getLocalMacByIp(ip);
        }

        // 非本地IP，使用ping + arp方式
        String pingCmd = isWindows() ? "ping " + ip + " -n 2" : "ping " + ip + " -c 2";
        String pingResult = command(pingCmd);

        if (pingResult.contains("TTL") || pingResult.contains("ttl")) {
            String arpResult = command("arp -a " + ip);
            return extractMac(arpResult);
        } else {
            log.warn("IP地址不可达: {}", ip);
            return null;
        }
    }

    /**
     * 查询所有ip 和 mac（基于ARP缓存）。
     * @author lipeng
     * 2020/9/22
     * @return java.util.List<com.alibaba.fastjson2.JSONObject>
     */
    public static List<JSONObject> getIPAndMacs() {
        String result = command("arp -a");
        if (KPStringUtil.isEmpty(result)) {
            return new ArrayList<>(0); // 返回空集合而非null
        }

        String[] lines = result.split(System.lineSeparator());
        List<JSONObject> list = new ArrayList<>(lines.length);

        for (String line : lines) {
            String mac = extractMac(line);
            String ip = extractIp(line);

            if (mac != null && ip != null) {
                JSONObject json = new JSONObject();
                json.put("ip", ip);
                json.put("mac", mac);
                list.add(json);
            }
        }

        return list;
    }

    /**
     * 主动扫描局域网所有活跃设备（包含本机）
     * @author lipeng
     * 2024/11/17
     * @param subnet 子网（如"192.168.1."）
     * @param start 起始IP段 如 1
     * @param end 结束IP段 如 255
     * @return java.util.List<com.alibaba.fastjson2.JSONObject>
     */
    public static List<JSONObject> scanAllLanDevices(String subnet, int start, int end) {
        List<JSONObject> devices = new ArrayList<>();
        // 1. 主动扫描活跃IP
        List<String> activeIps = scanActiveIps(subnet, start, end);

        // 2. 获取每个活跃IP的MAC地址
        for (String ip : activeIps) {
            try {
                String mac = getMac(ip);
                if (mac != null) {
                    JSONObject device = new JSONObject();
                    device.put("ip", ip);
                    device.put("mac", mac);
                    devices.add(device);
                }
            } catch (Exception e) {
                log.error("获取IP[{}]的MAC地址失败", ip, e);
            }
        }

        // 3. 补充本机信息（使用KPIPUtil获取本机IP）
        addLocalHostInfo(devices, subnet);

        return devices;
    }

    /**
     * 扫描子网内的活跃IP
     */
    private static List<String> scanActiveIps(String subnet, int start, int end) {
        List<String> activeIps = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(50);

        for (int i = start; i <= end; i++) {
            String ip = subnet + i;
            executor.submit(() -> {
                try {
                    // 跳过本机IP的ping扫描（避免冗余，后续会通过KPIPUtil补充）
                    if (isLocalIp(ip)) {
                        synchronized (activeIps) {
                            activeIps.add(ip);
                        }
                        return;
                    }

                    String cmd = isWindows()
                            ? "ping " + ip + " -n 1 -w 500"
                            : "ping " + ip + " -c 1 -W 1";

                    Process process = Runtime.getRuntime().exec(cmd);
                    if (process.waitFor(1, TimeUnit.SECONDS) && process.exitValue() == 0) {
                        synchronized (activeIps) {
                            activeIps.add(ip);
                        }
                    }
                } catch (Exception e) {
                    log.debug("扫描IP[{}]时发生异常", ip, e);
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return activeIps;
    }

    /**
     * 补充本机的IP和MAC信息（基于KPIPUtil）
     */
    private static void addLocalHostInfo(List<JSONObject> devices, String subnet) {
        try {
            // 使用KPIPUtil获取本机IP
            String localIp = KPIPUtil.getHostIp();
            if (localIp.startsWith(subnet)) {
                // 获取本机MAC（复用getLocalMacByIp方法）
                String localMac = getLocalMacByIp(localIp);

                // 避免重复添加
                boolean exists = devices.stream()
                        .anyMatch(dev -> localIp.equals(dev.getString("ip")));
                if (!exists && localMac != null) {
                    JSONObject local = new JSONObject();
                    local.put("ip", localIp);
                    local.put("mac", localMac);
                    local.put("isLocal", true); // 标记为本机
                    devices.add(local);
                }
            }
        } catch (Exception e) {
            log.error("通过KPIPUtil获取本机信息失败", e);
        }
    }

    /**
     * 检查IP是否为本地IP（基于KPIPUtil的内网判断）
     */
    private static boolean isLocalIp(String ip) {
        return ip.equals(KPIPUtil.getHostIp());
    }

    /**
     * 根据本地IP获取对应的MAC地址
     */
    private static String getLocalMacByIp(String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            NetworkInterface iface = NetworkInterface.getByInetAddress(address);
            if (iface != null) {
                byte[] macBytes = iface.getHardwareAddress();
                if (macBytes != null) {
                    StringBuilder mac = new StringBuilder();
                    for (byte b : macBytes) {
                        mac.append(String.format("%02X:", b));
                    }
                    if (mac.length() > 0) {
                        return mac.deleteCharAt(mac.length() - 1).toString();
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取本地IP[{}]的MAC地址失败", ip, e);
        }
        return null;
    }

    /**
     * 从字符串中提取MAC地址
     */
    private static String extractMac(String input) {
        if (KPStringUtil.isEmpty(input)) {
            return null;
        }
        Matcher matcher = MAC_PATTERN.matcher(input);
        return matcher.find() ? matcher.group() : null;
    }

    /**
     * 从字符串中提取IP地址
     */
    private static String extractIp(String input) {
        if (KPStringUtil.isEmpty(input)) {
            return null;
        }
        Matcher matcher = IP_PATTERN.matcher(input);
        return matcher.find() ? matcher.group() : null;
    }

    /**
     * 检查IP地址是否有效
     */
    private static boolean isValidIp(String ip) {
        return ip != null && IP_PATTERN.matcher(ip).matches();
    }

    /**
     * 读取输入流内容
     */
    private static String readStream(InputStream inputStream) {
        try (InputStream in = inputStream) {
            StringBuilder result = new StringBuilder();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                result.append(new String(buffer, 0, bytesRead, SYSTEM_ENCODING));
            }
            return result.toString().trim();
        } catch (Exception ex) {
            throw new KPUtilException(ex.getMessage());
        }
    }

    /**
     * 判断当前系统是否为Windows
     */
    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    /**
     * 命令执行超时异常
     */
    public static class TimeoutException extends Exception {
        public TimeoutException(String message) {
            super(message);
        }
    }

    /**
     * 主方法：测试所有工具类方法
     */
    public static void main(String[] args) {
        System.out.println("===== 开始测试KPCmdUtil工具类 =====");


        // 打印本机IP（使用KPIPUtil）
        String localIp = KPIPUtil.getHostIp();
        System.out.println("本机IP（来自KPIPUtil）：" + localIp);

        // 1. 测试执行命令方法
        System.out.println("\n1. 测试执行命令方法：");
        String cmd = isWindows() ? "ipconfig" : "ifconfig";
        String cmdResult = command(cmd);
        System.out.println("命令执行结果（前500字符）：" + cmdResult.substring(0, Math.min(500, cmdResult.length())));

        // 2. 测试获取本机MAC地址
        System.out.println("\n2. 测试获取本机MAC地址：");
        String localMac = getMac(localIp);
        System.out.println("本机MAC地址：" + (localMac != null ? localMac : "未找到"));

        // 3. 测试获取其他IP的MAC地址（替换为局域网内的其他设备IP）
        System.out.println("\n3. 测试获取其他IP的MAC地址：");
        String testIp = "192.168.0.215"; // 示例：网关IP
        String mac = getMac(testIp);
        System.out.println("IP " + testIp + " 的MAC地址：" + (mac != null ? mac : "未找到"));

        // 4. 测试获取ARP缓存中的IP和MAC列表
        System.out.println("\n4. 测试获取ARP缓存中的IP和MAC列表：");
        List<JSONObject> arpList = getIPAndMacs();
        System.out.println("ARP缓存中找到 " + arpList.size() + " 条记录：");
        for (JSONObject item : arpList) {
            System.out.println("IP: " + item.getString("ip") + ", MAC: " + item.getString("mac"));
        }

        // 5. 测试扫描局域网所有设备
        System.out.println("\n5. 测试扫描局域网所有设备：");
        String subnet = "192.168.0.";
        int start = 1;
        int end = 255; // 缩小范围加快测试
        System.out.println("开始扫描 " + subnet + start + " 到 " + subnet + end + " 网段...");

        long scanStart = System.currentTimeMillis();
        List<JSONObject> allDevices = scanAllLanDevices(subnet, start, end);
        long scanEnd = System.currentTimeMillis();

        System.out.println("扫描完成，共发现 " + allDevices.size() + " 个设备，耗时 " + (scanEnd - scanStart) + "ms");
        for (JSONObject device : allDevices) {
            String isLocal = device.getBooleanValue("isLocal") ? " (本机)" : "";
            System.out.println("IP: " + device.getString("ip") + ", MAC: " + device.getString("mac") + isLocal);
        }


        System.out.println("\n===== KPCmdUtil工具类测试结束 =====");
    }
}