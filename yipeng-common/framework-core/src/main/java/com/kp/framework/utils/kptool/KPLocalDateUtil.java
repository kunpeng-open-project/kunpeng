package com.kp.framework.utils.kptool;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * @Author lipeng
 * @Description LocalDate 工具类（处理日期，不含时间），其方法名与 KPDateUtil 保持一致，以便于项目迁移。
 * @Date 2025/8/5
 */
@UtilityClass
public class KPLocalDateUtil {

    // 日期格式常量（均为纯日期格式，不含时间）
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String MONTH_PATTERN = "yyyy-MM";
    public static final String YEAR_PATTERN = "yyyy";
    public static final String TIMESTAMP_YMD = "yyyyMMdd";

    /**
     * 时间格式化为字符串
     * @param date 目标日期
     * @param pattern 格式模板
     */
    public String format(LocalDate date, String pattern) {
        if (date == null) {
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public String format(String dateStr, String pattern) {
        return parse(dateStr, pattern).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 转换日期格式
     * @param dateStr 日期字符串
     * @param inputPattern 输入格式
     * @param outputPattern 输出格式
     */
    public String format(String dateStr, String inputPattern, String outputPattern) {
        LocalDate date = parse(dateStr, inputPattern);
        return format(date, outputPattern);
    }

    /**
     * 字符串解析为 LocalDate 对象
     * @param dateTimeString 日期时间字符串
     * @param pattern 格式模板
     */
    public LocalDate parse(String dateTimeString, String pattern) {
        if (dateTimeString == null || dateTimeString.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(dateTimeString, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 字符串解析为 LocalDate 对象（默认格式）
     * @param dateTimeString 日期时间字符串
     */
    public LocalDate parse(String dateTimeString) {
        return parse(dateTimeString, DATE_PATTERN);
    }

    /**
     * 日期增减天数
     * @param date 目标日期 (为 null 则取当前时间)
     * @param days 增减天数 (正数加，负数减)
     */
    public LocalDate addDays(LocalDate date, int days) {
        if (date == null) {
            date = LocalDate.now();
        }
        return date.plusDays(days);
    }

    /**
     * 时间增减月数
     * @param date 起始日期 (为 null 则取当前时间)
     * @param months 增减月数 (正数加，负数减)
     */
    public LocalDate addMonths(LocalDate date, int months) {
        if (date == null) {
            date = LocalDate.now();
        }
        return date.plusMonths(months);
    }

    /**
     * 时间增减年数
     * @param date 起始日期 (为 null 则取当前时间)
     * @param years 增减年数 (正数加，负数减)
     */
    public LocalDate addYears(LocalDate date, int years) {
        if (date == null) {
            date = LocalDate.now();
        }
        return date.plusYears(years);
    }

    /**
     * 时间比较
     * @param date1 日期 1
     * @param date2 日期 2
     * @return date1.isAfter(date2) 返回 1；date1.isBefore(date2) 返回 -1；相等返回 0
     */
    public int compare(LocalDate date1, LocalDate date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("日期不能为 null");
        }
        if (date1.isAfter(date2)) return 1;
        if (date1.isBefore(date2)) return -1;
        return 0;
    }

    /**
     * 获取两个时间的较小值
     * @param date1 日期 1
     * @param date2 日期 2
     */
    public LocalDate min(LocalDate date1, LocalDate date2) {
        if (date1 == null) return date2;
        if (date2 == null) return date1;
        return date1.isBefore(date2) ? date1 : date2;
    }

    /**
     * 获取两个时间的较大值
     * @param date1 日期 1
     * @param date2 日期 2
     */
    public LocalDate max(LocalDate date1, LocalDate date2) {
        if (date1 == null) return date2;
        if (date2 == null) return date1;
        return date1.isAfter(date2) ? date1 : date2;
    }

    /**
     * 两个日期的天数差 (不包含结束日)
     * @param startDate 起始日期
     * @param endDate 结束日期
     */
    public long daysBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("日期不能为 null");
        }
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * 两个日期的天数差 (包含结束日)
     * @param startDate 起始日期
     * @param endDate 结束日期
     */
    public long daysBetweenIncludeToday(LocalDate startDate, LocalDate endDate) {
        return daysBetween(startDate, endDate) + 1;
    }

    /**
     * 获取日期的年份
     * @param date 目标日期
     */
    public int getYear(LocalDate date) {
        if (date == null) {
            return LocalDate.now().getYear();
        }
        return date.getYear();
    }

    /**
     * 获取日期的月份 (1-12)
     * @param date 目标日期
     */
    public int getMonth(LocalDate date) {
        if (date == null) {
            return LocalDate.now().getMonthValue();
        }
        return date.getMonthValue();
    }

    /**
     * 获取日期的天数 (1-31)
     * @param date 目标日期
     */
    public int getDay(LocalDate date) {
        if (date == null) {
            return LocalDate.now().getDayOfMonth();
        }
        return date.getDayOfMonth();
    }

    /**
     * 获取当月的总天数
     * @param date 目标日期
     */
    public int getDaysOfMonth(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        return date.lengthOfMonth();
    }

    /**
     * 获取当年的总天数
     * @param date 目标日期
     */
    public int getDaysOfYear(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        return date.lengthOfYear();
    }

    /**
     * 获取当月最大日期
     * @param date 目标日期
     */
    public LocalDate getMaxDayOfMonth(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 获取当月最小日期 (当月 1 号)
     * @param date 目标日期
     */
    public LocalDate getMinDayOfMonth(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 获取本周周一日期
     */
    public String getMinDayOfWeek() {
        LocalDate monday = LocalDate.now().with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        return format(monday, DATE_PATTERN);
    }

    /**
     * 获取本周最后一天日期
     */
    public String getMaxDayOfWeek() {
        LocalDate sunday = LocalDate.now().with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));
        return format(sunday, DATE_PATTERN);
    }

    /**
     * 获取指定年份的第一天
     * @param date 目标日期 (为 null 则取当前时间)
     */
    public LocalDate getFirstDayOfYear(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        return date.with(TemporalAdjusters.firstDayOfYear());
    }

    /**
     * 获取指定年份的最后一天
     * @param date 目标日期 (为 null 则取当前时间)
     */
    public LocalDate getLastDayOfYear(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        return date.with(TemporalAdjusters.lastDayOfYear());
    }

    // ------------------------------ 以下为 KPLocalDateUtil 原有的、KPDateUtil 没有的方法 ------------------------------

    /**
     * 计算两个日期之间的月数差（结束日期 - 开始日期）
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return 相差的月数（可为负数）
     */
    public long getMonthsBetween(LocalDate beginDate, LocalDate endDate) {
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
    public long getYearsBetween(LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            throw new IllegalArgumentException("日期不能为 null");
        }
        return ChronoUnit.YEARS.between(beginDate, endDate);
    }


    /**
     * 判断日期是否在指定范围内（包含边界）
     * @param date 要判断的日期
     * @param start 开始日期（包含）
     * @param end 结束日期（包含）
     * @return true：在范围内；false：不在范围内（或任意日期为 null）
     */
    public boolean isBetween(LocalDate date, LocalDate start, LocalDate end) {
        if (date == null || start == null || end == null) {
            return false;
        }
        return !date.isBefore(start) && !date.isAfter(end);
    }

    /**
     * 获取有效日期（若输入日期为null则返回当前日期，否则返回原日期）
     * @param localDate 待校验的日期对象（可为null）
     * @return java.time.LocalDate 有效日期（非null）
     */
    public LocalDate getEffectiveDate(LocalDate localDate) {
        if (localDate == null) return LocalDate.now();
        return localDate;
    }

    public LocalDate getEffectiveDate(String localDateString) {
        if (localDateString == null || localDateString.trim().isEmpty()) return LocalDate.now();
        try {
            return parse(localDateString);
        } catch (Exception e) {
            // 解析异常时（如格式不匹配），返回当前日期
            return LocalDate.now();
        }
    }


    //------------------------已经改名弃用  保留是为了兼容老代码 新写的代码请勿使用---------------------------
    @Deprecated
    public static LocalDate getFirstDayOfMonth(LocalDate date) {
        return getMinDayOfMonth(date);
    }
    @Deprecated
    public static String toString(LocalDate localDate, String format) {
        return format(localDate, format);
    }
}