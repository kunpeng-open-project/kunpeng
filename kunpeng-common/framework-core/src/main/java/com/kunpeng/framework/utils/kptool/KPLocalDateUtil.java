package com.kunpeng.framework.utils.kptool;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * @Author lipeng
 * @Description LocalDate 工具类（处理日期，不含时间）
 * @Date 2025/8/5 15:34
 */
public class KPLocalDateUtil {

    // 日期格式常量（均为纯日期格式，不含时间）
    public static final String DATE_PATTERN = "yyyy-MM-dd";       // 年月日（默认）
    public static final String MONTH_PATTERN = "yyyy-MM";         // 年月
    public static final String YEAR_PATTERN = "yyyy";             // 年
    public static final String MONTH_DAY_PATTERN = "MM-dd";       // 月日
    public static final String TIMESTAMP_YMD = "yyyyMMdd";        // 年月日紧凑格式（无分隔符）

    /**
     * LocalDate 转字符串（指定格式）
     * @param localDate 日期对象
     * @param format 目标格式（如 DATE_PATTERN）
     * @return 格式化后的日期字符串
     */
    public static String toString(LocalDate localDate, String format) {
        if (localDate == null) {
            return null;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return df.format(localDate);
    }

    /**
     * LocalDate 转字符串（默认格式：yyyy-MM-dd）
     * @param localDate 日期对象
     * @return 格式化后的日期字符串（如 2025-08-05）
     */
    public static String toString(LocalDate localDate) {
        return toString(localDate, DATE_PATTERN);
    }

    /**
     * 字符串转 LocalDate（指定格式）
     * @param dateStr 日期字符串（如 "2025-08-05"）
     * @param format 字符串格式（需与 dateStr 匹配）
     * @return 解析后的 LocalDate 对象
     */
    public static LocalDate parse(String dateStr, String format) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(dateStr, df);
    }

    /**
     * 字符串转 LocalDate（默认格式：yyyy-MM-dd）
     * @param dateStr 日期字符串（如 "2025-08-05"）
     * @return 解析后的 LocalDate 对象
     */
    public static LocalDate parse(String dateStr) {
        return parse(dateStr, DATE_PATTERN);
    }

    /**
     * 计算两个日期之间的天数差（结束日期 - 开始日期）
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return 相差的天数（可为负数，若 endDate 在 beginDate 之前）
     */
    public static long getDaysBetween(LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            throw new IllegalArgumentException("日期不能为 null");
        }
        return ChronoUnit.DAYS.between(beginDate, endDate);
    }

    /**
     * 计算两个日期之间的月数差（结束日期 - 开始日期）
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return 相差的月数（可为负数）
     */
    public static long getMonthsBetween(LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            throw new IllegalArgumentException("日期不能为 null");
        }
        return ChronoUnit.MONTHS.between(beginDate, endDate);
    }

    /**
     * 计算两个日期之间的年数差（结束日期 - 开始日期）
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return 相差的年数（可为负数）
     */
    public static long getYearsBetween(LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            throw new IllegalArgumentException("日期不能为 null");
        }
        return ChronoUnit.YEARS.between(beginDate, endDate);
    }

    /**
     * 给日期增加指定天数
     * @param date 原日期
     * @param days 要增加的天数（正数往后，负数往前）
     * @return 计算后的日期
     */
    public static LocalDate plusDays(LocalDate date, long days) {
        if (date == null) {
            return null;
        }
        return date.plusDays(days);
    }

    /**
     * 给日期增加指定月数
     * @param date 原日期
     * @param months 要增加的月数（正数往后，负数往前）
     * @return 计算后的日期
     */
    public static LocalDate plusMonths(LocalDate date, long months) {
        if (date == null) {
            return null;
        }
        return date.plusMonths(months);
    }

    /**
     * 给日期增加指定年数
     * @param date 原日期
     * @param years 要增加的年数（正数往后，负数往前）
     * @return 计算后的日期
     */
    public static LocalDate plusYears(LocalDate date, long years) {
        if (date == null) {
            return null;
        }
        return date.plusYears(years);
    }

    /**
     * 获取当月的第一天
     * @param date 任意日期（以该日期所在月为准）
     * @return 当月第一天（如 2025-08-01）
     */
    public static LocalDate getFirstDayOfMonth(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.withDayOfMonth(1);
    }

    /**
     * 获取当月的最后一天
     * @param date 任意日期（以该日期所在月为准）
     * @return 当月最后一天（如 2025-08-31）
     */
    public static LocalDate getLastDayOfMonth(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.withDayOfMonth(date.lengthOfMonth());
    }

    /**
     * 判断两个日期是否为同一天
     * @param date1 第一个日期
     * @param date2 第二个日期
     * @return true：同一天；false：不同天（或任意为 null）
     */
    public static boolean isSameDay(LocalDate date1, LocalDate date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.isEqual(date2);
    }

    /**
     * 判断日期是否在指定范围内（包含边界）
     * @param date 要判断的日期
     * @param start 开始日期（包含）
     * @param end 结束日期（包含）
     * @return true：在范围内；false：不在范围内（或任意日期为 null）
     */
    public static boolean isBetween(LocalDate date, LocalDate start, LocalDate end) {
        if (date == null || start == null || end == null) {
            return false;
        }
        return date.isAfter(start.minusDays(1)) && date.isBefore(end.plusDays(1));
    }

// TODO 加入

    /**
     * @Author lipeng
     * @Description 获取当前年份的第一天（仅日期）
     * @Date 2025/8/15 补充方法
     * @return java.time.LocalDate 当前年份的第一天
     **/
    public static LocalDate getFirstDayOfCurrentYear() {
        // 获取当前日期并定位到当前年份的第一天
        return LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
    }

    /**
     * @Author lipeng
     * @Description 获取当前年份的最后一天（仅日期）
     * @Date 2025/8/15 补充方法
     * @return java.time.LocalDate 当前年份的最后一天
     **/
    public static LocalDate getLastDayOfCurrentYear() {
        // 获取当前日期并定位到当前年份的最后一天
        return LocalDate.now().with(TemporalAdjusters.lastDayOfYear());
    }



    /**
     * @Author lipeng
     * @Description 获取有效日期（若输入日期为null则返回当前日期，否则返回原日期）
     * @Date 2025/8/28
     * @param localDate 待校验的日期对象（可为null）
     * @return java.time.LocalDate 有效日期（非null）
     */
    public static LocalDate getEffectiveDate(LocalDate localDate){
        if (localDate == null) return LocalDate.now();
        return localDate;
    }

    public static LocalDate getEffectiveDate(String localDateString){
        if (KPStringUtil.isEmpty(localDateString))  return LocalDate.now();
        try {
            LocalDate parsedDate = parse(localDateString);
            // 解析成功则返回，失败则返回当前日期
            return parsedDate != null ? parsedDate : LocalDate.now();
        } catch (Exception e) {
            // 解析异常时（如格式不匹配），返回当前日期
            return LocalDate.now();
        }
    }
}