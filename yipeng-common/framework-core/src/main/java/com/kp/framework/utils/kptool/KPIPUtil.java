package com.kp.framework.utils.kptool;

import com.kp.framework.exception.KPUtilException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * IP地址与网络相关的工具类。
 * @author lipeng
 * 2024/11/20
 */
@UtilityClass
public final class KPIPUtil {

    private static final Logger log = LoggerFactory.getLogger(KPIPUtil.class);

    /**
     * 用于获取客户端IP的HTTP头名称列表。
     * 按优先级从高到低排列。
     */
    private static final List<String> IP_HEADER_CANDIDATES = Arrays.asList(
            "x-forwarded-for",
            "Proxy-Client-Ip",
            "WL-Proxy-Client-Ip",
            "entrust-client-ip"
    );

    /**
     * IPv4 本地回环地址
     */
    private static final String LOCALHOST_IPV4 = "127.0.0.1";
    /**
     * IPv6 本地回环地址
     */
    private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

    /**
     * 内网IP判断相关常量 (IPv4)
     */
    private static final byte IPV4_SECTION_10 = 0x0A;         // 10.x.x.x/8
    private static final byte IPV4_SECTION_172 = (byte) 0xAC; // 172.16.x.x/12
    private static final byte IPV4_SECTION_172_START = (byte) 0x10;
    private static final byte IPV4_SECTION_172_END = (byte) 0x1F;
    private static final byte IPV4_SECTION_192 = (byte) 0xC0; // 192.168.x.x/16
    private static final byte IPV4_SECTION_192_168 = (byte) 0xA8;

    /**
     * 从 HttpServletRequest 对象中获取客户端的真实IP地址。
     * <p>
     * 该方法会依次检查一系列HTTP头（如x-forwarded-for），以获取经过代理转发后的真实客户端IP。
     * 如果所有HTTP头都无法获取，则会调用 request.getRemoteAddr()。
     * 如果获取到的是本地回环地址（127.0.0.1或[0:0:0:0:0:0:0:1]），则会尝试获取本机的LAN地址。
     *
     * @param request HttpServletRequest 对象，可以为 null
     * @return 客户端的IP地址字符串，如果 request 为 null 或无法获取则返回空字符串
     */
    public static String getClientIP(HttpServletRequest request) {
        if (request == null) {
            log.warn("尝试从一个 null 的 HttpServletRequest 中获取客户端IP。");
            return "";
        }

        String clientIp = null;
        // 1. 优先从代理头中获取IP地址
        for (String header : IP_HEADER_CANDIDATES) {
            clientIp = request.getHeader(header);
            if (isValidIp(clientIp)) {
                break;
            }
        }

        // 2. 如果没有从代理头中获取到，则使用原始请求地址
        if (!isValidIp(clientIp)) {
            clientIp = request.getRemoteAddr();
        }

        // 3. 如果获取到的是本地地址，尝试获取本机LAN IP
        if (LOCALHOST_IPV4.equals(clientIp) || LOCALHOST_IPV6.equals(clientIp.trim())) {
            try {
                InetAddress inetAddress = InetAddress.getLocalHost();
                clientIp = inetAddress.getHostAddress();
                log.debug("检测到本地回环地址，已自动替换为本机LAN地址: {}", clientIp);
            } catch (UnknownHostException e) {
                log.error("获取本机LAN地址失败，将使用回环地址: {}", clientIp, e);
            }
        }

        // 4. 如果是多级代理，只取第一个IP
        if (clientIp != null && clientIp.indexOf(',') > 0) {
            clientIp = clientIp.substring(0, clientIp.indexOf(',')).trim();
        }

        return clientIp;
    }

    /**
     * 获取客户端IP地址。
     * <p>
     * 此方法依赖于 {@link KPRequsetUtil#getRequest()} 来获取当前线程绑定的 HttpServletRequest 对象。
     * 如果该方法无法获取到请求对象，可能会返回空字符串或触发异常。
     *
     * @return 客户端的IP地址字符串
     * @see #getClientIP(HttpServletRequest)
     */
    public static String getClientIP() {
        HttpServletRequest request = KPRequsetUtil.getRequest();
        return getClientIP(request);
    }

    /**
     * 判断一个IP地址是否为内网/私有地址。
     * <p>
     * 目前仅支持IPv4地址的判断。
     *
     * @param ip 待判断的IP地址字符串
     * @return 如果是内网IP则返回 true，否则返回 false
     */
    public static boolean isInternalIp(String ip) {
        if (!isValidIp(ip)) {
            log.warn("尝试判断一个无效的IP地址 [{}] 是否为内网IP。", ip);
            return false;
        }

        // 本地回环地址直接判定为内网IP
        if (LOCALHOST_IPV4.equals(ip) || LOCALHOST_IPV6.equals(ip)) {
            return true;
        }

        byte[] addr = textToNumericFormatV4(ip);
        // 如果不是有效的IPv4地址，则直接返回false
        if (addr == null || addr.length != 4) {
            return false;
        }

        final byte b0 = addr[0];
        final byte b1 = addr[1];

        switch (b0) {
            case IPV4_SECTION_10: // 10.0.0.0/8
                return true;
            case IPV4_SECTION_172: // 172.16.0.0/12
                return b1 >= IPV4_SECTION_172_START && b1 <= IPV4_SECTION_172_END;
            case IPV4_SECTION_192: // 192.168.0.0/16
                return b1 == IPV4_SECTION_192_168;
            default:
                return false;
        }
    }

    /**
     * 获取本地主机名。
     *
     * @return 本地主机名，如果获取失败则返回 "未知主机名"
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.error("获取本地主机名失败。", e);
            return "未知主机名";
        }
    }

    /**
     * 获取本地IP地址。
     * <p>
     * 注意：一台机器可能有多个网络接口和IP地址，此方法返回的是由操作系统决定的"首选"地址。
     *
     * @return 本地IP地址，如果获取失败则返回 "127.0.0.1"
     */
    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("获取本地IP地址失败。", e);
            return LOCALHOST_IPV4;
        }
    }

    /**
     * 检查指定的主机和端口是否被占用。
     *
     * @param host 主机名或IP地址，可以是 "localhost"
     * @param port 端口号
     * @return 如果端口被占用，则返回 true，否则返回 false
     * @throws KPUtilException 如果端口号无效（小于0或大于65535）
     */
    public static boolean isPortOccupied(String host, int port) {
        if (port < 0 || port > 65535) {
            throw new KPUtilException("无效的端口号: " + port);
        }
        if (host == null || host.trim().isEmpty()) {
            host = "localhost";
        }

        try (Socket socket = new Socket(host, port)) {
            // 如果连接成功，说明端口已被占用
            log.trace("端口 {}:{} 已被占用。", host, port);
            return true;
        } catch (Exception e) {
            // 连接失败（Connection refused, timeout等），说明端口未被占用或不可达
            log.trace("端口 {}:{} 未被占用或无法连接。", host, port, e);
            return false;
        }
    }

    /**
     * 从指定端口开始，自动查找第一个可用的端口。
     * <p>
     * 此方法会从 startPort 开始，依次尝试绑定端口，直到找到一个可用的端口或达到端口上限(65535)。
     * 注意：这是一个阻塞操作，在某些网络环境下可能会比较耗时。
     *
     * @param host      主机名或IP地址
     * @param startPort 起始端口号
     * @return 找到的可用端口号。如果在 [startPort, 65535] 范围内没有找到可用端口，则返回 -1
     */
    public static int findAvailablePort(String host, int startPort) {
        if (startPort < 0) {
            throw new KPUtilException("起始端口号无效: " + startPort);
        }

        for (int port = startPort; port <= 65535; port++) {
            if (!isPortOccupied(host, port)) {
                log.info("找到可用端口: {}:{}", host, port);
                return port;
            }
        }

        log.warn("在主机 {} 上，从端口 {} 到 65535 的范围内未找到可用端口。", host, startPort);
        return -1;
    }

    /**
     * 检查一个字符串是否是一个有效的IP地址（IPv4或IPv6）。
     * <p>
     * 这里的检查比较宽松，主要是为了过滤掉 "unknown" 等无效字符串。
     *
     * @param ip 待检查的字符串
     * @return 如果是有效的IP地址格式，则返回 true，否则返回 false
     */
    private static boolean isValidIp(String ip) {
        return ip != null && !ip.trim().isEmpty() && !"unknown".equalsIgnoreCase(ip.trim());
    }

    /**
     * 将点分十进制格式的IPv4字符串转换为字节数组。
     * <p>
     * 该方法是 {@link InetAddress#getByName(String)} 内部使用的辅助方法的简化版本。
     * 它不进行反向DNS查找，因此效率更高，但只适用于IPv4。
     *
     * @param ipString IPv4地址字符串
     * @return 对应的字节数组，如果格式不正确则返回 null
     */
    private static byte[] textToNumericFormatV4(String ipString) {
        if (ipString == null || ipString.isEmpty()) {
            return null;
        }

        String[] parts = ipString.split("\\.", -1);
        if (parts.length != 4) {
            return null;
        }

        byte[] bytes = new byte[4];
        try {
            for (int i = 0; i < 4; i++) {
                int part = Integer.parseInt(parts[i]);
                if (part < 0 || part > 255) {
                    return null;
                }
                bytes[i] = (byte) part;
            }
        } catch (NumberFormatException e) {
            return null;
        }

        return bytes;
    }
}