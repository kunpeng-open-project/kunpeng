package com.kp.framework.common.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.ServletRequestPathUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 因为鉴权系统不能直接引用鲲鹏核心工具包  所有把用的的 放在这里   只限这个小jar包使用。
 * @author lipeng
 * 2020/9/10
 */
@Deprecated
@Slf4j
public class CommonUtil {

    private static final RedisTemplate<String, Object> redisTemplate = ServiceUtil.getBean("redisTemplate", RedisTemplate.class);

    /**
     * 返回json
     */
    public static final void writeJson(JSONObject jsonData) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setStatus(200);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
            out.close();
        }
        String object = JSON.toJSONString(jsonData);
//        log.info("返回内容：{}", jsonData);
        out.println(object);
        out.flush();
        out.close();
    }


    public static final void remove(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            try {
                redisTemplate.expire(key, 1, TimeUnit.MILLISECONDS);
                Thread.sleep(500);
            } catch (Exception ex) {
            }

        }
    }

    /**
     * String转Json。
     * @author lipeng
     * 2020/9/10
     */
    public static final JSONObject toJson(String jsonString) {
        return JSONObject.parseObject(jsonString);
    }

    /**
     * String转Java对象。
     * @author lipeng
     * 2020/9/10
     */
    public static final <T> T toJavaObject(String obj, Class<T> clazz) {
        try {
            return JSON.toJavaObject(obj, clazz);
        } catch (Exception ex) {
            try {
                return JSON.toJavaObject(JSON.toJSONString(obj), clazz);
            } catch (Exception e) {
                log.error("json转Java对象失败：" + e.getMessage());
                throw new RuntimeException("数据转换异常！");
            }
        }
    }

    /**
     * 时间加减分钟。
     * @author lipeng
     */
    public static Date dateAddMinutes(Date startDate, int minutes) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + minutes);
        return c.getTime();
    }

    /**
     * LocalDateTime 转 date
     * @author lipeng
     * 2023/10/7
     * @param localDateTime LocalDateTime
     * @return java.util.Date
     */
    public static Date LocalDateTimeByDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 时间比较（如果myDate>compareDate返回1，<返回-1，相等返回0）
     * @author lipeng
     * 2026/1/22
     * @param myDate 时间
     * @param compareDate 要比较的时间 后面的大 -1
     * @return int
     */
    public static int dateCompare(Date myDate, Date compareDate) {
        Calendar myCal = Calendar.getInstance();
        Calendar compareCal = Calendar.getInstance();
        myCal.setTime(myDate);
        compareCal.setTime(compareDate);
        return myCal.compareTo(compareCal);
    }

    /**
     * date转LocalDateTime。
     * @author lipeng
     * 2023/10/7
     * @param date  Date
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime dateByLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 将毫秒数转换为天、小时、分钟、秒和毫秒的字符串形式。
     * @author lipeng
     * 2024/1/25
     */
    public static String formatDuration(Long milliseconds) {
        if (milliseconds == null) return "-1毫秒";
        if (milliseconds == 0) return "0毫秒";

        long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
        milliseconds -= TimeUnit.DAYS.toMillis(days);

        long hours = TimeUnit.MILLISECONDS.toHours(milliseconds);
        milliseconds -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);
        milliseconds -= TimeUnit.SECONDS.toMillis(seconds);

        String body = "";
        if (days != 0) body += CommonUtil.format("{0}天", days).toString();
        if (hours != 0) body += CommonUtil.format("{0}时", hours).toString();
        if (minutes != 0) body += CommonUtil.format("{0}分", minutes).toString();
        if (seconds != 0) body += CommonUtil.format("{0}秒", seconds).toString();
        if (milliseconds != 0) body += CommonUtil.format(" {0}毫秒", milliseconds).toString();
        return body;
    }

    /**
     * 计算两个日期时间（包含日期和时间）相差的分钟数
     * @author lipeng
     * 2026/1/22
     * @param startTimeStr 开始日期时间的字符串，格式为"yyyy-MM-dd HH:mm:ss"
     * @param endTimeStr 结束日期时间的字符串，格式为"yyyy-MM-dd HH:mm:ss"
     * @return long 相差的分钟数
     */
    public static long getSecondsBetween(String startTimeStr, String endTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(startTimeStr, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endTimeStr, formatter);
        Duration duration = Duration.between(startDateTime, endDateTime);
        return duration.getSeconds();
    }

    /**
     * 计算两个日期时间（包含日期和时间）相差的分钟数。
     * @author lipeng
     * 2024/5/7
     * @param startTime 开始日期时间
     * @param endTime 结束日期时间
     * @return long 相差的分钟数
     */
    public static long getSecondsBetween(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        return duration.getSeconds();
    }

    /**
     * 字符串替换  {num} 占位符 0 開始。
     * @author lipeng
     * 2022/5/10
     * @param format 字符串
     * @param args 占位符参数
     * @return java.lang.String
     */
    public static final String format(String format, Object... args) {
        try {
            if (format.contains("'")) format = format.replaceAll("'", "''");
            format = MessageFormat.format(format, args);
        } catch (Exception e) {
            for (int i = 0; i < args.length; i++) {
                format = format.replace("{" + i + "}", args[i].toString());
            }
        }
        return format;
    }


    public static final String toJsonString(Object obj) {
        try {
            // 配置 JSONWriter 特性
            JSONWriter.Feature[] features = {
                    JSONWriter.Feature.IgnoreErrorGetter,
                    JSONWriter.Feature.WriteMapNullValue
            };
            // 将对象转换为 JSON 字符串，并应用配置
            return JSON.toJSONString(obj, "yyyy-MM-dd HH:mm:ss", features);
        } catch (Exception e) {
            log.error("对象转JSON字符串失败：" + e.getMessage());
            throw new RuntimeException("数据转换异常！");
        }
    }

    public static final void set(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
        }
    }

    /**
     * 实现命令：SET key value EX seconds，设置key-value和超时时间（秒）
     */
    public static final void set(String key, Object value, long timeout) {
        try {
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
        }
    }

    public static final void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
        } catch (Exception e) {
        }
    }

    /**
     * 实现命令：GET key，返回 key所关联的字符串值。
     */
    public static final String get(String key) {
        try {
            return (String) redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
        }
        return "";
    }


    public static boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 字符串首字母小写。
     * @author lipeng
     * 2021/2/7
     */
    public static final String initialsLowerCase(String str) {
        if (Character.isLowerCase(str.charAt(0)))
            return str;

        return (new StringBuilder())
                .append(Character.toLowerCase(str.charAt(0)))
                .append(str.substring(1)).toString();
    }

    /**
     * 实现命令：expire 设置过期时间，单位秒
     */
    public static void expire(String key, long timeout) {
        try {
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
        }

    }


    public static String getClinetIP(HttpServletRequest request) {
        if (request == null) return "";

        String ip = null;
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-Ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-Ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("entrust-client-ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip.trim())) {
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    System.out.println("获取IP地址失败");
                }
                ip = inetAddress.getHostAddress();
            }
        }
        //多级 方向代理
        if (ip.indexOf(",") > 0) {
            ip = ip.substring(0, ip.indexOf(",")).trim();
        }

        return ip;
    }


    public static long secondsUntilEndOfDay() {
        // 获取当前日期的24:00时间（即次日的00:00）
        LocalDateTime endOfDay = LocalDateTime.now().toLocalDate().atTime(LocalTime.MAX);

        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 计算两者之间的秒数差
        if (now.isAfter(endOfDay)) {
            // 如果当前时间已超过当日24:00，则返回0
            return 0L;
        } else {
            return ChronoUnit.SECONDS.between(now, endOfDay);
        }
    }

    /**
     * 获取锁。
     */
    public static boolean lock(String key, int value, long releaseTime) {
        // 尝试获取锁
        Boolean boo = redisTemplate.opsForValue().setIfAbsent(key, value, releaseTime, TimeUnit.SECONDS);
        // 判断结果
        return boo != null && boo;
    }

    public static void removeBacth(String key) {
        try {
            redisTemplate.delete(redisTemplate.keys(key.concat("*")));
        } catch (Exception e) {
        }
    }

    /**
     * 从req中获取HandlerMethod。
     * @author lipeng
     * 2024/4/10
     * @return org.springframework.web.method.HandlerMethod
     */
    public static HandlerMethod queryHandlerMethod(HttpServletRequest req) {
        try {
            // 兼容所有 Spring Boot 3 版本，避免 getParsedRequestPath 方法不存在/访问受限
            ServletRequestPathUtils.parseAndCache(req);
            // 获取 HandlerMapping 并获取 HandlerExecutionChain
            RequestMappingHandlerMapping handlerMapping = ServiceUtil.getBean(RequestMappingHandlerMapping.class);
            HandlerExecutionChain handlerExecutionChain = handlerMapping.getHandler(req);
            // 校验并返回 HandlerMethod
            if (handlerExecutionChain != null && handlerExecutionChain.getHandler() instanceof HandlerMethod) {
                return (HandlerMethod) handlerExecutionChain.getHandler();
            }
        } catch (Exception e) {
        }
        return null;
    }
}
