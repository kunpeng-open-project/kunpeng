package com.kp.framework.utils.kptool;

import lombok.experimental.UtilityClass;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * 传统 Date 时间操作工具类。
 * @author lipeng
 * 2020/5/31
 */
@UtilityClass
public class KPDateUtil {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String HOUR_PATTERN = "yyyy-MM-dd HH";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String MONTH_PATTERN = "yyyy-MM";
    public static final String YEAR_PATTERN = "yyyy";
    public static final String TIMESTAMP_YMD = "yyyyMMdd";
    private static final String[] PARSE_PATTERNS = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 日期增减天数
     * @param date 目标日期（为 null 则取当前时间）
     * @param days 增减天数（正数加，负数减）
     * @param includeTime 是否包含时分秒
     */
    public Date addDays(Date date, int days, boolean includeTime) {
        if (date == null) {
            date = new Date();
        }
        if (!includeTime) {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
            try {
                date = sdf.parse(sdf.format(date));
            } catch (ParseException e) {
                return null;
            }
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * 时间格式化为字符串
     * @param date 目标日期
     * @param pattern 格式模板（为空则默认 yyyy-MM-dd）
     */
    public String format(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public String format(String date, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            Date sd = df.parse(date);
            return df.format(sd);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 转换日期格式
     * @param date 日期字符串
     * @param inputPattern 输入格式
     * @param outputPattern 输出格式
     */
    public String format(String date, String inputPattern, String outputPattern) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
            Date sd = inputFormat.parse(date);
            return outputFormat.format(sd);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 字符串解析为 Date 对象
     * @param dateTimeString 日期时间字符串
     * @param pattern 格式模板（为空则默认 yyyy-MM-dd）
     */
    public Date parse(String dateTimeString, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(dateTimeString);
        } catch (ParseException e) {
            return null;
        }
    }

    public Date formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(sdf.format(date));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 日期时间转换为纯日期字符串
     * @param dateTime 日期时间对象
     */
    public String toDateString(Date dateTime) throws ParseException {
        String dateTimeString = format(dateTime, DATE_TIME_PATTERN);
        return dateTimeString.substring(0, 10);
    }

    /**
     * 日期时间转换为纯日期字符串（时分秒为 00:00:00 时生效）
     * @param dateTime 日期时间对象
     */
    public String toDateStringIfTimeZero(Date dateTime) throws ParseException {
        String dateTimeString = format(dateTime, DATE_TIME_PATTERN);
        return dateTimeString.endsWith("00:00:00") ? dateTimeString.substring(0, 10) : dateTimeString;
    }

    /**
     * 日期时间转换为纯日期对象
     * @param dateTime 日期时间对象
     */
    public Date toDate(Date dateTime) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 时间增减小时
     * @param startDate 起始日期（为 null 则取当前时间）
     * @param hours 增减小时数（正数加，负数减）
     */
    public Date addHours(Date startDate, int hours) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.HOUR, hours);
        return c.getTime();
    }

    /**
     * 时间增减分钟
     * @param startDate 起始日期（为 null 则取当前时间）
     * @param minutes 增减分钟数（正数加，负数减）
     */
    public Date addMinutes(Date startDate, int minutes) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.MINUTE, minutes);
        return c.getTime();
    }

    /**
     * 时间增减秒数
     * @param startDate 起始日期（为 null 则取当前时间）
     * @param seconds 增减秒数（正数加，负数减）
     */
    public Date addSeconds(Date startDate, int seconds) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.SECOND, seconds);
        return c.getTime();
    }

    /**
     * 时间增减天数
     * @param startDate 起始日期（为 null 则取当前时间）
     * @param days 增减天数（正数加，负数减）
     */
    public Date addDays(Date startDate, int days) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    /**
     * 时间增减月数
     * @param startDate 起始日期（为 null 则取当前时间）
     * @param months 增减月数（正数加，负数减）
     */
    public Date addMonths(Date startDate, int months) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.MONTH, months);
        return c.getTime();
    }

    /**
     * 时间增减年数
     * @param startDate 起始日期（为 null 则取当前时间）
     * @param years 增减年数（正数加，负数减）
     */
    public Date addYears(Date startDate, int years) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.YEAR, years);
        return c.getTime();
    }

    /**
     * 时间比较
     * @param date1 日期 1
     * @param date2 日期 2
     * @return date1 > date2 返回 1；date1 < date2 返回 -1；相等返回 0
     */
    public int compare(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.compareTo(cal2);
    }

    /**
     * 获取两个时间的较小值
     * @param date1 日期 1
     * @param date2 日期 2
     */
    public Date min(Date date1, Date date2) {
        if (date1 == null) return date2;
        if (date2 == null) return date1;
        return compare(date1, date2) > 0 ? date2 : date1;
    }

    /**
     * 获取两个时间的较大值
     * @param date1 日期 1
     * @param date2 日期 2
     */
    public Date max(Date date1, Date date2) {
        if (date1 == null) return date2;
        if (date2 == null) return date1;
        return compare(date1, date2) > 0 ? date1 : date2;
    }

    /**
     * 两个日期（不含时分秒）的天数差（不包含今天）
     * @param startDate 起始日期
     * @param endDate 结束日期
     */
    public int daysBetween(Date startDate, Date endDate) {
        Date start = parse(format(startDate, DATE_PATTERN), DATE_PATTERN);
        Date end = parse(format(endDate, DATE_PATTERN), DATE_PATTERN);
        return (int) ((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 两个日期（不含时分秒）的天数差（包含今天）
     * @param startDate 起始日期
     * @param endDate 结束日期
     */
    public int daysBetweenIncludeToday(Date startDate, Date endDate) {
        try {
            return daysBetween(startDate, endDate) + 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取日期的年份
     * @param date 目标日期
     */
    public int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取日期的月份（1-12）
     * @param date 目标日期
     */
    public int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日期的天数（1-31）
     * @param date 目标日期
     */
    public int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DATE);
    }

    /**
     * 获取当月的总天数
     * @param date 目标日期
     */
    public int getDaysOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取当年的总天数
     * @param date 目标日期
     */
    public int getDaysOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获取当月最大日期
     * @param date 目标日期
     */
    public Date getMaxDayOfMonth(Date date) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int maxDay = cal.getActualMaximum(Calendar.DATE);
        return parse(format(date, MONTH_PATTERN) + "-" + maxDay, null);
    }

    /**
     * 获取当月最小日期（当月 1 号）
     * @param date 目标日期
     */
    public Date getMinDayOfMonth(Date date) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int minDay = cal.getActualMinimum(Calendar.DATE);
        return parse(format(date, MONTH_PATTERN) + "-" + minDay, null);
    }

    /**
     * 获取本周周一日期
     */
    public String getMinDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return format(cal.getTime(), DATE_PATTERN);
    }

    /**
     * 获取本周最后一天日期
     */
    public String getMaxDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.add(Calendar.DATE, 6);
        return format(cal.getTime(), DATE_PATTERN);
    }

    /**
     * 时间戳转换为日期字符串
     * @param seconds 秒级时间戳字符串
     * @param format 格式模板（为空则默认 yyyy-MM-dd HH:mm:ss）
     */
    public String timestampToDate(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || "null".equals(seconds)) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.parseLong(seconds)));
    }

    /**
     * 日期字符串转换为时间戳（毫秒）
     * @param dateStr 日期字符串
     * @param format 格式模板
     */
    public Long dateToTimestamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr).getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前秒级时间戳
     */
    public String getTimestamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * 获取当天凌晨（00:00:00）
     */
    public Date getWeeHours() {
        String str = new SimpleDateFormat(DATE_PATTERN).format(new Date()) + " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取指定年份的第一天
     * @param date 目标日期（为 null 则取当前时间）
     */
    public Date getFirstDayOfYear(Date date) {
        date = date == null ? new Date() : date;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
        String firstDay = new SimpleDateFormat(YEAR_PATTERN).format(date) + "-01-01 00:00:00";
        try {
            return sdf.parse(firstDay);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取指定年份的最后一天
     * @param date 目标日期（为 null 则取当前时间）
     */
    public Date getLastDayOfYear(Date date) {
        date = date == null ? new Date() : date;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.getActualMaximum(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.YEAR, Integer.parseInt(format(date, YEAR_PATTERN)));
        String lastDay = new SimpleDateFormat(DATE_PATTERN).format(cal.getTime()) + " 23:59:59";
        try {
            return sdf.parse(lastDay);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
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
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date 转换为 LocalDateTime
     * @param date 目标 Date
     */
    public LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
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
    public Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差（天、时、分）
     * @param endDate 结束日期
     * @param nowDate 当前日期
     */
    public String getDuration(Date endDate, Date nowDate) {
        long nd = 1000L * 24 * 60 * 60;
        long nh = 1000L * 60 * 60;
        long nm = 1000L * 60;
        long diff = endDate.getTime() - nowDate.getTime();
        long day = diff / nd;
        long hour = diff % nd / nh;
        long min = diff % nd % nh / nm;
        return day + "天" + hour + "小时" + min + "分钟";
    }


    //------------------------已经改名弃用  保留是为了兼容老代码 新写的代码请勿使用---------------------------
    @Deprecated
    public static String dateFormat(Date date, String pattern) {
        return format(date, pattern);
    }

    @Deprecated
    public static String dateFormat(String date, String pattern) {
        return format(date, pattern);
    }

    @Deprecated
    public static String dateFormat(String date, String inputPattern, String outputPattern) {
        return format(date, inputPattern, outputPattern);
    }

    @Deprecated
    public static String getDatePoor(Date endDate, Date nowDate) {
        return getDuration(endDate, nowDate);
    }

    @Deprecated
    public static Date dateAdd(Date date, int days, boolean includeTime) {
        return addDays(date, days, includeTime);
    }
}