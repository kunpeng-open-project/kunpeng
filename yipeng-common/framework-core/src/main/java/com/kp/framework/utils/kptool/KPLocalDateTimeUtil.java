package com.kp.framework.utils.kptool;

import lombok.experimental.UtilityClass;

import java.lang.management.ManagementFactory;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author lipeng
 * @Description LocalDateTime 工具类，其方法名与 KPDateUtil 保持一致，以便于项目迁移。
 * @Date 2023/10/24
 */
@UtilityClass
public class KPLocalDateTimeUtil {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String HOUR_PATTERN = "yyyy-MM-dd HH";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String MONTH_PATTERN = "yyyy-MM";
    public static final String YEAR_PATTERN = "yyyy";
    public static final String TIMESTAMP_YMD = "yyyyMMdd";

    /**
     * 日期增减天数
     * @param date 目标日期 (为 null 则取当前时间)
     * @param days 增减天数 (正数加，负数减)
     * @param includeTime 是否包含时分秒
     */
    public LocalDateTime addDays(LocalDateTime date, int days, boolean includeTime) {
        if (date == null) {
            date = LocalDateTime.now();
        }
        if (!includeTime) {
            date = date.with(LocalTime.MIN);
        }
        return date.plusDays(days);
    }

    /**
     * 时间格式化为字符串
     * @param date 目标日期
     * @param pattern 格式模板
     */
    public String format(LocalDateTime date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public String format(String dateStr, String pattern) {
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(pattern))
                .format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 转换日期格式
     * @param dateStr 日期字符串
     * @param inputPattern 输入格式
     * @param outputPattern 输出格式
     */
    public String format(String dateStr, String inputPattern, String outputPattern) {
        LocalDateTime dateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(inputPattern));
        return dateTime.format(DateTimeFormatter.ofPattern(outputPattern));
    }

    /**
     * 字符串解析为 LocalDateTime 对象
     * @param dateTimeString 日期时间字符串
     * @param pattern 格式模板
     */
    public LocalDateTime parse(String dateTimeString, String pattern) {
        return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 时间增减小时
     * @param startDate 起始日期 (为 null 则取当前时间)
     * @param hours 增减小时数 (正数加，负数减)
     */
    public LocalDateTime addHours(LocalDateTime startDate, int hours) {
        if (startDate == null) {
            startDate = LocalDateTime.now();
        }
        return startDate.plusHours(hours);
    }

    /**
     * 时间增减分钟
     * @param startDate 起始日期 (为 null 则取当前时间)
     * @param minutes 增减分钟数 (正数加，负数减)
     */
    public LocalDateTime addMinutes(LocalDateTime startDate, int minutes) {
        if (startDate == null) {
            startDate = LocalDateTime.now();
        }
        return startDate.plusMinutes(minutes);
    }

    /**
     * 时间增减秒数
     * @param startDate 起始日期 (为 null 则取当前时间)
     * @param seconds 增减秒数 (正数加，负数减)
     */
    public LocalDateTime addSeconds(LocalDateTime startDate, int seconds) {
        if (startDate == null) {
            startDate = LocalDateTime.now();
        }
        return startDate.plusSeconds(seconds);
    }

    /**
     * 时间增减天数 (重载方法，包含时分秒)
     * @param startDate 起始日期 (为 null 则取当前时间)
     * @param days 增减天数 (正数加，负数减)
     */
    public LocalDateTime addDays(LocalDateTime startDate, int days) {
        if (startDate == null) {
            startDate = LocalDateTime.now();
        }
        return startDate.plusDays(days);
    }

    /**
     * 时间增减月数
     * @param startDate 起始日期 (为 null 则取当前时间)
     * @param months 增减月数 (正数加，负数减)
     */
    public LocalDateTime addMonths(LocalDateTime startDate, int months) {
        if (startDate == null) {
            startDate = LocalDateTime.now();
        }
        return startDate.plusMonths(months);
    }

    /**
     * 时间增减年数
     * @param startDate 起始日期 (为 null 则取当前时间)
     * @param years 增减年数 (正数加，负数减)
     */
    public LocalDateTime addYears(LocalDateTime startDate, int years) {
        if (startDate == null) {
            startDate = LocalDateTime.now();
        }
        return startDate.plusYears(years);
    }

    /**
     * 时间比较
     * @param date1 日期 1
     * @param date2 日期 2
     * @return date1.isAfter(date2) 返回 1；date1.isBefore(date2) 返回 -1；相等返回 0
     */
    public int compare(LocalDateTime date1, LocalDateTime date2) {
        if (date1.isAfter(date2)) return 1;
        if (date1.isBefore(date2)) return -1;
        return 0;
    }

    /**
     * 获取两个时间的较小值
     * @param date1 日期 1
     * @param date2 日期 2
     */
    public LocalDateTime min(LocalDateTime date1, LocalDateTime date2) {
        if (date1 == null) return date2;
        if (date2 == null) return date1;
        return date1.isBefore(date2) ? date1 : date2;
    }

    /**
     * 获取两个时间的较大值
     * @param date1 日期 1
     * @param date2 日期 2
     */
    public LocalDateTime max(LocalDateTime date1, LocalDateTime date2) {
        if (date1 == null) return date2;
        if (date2 == null) return date1;
        return date1.isAfter(date2) ? date1 : date2;
    }

    /**
     * 两个日期（不含时分秒）的天数差 (不包含今天)
     * @param startDate 起始日期
     * @param endDate 结束日期
     */
    public long daysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * 两个日期（不含时分秒）的天数差 (不包含今天)
     *  两个时间点之间的完整天数。例如，从 2024-01-01 23:00:00 到 2024-01-02 01:00:00，结果为 0。满24小时才算一天 如果 不考虑时间 使用KPLocalDateTimeUtil
     * @param startDateTime 起始日期
     * @param endDateTime 结束日期
     */
    public long daysBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return ChronoUnit.DAYS.between(startDateTime.toLocalDate(), endDateTime.toLocalDate());
    }

    /**
     * 两个日期（不含时分秒）的天数差 (包含今天)
     * @param startDate 起始日期
     * @param endDate 结束日期
     */
    public long daysBetweenIncludeToday(LocalDate startDate, LocalDate endDate) {
        return daysBetween(startDate, endDate) + 1;
    }

    public long daysBetweenIncludeToday(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return daysBetween(startDateTime, endDateTime) + 1;
    }

    /**
     * 获取日期的年份
     * @param date 目标日期
     */
    public int getYear(LocalDateTime date) {
        return date.getYear();
    }

    /**
     * 获取日期的月份 (1-12)
     * @param date 目标日期
     */
    public int getMonth(LocalDateTime date) {
        return date.getMonthValue();
    }

    /**
     * 获取日期的天数 (1-31)
     * @param date 目标日期
     */
    public int getDay(LocalDateTime date) {
        return date.getDayOfMonth();
    }

    /**
     * 获取当月的总天数
     * @param date 目标日期
     */
    public int getDaysOfMonth(LocalDateTime date) {
        return date.toLocalDate().lengthOfMonth();
    }

    /**
     * 获取当年的总天数
     * @param date 目标日期
     */
    public int getDaysOfYear(LocalDateTime date) {
        return date.toLocalDate().lengthOfYear();
    }

    /**
     * 获取当月最大日期
     * @param date 目标日期
     */
    public LocalDateTime getMaxDayOfMonth(LocalDateTime date) {
        return date.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
    }

    /**
     * 获取当月最小日期 (当月 1 号)
     * @param date 目标日期
     */
    public LocalDateTime getMinDayOfMonth(LocalDateTime date) {
        return date.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
    }

    /**
     * 获取本周周一日期
     */
    public String getMinDayOfWeek() {
        return LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    /**
     * 获取本周最后一天日期
     */
    public String getMaxDayOfWeek() {
        return LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                .format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    /**
     * 时间戳转换为日期字符串
     * @param seconds 秒级时间戳字符串
     * @param format 格式模板 (为空则默认 yyyy-MM-dd HH:mm:ss)
     */
    public String timestampToDate(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || "null".equals(seconds)) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = DATE_TIME_PATTERN;
        }
        return Instant.ofEpochSecond(Long.parseLong(seconds))
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 日期字符串转换为时间戳 (毫秒)
     * @param dateStr 日期字符串
     * @param format 格式模板
     */
    public Long dateToTimestamp(String dateStr, String format) {
        try {
            return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(format))
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前秒级时间戳
     */
    public String getTimestamp() {
        return String.valueOf(Instant.now().getEpochSecond());
    }

    /**
     * 获取当天凌晨 (00:00:00)
     */
    public LocalDateTime getWeeHours() {
        return LocalDate.now().atStartOfDay();
    }

    /**
     * 获取指定日期的凌晨 (00:00:00)
     */
    public LocalDateTime getWeeHours(LocalDateTime date) {
        if (date == null) date = LocalDateTime.now();
        return date.toLocalDate().atStartOfDay();
    }
    public LocalDateTime getWeeHours(LocalDate date) {
        if (date == null) date = LocalDate.now();
        return date.atStartOfDay().toLocalDate().atStartOfDay();
    }


    /**
     * 获取当天晚时间 (23:59:59)
     */
    public static LocalDateTime getWitchingHour() {
        return LocalDateTime.now().toLocalDate().atTime(LocalTime.MAX);
    }

    /**
     *  获取指定日期的最晚时间 (23:59:59)
     **/
    public static LocalDateTime getWitchingHour(LocalDateTime date) {
        if (date == null) date = LocalDateTime.now();
        return date.toLocalDate().atTime(LocalTime.MAX);
    }
    public static LocalDateTime getWitchingHour(LocalDate date) {
        if (date == null) date = LocalDate.now();
        return date.atStartOfDay().toLocalDate().atTime(LocalTime.MAX);
    }


    /**
     * 获取指定年份的第一天
     * @param date 目标日期 (为 null 则取当前时间)
     */
    public LocalDateTime getFirstDayOfYear(LocalDateTime date) {
        date = date == null ? LocalDateTime.now() : date;
        return date.with(TemporalAdjusters.firstDayOfYear()).with(LocalTime.MIN);
    }

    /**
     * 获取指定年份的最后一天
     * @param date 目标日期 (为 null 则取当前时间)
     */
    public LocalDateTime getLastDayOfYear(LocalDateTime date) {
        date = date == null ? LocalDateTime.now() : date;
        return date.with(TemporalAdjusters.lastDayOfYear()).with(LocalTime.MAX);
    }

    /**
     * Date 转换为 LocalDateTime
     * @param date 目标 Date
     */
    public LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * LocalDateTime 转换为 Date
     * @param localDateTime 目标 LocalDateTime
     */
    public Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDate 转换为 Date
     * @param localDate 目标 LocalDate
     */
    public Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 秒数转换为分秒格式字符串
     * @param seconds 秒数
     */
    public String toMinuteSecond(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return minutes + "分" + remainingSeconds + "秒";
    }

    /**
     * 获取服务器启动时间
     */
    public LocalDateTime getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 计算两个时间差 (天、时、分)
     * @param endDate 结束日期
     * @param nowDate 当前日期
     */
    public String getDuration(LocalDateTime endDate, LocalDateTime nowDate) {
        // 确保 endDate 在 nowDate 之后，否则结果可能为负
        if (endDate.isBefore(nowDate)) {
            return "0天0小时0分钟";
        }

        Duration duration = Duration.between(nowDate, endDate);

        long days = duration.toDays();
        // 减去已经计算过的天数
        duration = duration.minusDays(days);

        long hours = duration.toHours();
        // 减去已经计算过的小时数
        duration = duration.minusHours(hours);

        long minutes = duration.toMinutes();

        return days + "天" + hours + "小时" + minutes + "分钟";
    }


    /**
     * 将毫秒数转换为天、小时、分钟、秒和毫秒的字符串形式
     * @param milliseconds 毫秒数
     */
    public String formatDuration(Long milliseconds) {
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
        if (days != 0) body += KPStringUtil.format("{0}天", days).toString();
        if (hours != 0) body += KPStringUtil.format("{0}时", hours).toString();
        if (minutes != 0) body += KPStringUtil.format("{0}分", minutes).toString();
        if (seconds != 0) body += KPStringUtil.format("{0}秒", seconds).toString();
        if (milliseconds != 0) body += KPStringUtil.format(" {0}毫秒", milliseconds).toString();
        return body;
    }

    /**
     * 计算两个日期时间（包含日期和时间）相差的秒数。
     * @param startTime 开始日期时间
     * @param endTime 结束日期时间
     */
    public long getSecondsBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.between(startTime, endTime).getSeconds();
    }

    /**
     * 计算两个日期时间字符串相差的秒数。
     * @param startTimeStr 开始日期时间字符串
     * @param endTimeStr 结束日期时间字符串
     * @param pattern 格式模板
     */
    public long getSecondsBetween(String startTimeStr, String endTimeStr, String pattern) {
        LocalDateTime start = LocalDateTime.parse(startTimeStr, DateTimeFormatter.ofPattern(pattern));
        LocalDateTime end = LocalDateTime.parse(endTimeStr, DateTimeFormatter.ofPattern(pattern));
        return getSecondsBetween(start, end);
    }

    /**
     * 获取从当前时间到当日24:00（次日0点）剩余的秒数。
     */
    public long secondsUntilEndOfDay() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endOfDay = now.with(LocalTime.MAX);
        return Duration.between(now, endOfDay).getSeconds();
    }

    /**
     * 根据给定的时间戳（秒）获取 LocalDateTime 表示。
     * @param timestampInSeconds 时间戳（秒）
     * @param zoneId 时区
     */
    public LocalDateTime fromTimeMilli(Long timestampInSeconds, ZoneId zoneId) {
        // 注意：方法名中的 "Milli" 可能是笔误，参数是秒。为保持兼容性，暂不修改方法名。
        // 如果参数确实是毫秒，请使用 Instant.ofEpochMilli(timestampInSeconds)
        return Instant.ofEpochSecond(timestampInSeconds).atZone(zoneId).toLocalDateTime();
    }


    //------------------------已经改名弃用  保留是为了兼容老代码 新写的代码请勿使用---------------------------
    @Deprecated
    public static LocalDateTime getFirstDateTimeOfDay(LocalDateTime dateTime) {
        return getWeeHours(dateTime);
    }
    @Deprecated
    public static LocalDateTime getFirstDateTimeOfDay(LocalDate date) {
        return getWeeHours(date);
    }
    @Deprecated
    public static LocalDateTime getFirstDateTimeOfDay() {
        return getWeeHours();
    }
    @Deprecated
    public static LocalDateTime getLastDateTimeOfDay(LocalDateTime dateTime) {
        return getWitchingHour(dateTime);
    }
    @Deprecated
    public static LocalDateTime getLastDateTimeOfDay(LocalDate localDate) {
        return getWitchingHour(localDate);
    }
    @Deprecated
    public static LocalDateTime getLastDateTimeOfDay() {
        return getWitchingHour();
    }


}