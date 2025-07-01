package com.kunpeng.framework.utils.kptool;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/* *
 * @Author 李鹏
 * @Description //时间操作的工具类
 * @Date 2020/5/31 0:07
 * @Param
 * @return
 **/
public final class KPDateUtil {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String HOUR_PATTERN = "yyyy-MM-dd HH";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String MONTH_PATTERN = "yyyy-MM";
    public static final String YEAR_PATTERN = "yyyy";
    public static final String MINUTE_ONLY_PATTERN = "MM";
    public static final String HOUR_ONLY_PATTERN = "HH";
    public static final String TIMESTAMP_YMD = "yyyyMMdd";
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    private KPDateUtil(){}

    /**
     * 日期相加减天数
     * @param date 如果为Null，则为当前时间
     * @param days 加减天数
     * @param includeTime 是否包括时分秒,true表示包含
     * @return
     * @throws ParseException
     */
    public static Date dateAdd(Date date, int days, boolean includeTime) {
        if(date == null){
            date = new Date();
        }
        if(!includeTime){
            SimpleDateFormat sdf = new SimpleDateFormat(KPDateUtil.DATE_PATTERN);
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
     * 时间格式化成字符串
     * @param date Date
     * @param pattern StrUtils.DATE_TIME_PATTERN || StrUtils.DATE_PATTERN， 如果为空，则为yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static String dateFormat(Date date, String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static String dateFormat(String date, String pattern){
        SimpleDateFormat df = new SimpleDateFormat(pattern);//规格化
        try {
            Date sd = df.parse(date);
            return df.format(sd);
        } catch (ParseException e) {
            return null;
        }
    }



    /**
     * @Author lipeng
     * @Description  转换日期格式
     * @Date 2024/8/6 15:21
     * @param date 日期
     * @param inputPattern  输入格式
     * @param outputPattern 输出格式
     * @return java.lang.String
     **/
    public static String dateFormat(String date, String inputPattern, String outputPattern) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern); // 输入格式
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern); // 输出格式
            Date sd = inputFormat.parse(date);
            return outputFormat.format(sd);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 字符串解析成时间对象
     * @param dateTimeString String
     * @param pattern StrUtils.DATE_TIME_PATTERN || StrUtils.DATE_PATTERN，如果为空，则为yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static Date dateParse(String dateTimeString, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(dateTimeString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date dateFormatDate(Date date, String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(sdf.format(date));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将日期时间格式成只有日期的字符串（可以直接使用dateFormat，Pattern为Null进行格式化）
     * @param dateTime Date
     * @return
     * @throws ParseException
     */
    public static String dateTimeToDateString(Date dateTime) throws ParseException{
        String dateTimeString = KPDateUtil.dateFormat(dateTime, KPDateUtil.DATE_TIME_PATTERN);
        return dateTimeString.substring(0, 10);
    }

    /**
     * 当时、分、秒为00:00:00时，将日期时间格式成只有日期的字符串，
     * 当时、分、秒不为00:00:00时，直接返回
     * @param dateTime Date
     * @return
     * @throws ParseException
     */
    public static String dateTimeToDateStringIfTimeEndZero(Date dateTime) throws ParseException{
        String dateTimeString = KPDateUtil.dateFormat(dateTime, KPDateUtil.DATE_TIME_PATTERN);
        if(dateTimeString.endsWith("00:00:00")){
            return dateTimeString.substring(0, 10);
        }else{
            return dateTimeString;
        }
    }

    /**
     * 将日期时间格式成日期对象，和dateParse互用
     * @param dateTime Date
     * @return Date
     * @throws ParseException
     */
    public static Date dateTimeToDate(Date dateTime) throws ParseException{
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 时间加减小时
     * @param startDate 要处理的时间，Null则为当前时间 
     * @param hours 加减的小时 
     * @return Date
     */
    public static Date dateAddHours(Date startDate, int hours) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.HOUR, c.get(Calendar.HOUR) + hours);
        return c.getTime();
    }

    /**
     * 时间加减分钟
     * @param startDate 要处理的时间，Null则为当前时间 
     * @param minutes 加减的分钟
     * @return
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
     * 时间加减秒数
     * @param startDate 要处理的时间，Null则为当前时间
     * @return
     */
    public static Date dateAddSeconds(Date startDate, int seconds) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.SECOND, c.get(Calendar.SECOND) + seconds);
        return c.getTime();
    }

    /**
     * 时间加减天数 
     * @param startDate 要处理的时间，Null则为当前时间 
     * @param days 加减的天数 
     * @return Date
     */
    public static Date dateAddDays(Date startDate, int days) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.DATE, c.get(Calendar.DATE) + days);
        return c.getTime();
    }

    /**
     * 时间加减月数
     * @param startDate 要处理的时间，Null则为当前时间 
     * @param months 加减的月数 
     * @return Date
     */
    public static Date dateAddMonths(Date startDate, int months) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + months);
        return c.getTime();
    }

    /**
     * 时间加减年数
     * @param startDate 要处理的时间，Null则为当前时间 
     * @param years 加减的年数 
     * @return Date
     */
    public static Date dateAddYears(Date startDate, int years) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.YEAR, c.get(Calendar.YEAR) + years);
        return c.getTime();
    }

    /**
     * 时间比较（如果myDate>compareDate返回1，<返回-1，相等返回0） 
     * @param myDate 时间 
     * @param compareDate 要比较的时间
     *                    后面的大 -1
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
     * 获取两个时间中最小的一个时间
     * @param date
     * @param compareDate
     * @return
     */
    public static Date dateMin(Date date, Date compareDate) {
        if(date == null){
            return compareDate;
        }
        if(compareDate == null){
            return date;
        }
        if(1 == dateCompare(date, compareDate)){
            return compareDate;
        }else if(-1 == dateCompare(date, compareDate)){
            return date;
        }
        return date;
    }

    /**
     * 获取两个时间中最大的一个时间
     * @param date
     * @param compareDate
     * @return
     */
    public static Date dateMax(Date date, Date compareDate) {
        if(date == null){
            return compareDate;
        }
        if(compareDate == null){
            return date;
        }
        if(1 == dateCompare(date, compareDate)){
            return date;
        }else if(-1 == dateCompare(date, compareDate)){
            return compareDate;
        }
        return date;
    }

    /**
     * 获取两个日期（不含时分秒）相差的天数，不包含今天
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static int dateBetween(Date startDate, Date endDate){
        Date dateStart = dateParse(dateFormat(startDate, DATE_PATTERN), DATE_PATTERN);
        Date dateEnd = dateParse(dateFormat(endDate, DATE_PATTERN), DATE_PATTERN);
        return (int) ((dateEnd.getTime() - dateStart.getTime())/1000/60/60/24);
    }

    /**
     * 获取两个日期（不含时分秒）相差的天数，包含今天
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static int dateBetweenIncludeToday(Date startDate, Date endDate) {
        try {
            return dateBetween(startDate, endDate) + 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取日期时间的年份，如2017-02-13，返回2017
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取日期时间的月份，如2017年2月13日，返回2
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日期时间的第几天（即返回日期的dd），如2017-02-13，返回13
     * @param date
     * @return
     */
    public static int getDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DATE);
    }

    /**
     * 获取日期时间当月的总天数，如2017-02-13，返回28
     * @param date
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取日期时间当年的总天数，如2017-02-13，返回2017年的总天数
     * @param date
     * @return
     */
    public static int getDaysOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    /**
     * 根据时间获取当月最大的日期
     * <li>2017-02-13，返回2017-02-28</li>
     * <li>2016-02-13，返回2016-02-29</li>
     * <li>2016-01-11，返回2016-01-31</li>
     * @param date Date
     * @return
     * @throws Exception
     */
    public static Date maxDateOfMonth(Date date) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int value = cal.getActualMaximum(Calendar.DATE);
        return dateParse(dateFormat(date, MONTH_PATTERN) + "-" + value, null);
    }

    /**
     * 根据时间获取当月最小的日期，也就是返回当月的1号日期对象
     * @param date Date
     * @return
     * @throws Exception
     */
    public static Date minDateOfMonth(Date date) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int value = cal.getActualMinimum(Calendar.DATE);
        return dateParse(dateFormat(date, MONTH_PATTERN) + "-" + value, null);
    }


    /**
     * @Author lipeng
     * @Description 获取本周周一日期
     * @Date 2023/12/7 17:17
     * @param
     * @return java.lang.String
     **/
    public static String minDateOfWeek()  {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return KPDateUtil.dateFormat(cal.getTime(), KPDateUtil.DATE_PATTERN) ;
    }

    /**
     * @Author lipeng
     * @Description 获取本周最后日期
     * @Date 2023/12/7 17:23
     * @param
     * @return java.lang.String
     **/
    public static String maxDateOfWeek()  {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.add(Calendar.DATE, 6);
        return KPDateUtil.dateFormat(cal.getTime(), KPDateUtil.DATE_PATTERN) ;
    }

    /**
     * 时间戳转换成日期格式字符串 
     * @param seconds 精确到秒的字符串 
     * @param
     * @return
     */
    public static String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)));
    }
    /**
     * 日期格式字符串转换成时间戳 
     * @param //字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss 
     * @return
     */
    public static Long dateToTime(String date_str,String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date_str).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 取得当前时间戳（精确到秒） 
     * @return
     */
    public static String timeStamp(){
        long time = System.currentTimeMillis();
        String t = String.valueOf(time/1000);
        return t;
    }


    /**
     * @Author lipeng
     * @Description 获取当天凌晨
     * @Date 2020/9/24 16:12
     * @Param []
     * @return java.util.Date
     **/
    public static final Date thatWeeHours(){
        String str = new SimpleDateFormat(KPDateUtil.DATE_PATTERN).format(new Date()).concat(" 00:00:00");
        SimpleDateFormat sdf = new SimpleDateFormat(KPDateUtil.DATE_TIME_PATTERN);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {}
        return null;
    }


    /**
     *  获取指定日期当年的第一天
     * @return Date 日期 如果不传  取当前日期
     **/
    public static Date getYearStart(Date date){
        date = date == null? new Date() : date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(KPDateUtil.DATE_TIME_PATTERN);
        String startDate = new SimpleDateFormat(KPDateUtil.YEAR_PATTERN).format(date) + "-01-01 00:00:00";
        try {
            return simpleDateFormat.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定日期当年的最后一天
     *
     * @return Date
     **/
    public static Date getYearEnd(Date date){
        date = date == null? new Date() : date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(KPDateUtil.DATE_TIME_PATTERN);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.getActualMaximum(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.YEAR, Integer.valueOf(KPDateUtil.dateFormat(date, KPDateUtil.YEAR_PATTERN)));
        Date currYearLast = calendar.getTime();
        String endDate = new SimpleDateFormat(KPDateUtil.DATE_PATTERN).format(currYearLast) + " 23:59:59";
        try {
            return simpleDateFormat.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @Author lipeng
     * @Description LocalDateTime 转 date
     * @Date 2023/10/7 14:38
     * @param localDateTime
     * @return java.util.Date
     **/
    public static Date LocalDateTimeByDate(LocalDateTime localDateTime){
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * @Author lipeng
     * @Description LocalDate 转 date
     * @Date 2023/10/7 14:38
     * @param localDate
     * @return java.util.Date
     **/
    public static Date LocalDateByDate(LocalDate localDate){
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * @Author lipeng
     * @Description date转LocalDateTime
     * @Date 2023/10/7 14:38
     * @param date
     * @return java.time.LocalDateTime
     **/
    public static LocalDateTime dateByLocalDateTime(Date date){
        return LocalDateTime.ofInstant(date.toInstant(),ZoneId.systemDefault());
    }

    /**
     * 将传入的秒数转成分秒格式
     *
     * @param seconds 秒数
     * @return 获取分秒格式
     */
    public static String parseMinutes(Integer seconds) {
        Integer minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return minutes + "分" + remainingSeconds + "秒";
    }


    @Deprecated
    public static String preRandomDay(String date) {
        Date regDate = KPDateUtil.dateParse(date, KPDateUtil.DATE_PATTERN);
        Date now = new Date();

        long nd = 1000 * 24 * 60 * 60;

        long diff = now.getTime() - regDate.getTime();
        long day = diff / nd;

        if (day > 15) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.DAY_OF_MONTH, 0 - new Random().nextInt(15));

            SimpleDateFormat sdf = new SimpleDateFormat(KPDateUtil.DATE_PATTERN);
            String dateStr = sdf.format(calendar.getTime());

            return dateStr;
        } else {
            return date;
        }
    }

    @Deprecated
    public static Date parseDate(String str) {
        if (str == null) return null;
        if (str.length()>12) return KPDateUtil.dateParse(str, KPDateUtil.DATE_TIME_PATTERN);
        return KPDateUtil.dateParse(str, KPDateUtil.DATE_PATTERN);
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }
}