package com.kunpeng.framework.utils.kptool;


import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.concurrent.TimeUnit;

/**
 * @Author lipeng
 * @Description LocalDateTime 工具
 * @Date 2023/10/24 14:45
 * @return
 **/
public class KPLocalDateTimeUtil {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String HOUR_PATTERN = "yyyy-MM-dd HH";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String MONTH_PATTERN = "yyyy-MM";
    public static final String YEAR_PATTERN = "yyyy";
    public static final String MINUTE_ONLY_PATTERN = "MM";
    public static final String HOUR_ONLY_PATTERN = "HH";
    public static final String TIMESTAMP_YMD = "yyyyMMdd";

    public static final ZoneId ZONEID_ASIA_ASIA = ZoneId.of("Asia/Shanghai");


    /**
     * @Author lipeng
     * @Description LocalDateTime 转 string
     * @Date 2023/10/24 14:47
     * @param localDateTime
     * @param format
     * @return java.lang.String
     **/
    public static String byString(LocalDateTime localDateTime, String format){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return df.format(localDateTime);
    }

    public static String byString(String localDateTime, String format){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return df.format(LocalDateTime.parse(localDateTime, df));
    }

    /**
     * @Author lipeng
     * @Description string 转 LocalDateTime
     * @Date 2023/10/24 14:55
     * @param str
     * @param format
     * @return java.time.LocalDateTime
     **/
    public static LocalDateTime byLocalDateTime(String str,String format){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(str,df);
    }

    /**
     * @Author lipeng
     * @Description 获取两个日期相差的天数
     * @Date 2023/10/24 14:56
     * @param beginDateTime
     * @param endDateTime
     * @return java.lang.Integer
     **/
    public static Integer getApartDay(LocalDateTime beginDateTime,LocalDateTime endDateTime){
        return endDateTime.getDayOfYear()-beginDateTime.getDayOfYear();
    }

    /**
     * @Author lipeng
     * @Description 获取一天最早的时间
     * @Date 2023/10/24 14:56
     * @param dateTime
     * @return java.time.LocalDateTime
     **/
    public static LocalDateTime getFirstDateTimeOfDay(LocalDateTime dateTime){
        if (dateTime == null) dateTime = LocalDateTime.now();
        return LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MIN);
    }
    public static LocalDateTime getFirstDateTimeOfDay(LocalDate localDate){
        if (localDate == null) localDate = LocalDate.now();
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }
    public static LocalDateTime getFirstDateTimeOfDay(){
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    }

    /**
     * @Author lipeng
     * @Description 获取一天最晚的时间
     * @Date 2023/10/24 14:57
     * @param dateTime
     * @return java.time.LocalDateTime
     **/
    public static LocalDateTime getLastDateTimeOfDay(LocalDateTime dateTime){
        if (dateTime == null) dateTime = LocalDateTime.now();
        return LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MAX);
    }
    public static LocalDateTime getLastDateTimeOfDay(LocalDate localDate){
        if (localDate == null) localDate = LocalDate.now();
        return LocalDateTime.of(localDate, LocalTime.MAX);
    }
    public static LocalDateTime getLastDateTimeOfDay(){
        return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    }

   /**
    * @Author lipeng
    * @Description 取一个月内的最早一天
    * @Date 2023/10/24 14:57
    * @param dateTime
    * @return java.time.LocalDateTime
    **/
    public static LocalDateTime getFirstDateTimeOfMonth(LocalDateTime dateTime){
        return LocalDateTime.of(LocalDate.from(dateTime.toLocalDate().with(TemporalAdjusters.firstDayOfMonth())), LocalTime.MIN);
    }

    /**
     * @Author lipeng
     * @Description 获取一个月最后一天
     * @Date 2023/10/24 14:57
     * @param dateTime
     * @return java.time.LocalDateTime
     **/
    public static LocalDateTime getLastDateTimeOfMonth(LocalDateTime dateTime){
        return LocalDateTime.of(LocalDate.from(dateTime.toLocalDate().with(TemporalAdjusters.lastDayOfMonth())), LocalTime.MAX);
    }


    /**
     * @Author lipeng
     * @Description 获取当前日期
     * @Date 2023/12/7 16:07
     * @param
     * @return java.lang.String
     **/
    public static String getDate() {
        return byString(LocalDateTime.now(), KPLocalDateTimeUtil.DATE_PATTERN);
    }


    /**
     * @Author lipeng
     * @Description 获取当前时间+日期
     * @Date 2023/12/7 16:07
     * @param
     * @return java.lang.String
     **/
    public static final String getTime() {
        return byString(LocalDateTime.now(), KPLocalDateTimeUtil.DATE_TIME_PATTERN);
    }




    /**
     * @Author lipeng
     * @Description 减去天数
     * @Date 2023/12/7 15:57
     * @param now 操作的日期
     * @param days 要减去的天数
     * @param format 格式化
     * @return java.lang.String
     **/
    public static String minus(LocalDateTime now, int days, String format) {
        LocalDateTime previous = now.plusDays(-days);
        DateTimeFormatter spl = DateTimeFormatter.ofPattern(format);
        return previous.format(spl);
    }
    public static LocalDateTime minus(LocalDateTime now, int days) {
        LocalDateTime previous = now.plusDays(-days);
        return previous;
    }



    /**
     * @Author lipeng
     * @Description 时间加或减去指定月份
     * @Date 2024/7/14 10:24
     * @param localDateTime 目标时间
     * @param months 月份
     * @return java.time.LocalDateTime
     **/
    public static LocalDateTime addMonths(LocalDateTime localDateTime, int months) {
        if ( months > 0 )
            return localDateTime.plusMonths(months);
        if ( months < 0 )
            return localDateTime.minusMonths(Math.abs(months));
        return localDateTime;
    }


    /**
     * @Author lipeng
     * @Description 将毫秒数转换为天、小时、分钟、秒和毫秒的字符串形式
     * @Date 2024/1/25 17:30
     * @param milliseconds 毫秒数
     * @return java.lang.String
     **/
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
        if (days != 0) body += KPStringUtil.format("{0}天", days).toString();
        if (hours != 0) body += KPStringUtil.format("{0}时", hours).toString();
        if (minutes != 0) body += KPStringUtil.format("{0}分", minutes).toString();
        if (seconds != 0) body += KPStringUtil.format("{0}秒", seconds).toString();
        if (milliseconds != 0) body += KPStringUtil.format(" {0}毫秒", milliseconds).toString();
        return body;
    }



    /**
     * 计算两个日期时间（包含日期和时间）相差的分钟数。
     *
     * @param startTimeStr 开始日期时间的字符串，格式为"yyyy-MM-dd HH:mm:ss"
     * @param endTimeStr 结束日期时间的字符串，格式为"yyyy-MM-dd HH:mm:ss"
     * @return 相差的分钟数
     * @throws IllegalArgumentException 如果日期时间格式不正确或日期时间无效
     */
    public static long getSecondsBetween(String startTimeStr, String endTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(KPLocalDateTimeUtil.DATE_TIME_PATTERN);
        LocalDateTime startDateTime = LocalDateTime.parse(startTimeStr, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endTimeStr, formatter);
        Duration duration = Duration.between(startDateTime, endDateTime);
        return duration.getSeconds();
    }


    /**
     * @Author lipeng
     * @Description 计算两个日期时间（包含日期和时间）相差的分钟数。
     * @Date 2024/5/7 14:52
     * @param startTime 开始日期时间
     * @param endTime 结束日期时间
     * @return 相差的分钟数
     * @throws IllegalArgumentException 如果日期时间格式不正确或日期时间无效
     **/
    public static long getSecondsBetween(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        return duration.getSeconds();
    }


    /**
     * @Author lipeng
     * @Description 获取从当前时间到当日24:00（次日0点）剩余的秒数。
     * @Date 2024/7/1 14:28
     * @param
     * @return long 剩余的秒数。如果当前时间已过24:00，则返回0。
     **/
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
     * @Author lipeng
     * @Description 根据给定的时间戳（秒）获取LocalDateTime表示。
     * @Date 2024/8/12 10:34
     * @param timestampInSeconds  timestampInSeconds 时间戳（秒）
     * @param zoneId 时区  "Asia/Shanghai"
     * @return java.time.LocalDateTime 给定时间戳对应的LocalDateTime对象。
     **/
    public static LocalDateTime fromTimeMilli(Long timestampInSeconds, ZoneId zoneId) {
        // 将秒数的时间戳转换为Instant对象
        Instant instant = Instant.ofEpochMilli(timestampInSeconds);
//        ZoneOffset.UTC
        // 使用UTC时区转换为LocalDateTime
        return instant.atZone(zoneId).toLocalDateTime();
    }

}
