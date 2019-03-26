package com.xingling.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>Title:	  DateUtil. </p>
 * <p>Description 日期操作工具类 </p>
 * <p>Company:    http://www.xinglinglove.com </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>
 * @CreateDate 2017 /4/19 12:03
 */
public class DateUtil {

    private static final Log log = LogFactory.getLog(DateUtil.class);

    /**
     * The constant DAY_LAST_TIME.
     */
    public static final String DAY_LAST_TIME = " 23:59:59";

    /**
     * The constant DAY_FIRST_TIME.
     */
    public static final String DAY_FIRST_TIME = " 00:00:00";

    /**
     * The constant DATE_PATTERN_MONTH.
     */
    public static final String DATE_PATTERN_MONTH = "yyyy-MM";

    /**
     * The constant YYYY_MM_DD_MM_HH_SS.
     */
    public static final String YYYY_MM_DD_MM_HH_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * The constant YYYY_MM_DD.
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * The constant YYYYMMDDMMHHSSSSS.
     */
    public static final String YYYYMMDDMMHHSSSSS = "yyyyMMddHHmmssSSS";

    /**
     * The constant YYYYMMDDHHMMSS.
     */
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**
     * The constant YYYYMMDD.
     */
    public final static String YYYYMMDD = "yyyyMMdd";

    /**
     * The constant SECONDS_IN_DAY.
     */
    public static final int SECONDS_IN_DAY = 60 * 60 * 24;

    /**
     * The constant MILLIS_IN_DAY.
     */
    public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;

    /**
     * 返回不大于date2的日期 如果 date1 >= date2 返回date2 如果 date1 < date2 返回date1
     *
     * @param date1 the date 1
     * @param date2 the date 2
     * @return the date
     */
    public static Date ceil(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return null;
        }
        if (date1 == null) {
            return date2;
        }
        if (date2 == null) {
            return date1;
        }
        if (date1.after(date2)) {
            return date2;
        } else {
            return date1;
        }
    }

    /***
     * 将日期转化为字符串。 字符串格式("yyyy-MM-dd HH:mm:ss")。
     * @param date the date
     * @return the string
     */
    public static String dateTime2String(Date date) {
        return dateToString(date, YYYYMMDDMMHHSSSSS);
    }

    /***
     * 将日期转化为字符串。 字符串格式("yyyy-MM-dd")，小时、分、秒被忽略。
     * @param date the date
     * @return the string
     */
    public static String dateToString(Date date) {
        return dateToString(date, YYYY_MM_DD);
    }

    /***
     * 将日期转化为字符串
     * @param date the date
     * @param pattern the pattern
     * @return the string
     */
    public static String dateToString(Date date, String pattern) {
        String str = "";
        try {
            SimpleDateFormat formater = new SimpleDateFormat(pattern);
            str = formater.format(date);
        } catch (Throwable e) {
            log.error(e);
        }
        return str;
    }

    /**
     * 返回不小于date2的日期 如果 date1 >= date2 返回date1 如果 date1 < date2 返回date2
     *
     * @param date1 the date 1
     * @param date2 the date 2
     * @return the date
     */
    public static Date floor(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return null;
        }
        if (date1 == null) {
            return date2;
        }
        if (date2 == null) {
            return date1;
        }
        if (date1.after(date2)) {
            return date1;
        } else {
            return date2;
        }
    }

    /**
     * 获取2个时间相隔几天
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the between day number
     */
    public static int getBetweenDayNumber(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return -1;
        }

        if (startDate.after(endDate)) {
            Date tmp = endDate;
            endDate = startDate;
            startDate = tmp;
        }

        long dayNumber = -1L;
        long DAY = 24L * 60L * 60L * 1000L;
        try {
            // "2010-08-01 00:00:00 --- 2010-08-03 23:59:59"算三天
            dayNumber = (endDate.getTime() + 1000 - startDate.getTime()) / DAY;
            // System.out.println(endDate.getTime()+" "+startDate.getTime());
        } catch (Exception e) {
            log.error(e);
        }
        return (int) dayNumber;
    }

    /**
     * 获取2个时间相隔几天，非绝对值
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the between day number not absolute
     */
    public static int getBetweenDayNumberNotAbsolute(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return -1;
        }

        long dayNumber = -1L;
        long DAY = 24L * 60L * 60L * 1000L;
        try {
            // System.out.println((endDate.getTime() + 1000 -
            // startDate.getTime()));
            dayNumber = (endDate.getTime() - startDate.getTime()) / DAY;
            // System.out.println(endDate.getTime()+" "+startDate.getTime());
        } catch (Exception e) {
            log.error(e);
        }
        return (int) dayNumber;
    }

    /**
     * 获取2个时间相隔几小时
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the between hour number
     */
    public static int getBetweenHourNumber(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return -1;
        }

        if (startDate.after(endDate)) {
            Date tmp = endDate;
            endDate = startDate;
            startDate = tmp;
        }

        long timeNumber = -1l;
        long TIME = 60L * 60L * 1000L;
        try {
            timeNumber = (endDate.getTime() - startDate.getTime()) / TIME;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int) timeNumber;
    }

    /**
     * 获取2个时间相隔几小时，非绝对值
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the between hour number not absolute
     */
    public static int getBetweenHourNumberNotAbsolute(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return -1;
        }

        // if (startDate.after(endDate)) {
        // Date tmp = endDate;
        // endDate = startDate;
        // startDate = tmp;
        // }

        long timeNumber = -1l;
        long TIME = 60L * 60L * 1000L;
        try {
            timeNumber = (endDate.getTime() - startDate.getTime()) / TIME;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int) timeNumber;
    }

    /**
     * 获取2个时间相隔几分钟
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the between minute number
     */
    public static int getBetweenMinuteNumber(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return -1;
        }

        if (startDate.after(endDate)) {
            Date tmp = endDate;
            endDate = startDate;
            startDate = tmp;
        }

        long timeNumber = -1l;
        long TIME = 60L * 1000L;
        try {
            timeNumber = (endDate.getTime() - startDate.getTime()) / TIME;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int) timeNumber;
    }

    /**
     * 获取2个时间相隔几分钟,非绝对值
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the between minute number not absolute
     */
    public static int getBetweenMinuteNumberNotAbsolute(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return -1;
        }

        // if (startDate.after(endDate)) {
        // Date tmp = endDate;
        // endDate = startDate;
        // startDate = tmp;
        // }

        long timeNumber = -1l;
        long TIME = 60L * 1000L;
        try {
            timeNumber = (endDate.getTime() - startDate.getTime()) / TIME;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int) timeNumber;
    }

    /**
     * 获取2个时间相隔几月
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the between month number
     */
    public static int getBetweenMonthNumber(Date startDate, Date endDate) {
        int result = 0;
        try {
            if (startDate == null || endDate == null) {
                return -1;
            }

            // swap start and end date
            if (startDate.after(endDate)) {
                Date tmp = endDate;
                endDate = startDate;
                startDate = tmp;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);

            int monthS = calendar.get(Calendar.MONTH);
            int yearS = calendar.get(Calendar.YEAR);

            calendar.setTime(endDate);
            int monthE = calendar.get(Calendar.MONTH);
            int yearE = calendar.get(Calendar.YEAR);

            if (yearE - yearS == 0) {
                result = monthE - monthS;
            } else {
                result = (yearE - yearS - 1) * 12 + (12 - monthS) + monthE;
            }

        } catch (Exception e) {
            log.error(e);
        }
        return result;
    }

    /**
     * 获取2个时间相隔几月，非绝对值
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the between month number not absolute
     */
    public static int getBetweenMonthNumberNotAbsolute(Date startDate, Date endDate) {
        int result = 0;
        try {
            if (startDate == null || endDate == null) {
                return -1;
            }

            // swap start and end date
            // if (startDate.after(endDate)) {
            // Date tmp = endDate;
            // endDate = startDate;
            // startDate = tmp;
            // }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);

            int monthS = calendar.get(Calendar.MONTH);
            int yearS = calendar.get(Calendar.YEAR);

            calendar.setTime(endDate);
            int monthE = calendar.get(Calendar.MONTH);
            int yearE = calendar.get(Calendar.YEAR);

            if (yearE - yearS == 0) {
                result = monthE - monthS;
            } else {
                result = (yearE - yearS - 1) * 12 + (12 - monthS) + monthE;
            }

        } catch (Exception e) {
            log.error(e);
        }
        return result;
    }

    /**
     * 获取2个时间相隔几秒
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the between second number
     */
    public static int getBetweenSecondNumber(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return -1;
        }

        if (startDate.after(endDate)) {
            Date tmp = endDate;
            endDate = startDate;
            startDate = tmp;
        }

        long timeNumber = -1L;
        long TIME = 1000L;
        try {
            timeNumber = (endDate.getTime() - startDate.getTime()) / TIME;

        } catch (Exception e) {
            log.error(e);
        }
        return (int) timeNumber;
    }

    /**
     * 获取2个时间相隔几秒,非绝对值
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the between second number not absolute
     */
    public static int getBetweenSecondNumberNotAbsolute(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return -1;
            //
            // if (startDate.after(endDate)) {
            // Date tmp = endDate;
            // endDate = startDate;
            // startDate = tmp;
            // }
        }

        long timeNumber = -1L;
        long TIME = 1000L;
        try {
            timeNumber = (endDate.getTime() - startDate.getTime()) / TIME;

        } catch (Exception e) {
            log.error(e);
        }
        return (int) timeNumber;
    }

    /**
     * 获取2个时间相隔几年
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the between year number
     */
    public static int getBetweenYearNumber(Date startDate, Date endDate) {
        int result = 0;
        try {
            if (startDate == null || endDate == null) {
                return -1;
            }

            // swap start and end date
            if (startDate.after(endDate)) {
                Date tmp = endDate;
                endDate = startDate;
                startDate = tmp;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            int yearS = calendar.get(Calendar.YEAR);

            calendar.setTime(endDate);
            int yearE = calendar.get(Calendar.YEAR);

            result = yearE - yearS;

        } catch (Exception e) {
            log.error(e);
        }
        return result;
    }

    /**
     * 获取2个时间相隔几年，非绝对值
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the between year number not absolute
     */
    public static int getBetweenYearNumberNotAbsolute(Date startDate, Date endDate) {
        int result = 0;
        try {
            if (startDate == null || endDate == null) {
                return -1;
            }

            // swap start and end date
            // if (startDate.after(endDate)) {
            // Date tmp = endDate;
            // endDate = startDate;
            // startDate = tmp;
            // }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            int yearS = calendar.get(Calendar.YEAR);

            calendar.setTime(endDate);
            int yearE = calendar.get(Calendar.YEAR);

            result = yearE - yearS;

        } catch (Exception e) {
            log.error(e);
        }
        return result;
    }

    /**
     * 根据字符串时间,返回Calendar
     *
     * @param datetimeStr the datetime str
     * @return the calendar
     */
    public static Calendar getCalendar(String datetimeStr) {
        Calendar cal = Calendar.getInstance();
        if (StringUtils.isNotBlank(datetimeStr)) {
            Date date = DateUtil.stringToDate(datetimeStr, YYYYMMDDMMHHSSSSS);
            cal.setTime(date);
        }
        return cal;
    }

    /**
     * 本季度
     *
     * @return the current quarter
     */
    public static List<Date> getCurrentQuarter() {
        List<Date> dateList = new ArrayList<Date>();
        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);// 一月为0

        dateList.add(1, calendar.getTime());// 结束时间设置为当前时间

        if (month >= 0 && month <= 2) {// 第一季度
            calendar.set(Calendar.MONTH, 0);
        } else if (month >= 3 && month <= 5) {// 第二季度
            calendar.set(Calendar.MONTH, 3);
        } else if (month >= 6 && month <= 8) {// 第三季度
            calendar.set(Calendar.MONTH, 6);
        } else {// 第四季度
            calendar.set(Calendar.MONTH, 9);
        }

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        dateList.add(0, calendar.getTime());

        return dateList;
    }

    /**
     * 得到当前的日
     *
     * @return current stat day
     */
    public static int getCurrentStatDay() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    /**
     * Gets current stat month.
     *
     * @return the current stat month
     */
// 获取当前月份，cal.get(Calendar.MONTH)是从零开始。
    public static int getCurrentStatMonth() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        return month;
    }

    /**
     * 得到当前的年份
     *
     * @return current stat year
     */
    public static int getCurrentStatYear() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    /**
     * 获取默认的DateFormat
     *
     * @return the date format of default
     */
    public static DateFormat getDateFormatOfDefault() {
        return new SimpleDateFormat(YYYYMMDDMMHHSSSSS);
    }

    /**
     * 得到给定时间的日
     *
     * @param date the date
     * @return day
     */
    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    /**
     * startStr 或者 startStr-endStr
     *
     * @param startStr the start str
     * @param endStr   the end str
     * @return the different str
     */
    public static String getDifferentStr(String startStr, String endStr) {
        String dateRangeStr = "";
        if (startStr.equals(endStr)) {
            dateRangeStr = startStr;
        } else {
            dateRangeStr = startStr + "-" + endStr;
        }
        return dateRangeStr;
    }

    /**
     * 获取给定日期的上个月的最早
     *
     * @param startDate the start date
     * @return first month first day
     */
    public static Date getFirstMonthFirstDay(Date startDate) {

        return getMinDateOfMonth(getFristMonthDay(startDate));
    }

    /**
     * 获取给定日期的上一个月的日期的最早时间
     *
     * @param startDate the start date
     * @return frist month day
     */
    public static Date getFristMonthDay(Date startDate) {
        // 是不是
        // int month = startDate.getMonth();
        Date monthEndDate = getMaxDateOfMonth(startDate);
        Date nextMonth = DateUtils.addMonths(startDate, -1);
        nextMonth = stringToDate(dateToString(nextMonth, YYYY_MM_DD) + DAY_FIRST_TIME,
                YYYYMMDDMMHHSSSSS);
        if (isTheSameDay(startDate, monthEndDate)) {
            nextMonth = getMaxDateOfMonth(nextMonth);
        }
        return nextMonth;
    }

    /**
     * 功能：获取昨天最大时间。 输入: 2010-01-31 00:00:00 返回：2010-01-30 23:59:59
     *
     * @param startDate the start date
     * @return the last max day
     */
    public static Date getLastMaxDay(Date startDate) {
        if (startDate == null) {
            return null;
        }
        startDate = DateUtils.addDays(startDate, -1);
        return DateUtil.getMaxDateOfDay(startDate);
    }

    /**
     * 获取给定日期的上一个月的最晚时间
     *
     * @param startDate the start date
     * @return last month day
     */
    public static Date getLastMonthDay(Date startDate) {
        // 是不是
        // int month = startDate.getMonth();
        Date monthEndDate = getMaxDateOfMonth(startDate);
        Date nextMonth = DateUtils.addMonths(startDate, -1);
        nextMonth = stringToDate(dateToString(nextMonth, YYYY_MM_DD) + DAY_LAST_TIME,
                YYYYMMDDMMHHSSSSS);
        if (isTheSameDay(startDate, monthEndDate)) {
            nextMonth = getMaxDateOfMonth(nextMonth);
        }
        return nextMonth;
    }

    /**
     * 获取给定日期的上个月的日期的最后一天
     *
     * @param startDate the start date
     * @return last month last day
     */
    public static Date getLastMonthLastDay(Date startDate) {

        return getMaxDateOfMonth(getLastMonthDay(startDate));
    }

    /**
     * 上季度
     *
     * @return the last quarter
     */
    public static List<Date> getLastQuarter() {
        List<Date> dateList = new ArrayList<Date>();
        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);// 一月为0

        // 如果是第一季度则返回去年的第四季度
        if (month >= 0 && month <= 2) {// 当前第一季度
            calendar.add(Calendar.YEAR, -1);// 退到去年
            calendar.set(Calendar.MONTH, 9);// 去年十月
        } else if (month >= 3 && month <= 5) {// 当前第二季度
            calendar.set(Calendar.MONTH, 0);
        } else if (month >= 6 && month <= 8) {// 当前第三季度
            calendar.set(Calendar.MONTH, 3);
        } else {// 当前第四季度
            calendar.set(Calendar.MONTH, 6);
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        dateList.add(0, calendar.getTime());

        calendar.add(Calendar.MONTH, 3);// 加3个月，到下个季度的第一天
        calendar.add(Calendar.DAY_OF_MONTH, -1);// 退一天，得到上季度的最后一天
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        dateList.add(1, calendar.getTime());

        return dateList;
    }

    /**
     * 取得一个date对象对应的日期的23点59分59秒时刻的Date对象。
     *
     * @param date the date
     * @return the max date of day
     */
    public static Date getMaxDateOfDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

        return calendar.getTime();
    }

    /**
     * 取得一个date对象对应的小时的59分59秒时刻的Date对象。
     *
     * @param date the date
     * @return the max date of hour
     */
    public static Date getMaxDateOfHour(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

        return calendar.getTime();
    }

    /**
     * 取得一月中的最后一天
     *
     * @param date the date
     * @return the max date of month
     */
    public static Date getMaxDateOfMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

        return calendar.getTime();
    }

    /**
     * 取得一周中的最后一天
     *
     * @param date   the date
     * @param locale the locale
     * @return the max date of week
     */
    public static Date getMaxDateOfWeek(Date date, Locale locale) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);

        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

        if (locale == null) {
            locale = Locale.CHINESE;
        }
        Date tmpDate = calendar.getTime();
        if (Locale.CHINESE.getLanguage().equals(locale.getLanguage())) {
            if (day_of_week == 1) {// 星期天
                tmpDate = DateUtils.addDays(tmpDate, -6);
            } else {
                tmpDate = DateUtils.addDays(tmpDate, 1);
            }
        }

        return tmpDate;
    }

    /**
     * 取得一年中的最后一天
     *
     * @param date the date
     * @return the max date of year
     */
    public static Date getMaxDateOfYear(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

        return calendar.getTime();
    }

    /**
     * Gets max day of last month.
     *
     * @return the max day of last month
     */
    public static int getMaxDayOfLastMonth() {
        Date now = new Date();
        Date lastMonth = DateUtils.addMonths(now, -1);
        lastMonth = getMaxDateOfMonth(lastMonth);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lastMonth);
        int maxDay = calendar.get(Calendar.DAY_OF_MONTH);
        return maxDay;
    }

    /**
     * 取得一个date对象对应的日期的0点0分0秒时刻的Date对象。
     *
     * @param date the date
     * @return the min date of day
     */
    public static Date getMinDateOfDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getMinDateOfHour(date));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        return calendar.getTime();
    }

    /**
     * 取得一个date对象对应的日期的0分0秒时刻的Date对象。
     *
     * @param date the date
     * @return the min date of hour
     */
    public static Date getMinDateOfHour(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        return calendar.getTime();
    }

    /**
     * 取得一月中的最早一天。
     *
     * @param date the date
     * @return the min date of month
     */
    public static Date getMinDateOfMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));

        return calendar.getTime();
    }

    /**
     * 取得一周中的最早一天。
     *
     * @param date   the date
     * @param locale the locale
     * @return the min date of week
     */
    public static Date getMinDateOfWeek(Date date, Locale locale) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);

        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));

        if (locale == null) {
            locale = Locale.CHINESE;
        }
        Date tmpDate = calendar.getTime();
        if (Locale.CHINESE.getLanguage().equals(locale.getLanguage())) {
            if (day_of_week == 1) {// 星期天
                tmpDate = DateUtils.addDays(tmpDate, -6);
            } else {
                tmpDate = DateUtils.addDays(tmpDate, 1);
            }
        }

        return tmpDate;
    }

    /**
     * 取得一年中的最早一天。
     *
     * @param date the date
     * @return the min date of year
     */
    public static Date getMinDateOfYear(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));

        return calendar.getTime();
    }

    /**
     * Gets month.
     *
     * @param date the date
     * @return the month
     */
// 获取给定时间月份，cal.get(Calendar.MONTH)是从零开始。
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        return month;
    }

    /**
     * 得到下一个月的月份
     *
     * @return month of last month
     */
    public static int getMonthOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        int lastMonth = calendar.get(Calendar.MONTH) + 1;
        return lastMonth;
    }

    /**
     * 给定一个日期和天数，得到这个日期+天数的日期
     *
     * @param date 指定日期
     * @param num  the num
     * @return next day
     * @author tangzhi, 2012 -11-28
     */
    public static String getNextDay(String date, int num) {
        Date d = stringToDate(date, YYYY_MM_DD);
        Calendar ca = Calendar.getInstance();
        ca.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        ca.setTime(d);

        int day = ca.get(Calendar.DATE);
        day = day + num;
        ca.set(Calendar.DATE, day);
        return getFormatDateTime(ca.getTime(), YYYY_MM_DD);

    }

    /**
     * 获取给定日期的下一个月的日期的最晚时间
     *
     * @param startDate the start date
     * @return next month day
     */
    public static Date getNextMonthDay(Date startDate) {
        // 是不是
        // int month = startDate.getMonth();
        Date monthEndDate = getMaxDateOfMonth(startDate);
        Date nextMonth = DateUtils.addMonths(startDate, 1);
        nextMonth = stringToDate(dateToString(nextMonth, YYYY_MM_DD) + DAY_LAST_TIME,
                YYYYMMDDMMHHSSSSS);
        if (isTheSameDay(startDate, monthEndDate)) {
            nextMonth = getMaxDateOfMonth(nextMonth);
        }
        return nextMonth;
    }

    /**
     * 得到给定时间的年份
     *
     * @param date the date
     * @return year
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    /**
     * 得到当年的最大月份
     *
     * @return year of last month
     */
    public static int getYearOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        int yearOfLastMonth = calendar.get(Calendar.YEAR);
        return yearOfLastMonth;
    }

    /**
     * 判断输入日期是否是一天中的最大时刻
     *
     * @param date1 the date 1
     * @return the boolean
     */
    public static boolean isMaxDayOfDay(Date date1) {
        if (date1 == null) {
            return false;
        }
        Date date2 = getMaxDateOfDay(date1);
        int secondNum = getBetweenSecondNumber(date1, date2);
        return secondNum == 0;
    }

    /**
     * 判断输入日期是否是一天中的最大时刻
     *
     * @param date1     the date 1
     * @param precision the precision
     * @return the boolean
     */
    public static boolean isMaxDayOfDay(Date date1, String precision) {
        if (date1 == null) {
            return false;
        }
        Date date2 = getMaxDateOfDay(date1);
        int diffNum = 0;
        if ("HH".equals(precision)) {
            diffNum = getBetweenHourNumber(date1, date2);
        } else if ("mm".equals(precision)) {
            diffNum = getBetweenMinuteNumber(date1, date2);
        } else {
            diffNum = getBetweenSecondNumber(date1, date2);
        }
        return diffNum == 0;
    }

    /**
     * 判断输入日期是否是一月中的最大时刻
     *
     * @param date1 the date 1
     * @return the boolean
     */
    public static boolean isMaxDayOfMonth(Date date1) {
        if (date1 == null) {
            return false;
        }
        Date date2 = getMaxDateOfMonth(date1);
        int secondNum = getBetweenSecondNumber(date1, date2);
        return secondNum == 0;
    }

    /**
     * 判断输入日期是否是一天中的最小时刻
     *
     * @param date1 the date 1
     * @return the boolean
     */
    public static boolean isMinDayOfDay(Date date1) {
        if (date1 == null) {
            return false;
        }
        Date date2 = getMinDateOfDay(date1);
        int secondNum = getBetweenSecondNumber(date1, date2);
        return secondNum == 0;
    }

    /**
     * 判断输入日期是否是一天中的最小时刻
     *
     * @param date1     the date 1
     * @param precision the precision
     * @return the boolean
     */
    public static boolean isMinDayOfDay(Date date1, String precision) {
        if (date1 == null) {
            return false;
        }
        Date date2 = getMinDateOfDay(date1);
        int diffNum = 0;
        if ("HH".equals(precision)) {
            diffNum = getBetweenHourNumber(date1, date2);
        } else if ("mm".equals(precision)) {
            diffNum = getBetweenMinuteNumber(date1, date2);
        } else {
            diffNum = getBetweenSecondNumber(date1, date2);
        }
        return diffNum == 0;
    }

    /**
     * 判断输入日期是否是一月中的最小时刻
     *
     * @param date1 the date 1
     * @return the boolean
     */
    public static boolean isMinDayOfMonth(Date date1) {
        if (date1 == null) {
            return false;
        }
        Date date2 = getMinDateOfMonth(date1);
        int secondNum = getBetweenSecondNumber(date1, date2);
        return secondNum == 0;
    }

    /**
     * 输入日期是否为同一天.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the boolean
     */
    public static boolean isTheSameDay(Date startDate, Date endDate) {
        String startDateStr = dateToString(startDate);
        String endDateStr = dateToString(endDate);
        return startDateStr.equals(endDateStr);
    }

    /**
     * 返回2个日期中的大者
     *
     * @param date1 the date 1
     * @param date2 the date 2
     * @return the date
     */
    public static Date max(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return null;
        }
        if (date1 == null) {
            return date2;
        }
        if (date2 == null) {
            return date1;
        }
        if (date1.after(date2)) {
            return date1;
        } else {
            return date2;
        }
    }

    /**
     * 返回2个日期中的小者
     *
     * @param date1 the date 1
     * @param date2 the date 2
     * @return the date
     */
    public static Date min(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return null;
        }
        if (date1 == null) {
            return date2;
        }
        if (date2 == null) {
            return date1;
        }
        if (date1.after(date2)) {
            return date2;
        } else {
            return date1;
        }
    }

    /**
     * 时间校验: 不能晚于上一个月(如果晚于上一个月，则替换为上一个月)
     *
     * @param myDate the my date
     * @return the date
     */
    public static Date notAfterLastMonth(Date myDate) {
        Date today = new Date();
        Date lastMonth = DateUtils.addMonths(today, -1);
        lastMonth = DateUtil.getMaxDateOfMonth(lastMonth);
        // 3. 结束时间不能大于上一个月.
        if (myDate.after(lastMonth)) {
            log.warn("myDate.after(lastMonth), set myDate = lastMonth:" + lastMonth);
            myDate = lastMonth;
        }
        return myDate;
    }

    /**
     * 时间校验: 不能晚于上一年(如果晚于上一年，则替换为上一年)
     *
     * @param myDate the my date
     * @return the date
     */
    public static Date notAfterLastYear(Date myDate) {
        Date today = new Date();
        Date lastYear = DateUtils.addYears(today, -1);
        lastYear = DateUtil.getMaxDateOfYear(lastYear);
        // 3. 结束时间不能大于上一年.
        if (myDate.after(lastYear)) {
            log.warn("myDate.after(lastYear), set myDate = lastYear:" + lastYear);
            myDate = lastYear;
        }
        return myDate;
    }

    /**
     * 时间校验: 不能晚于当前时间(如果晚于当前时间，则替换为当前时间)
     *
     * @param myDate the my date
     * @return the date
     */
    public static Date notAfterNow(Date myDate) {
        Date today = new Date();
        if (myDate.after(today)) {
            log.warn("myDate.after(today), set myDate = today:" + today);
            myDate = today;
        }
        return myDate;
    }

    /**
     * 时间校验: 不能晚于昨天(如果晚于昨天，则替换为昨天)
     *
     * @param myDate the my date
     * @return the date
     */
    public static Date notAfterYesterday(Date myDate) {
        Date today = new Date();
        Date yesterday = DateUtils.addDays(today, -1);
        ;
        // 3. 结束时间不能大于昨天.
        if (myDate.after(yesterday)) {
            log.warn("myDate.after(yesterday), set myDate = yesterday:" + yesterday);
            myDate = yesterday;
        }
        return myDate;
    }

    /**
     * 时间校验: myDate不能早于basicDate(如果早于basicDate，则替换为basicDate)
     *
     * @param myDate   the my date
     * @param basicStr the basic str
     * @return the date
     * @throws Exception the exception
     */
    public static Date notBefore(Date myDate, String basicStr) throws Exception {
        Date basicDate = DateUtil.stringToDateTime(basicStr);
        // Date today = new Date();
        // Date yesterday = DateUtils.addDays(today, -1);;
        // 3. 结束时间不能大于昨天.
        if (myDate.before(basicDate)) {
            log.warn("myDate.before(basicDate), set myDate = basicDate:" + basicDate);
            myDate = basicDate;
        }
        return myDate;
    }

    /**
     * 按天拆分时间
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the list
     */
    public static List<Date> splitDateByDay(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return null;
        }

        List<Date> dateList = new ArrayList<Date>();
        dateList.add(startDate);

        int num = getBetweenDayNumber(startDate, endDate);
        for (int i = 1; i <= num; i++) {
            dateList.add(DateUtils.addDays(startDate, i));
        }

        return dateList;
    }

    /**
     * 按月拆分时间
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the list
     */
    public static List<Date> splitDateByMonth(Date startDate, Date endDate) {
        List<Date> dateList = new ArrayList<Date>();

        if (startDate == null || endDate == null) {
            return dateList;
        }

        dateList.add(startDate);
        int num = getBetweenMonthNumber(startDate, endDate);
        for (int i = 1; i <= num; i++) {
            dateList.add(DateUtils.addMonths(startDate, i));
        }

        return dateList;
    }

    /**
     * 按年拆分时间
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the list
     */
    public static List<Date> splitDateByYear(Date startDate, Date endDate) {
        List<Date> dateList = new ArrayList<Date>();

        if (startDate == null || endDate == null) {
            return dateList;
        }

        dateList.add(startDate);
        int num = getBetweenYearNumber(startDate, endDate);
        for (int i = 1; i <= num; i++) {
            dateList.add(DateUtils.addYears(startDate, i));
        }

        return dateList;
    }

    /**
     * 将字符串转化为日期(从一种格式到另一种格式)。
     *
     * @param str      the str
     * @param pattern1 the pattern 1
     * @param pattern2 the pattern 2
     * @return the string
     */
    public static String StringPatternToPattern(String str, String pattern1, String pattern2) {
        Date dateTime = null;
        String productStr = "";
        try {
            if (!(str == null || str.equals(""))) {
                SimpleDateFormat formater = new SimpleDateFormat(pattern1);
                dateTime = formater.parse(str);

                SimpleDateFormat formater1 = new SimpleDateFormat(pattern2);
                productStr = formater1.format(dateTime);
            }
        } catch (Exception ex) {
            log.error(ex);
        }
        return productStr;
    }

    /**
     * 将字符串转化为日期。 字符串格式("YYYY-MM-DD")。
     * 例如："2012-07-01"或者"2012-7-1"或者"2012-7-01"或者"2012-07-1"是等价的。
     *
     * @param str     the str
     * @param pattern the pattern
     * @return the date
     */
    public static Date stringToDate(String str, String pattern) {
        Date dateTime = null;
        try {
            SimpleDateFormat formater = new SimpleDateFormat(pattern);
            dateTime = formater.parse(str);
        } catch (Exception e) {
            log.error(e);
        }
        return dateTime;
    }

    /**
     * 日期时间带时分秒的Timestamp表示
     *
     * @param str the str
     * @return the timestamp
     */
    public static Timestamp stringToDateHMS(String str) {
        Timestamp time = null;
        try {
            time = Timestamp.valueOf(str);
        } catch (Exception ex) {
            log.error(ex);
        }
        return time;

    }

    /**
     * 将字符串转化为日期
     *
     * @param str the str
     * @return the date
     * @throws Exception the exception
     */
    public static Date stringToDateTime(String str) throws Exception {
        return getDateFormatOfDefault().parse(str);
    }

    /**
     * 将字符串转化为日期
     *
     * @param str the str
     * @return the date
     * @throws ParseException the parse exception
     */
    public static Date stringToMediumDateTime(String str) throws ParseException {
        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        return format.parse(str);
    }

    /**
     * 时间校验: 开始时间不能大于当前时间.
     *
     * @param startDate the start date
     * @return the date
     */
    public static Date validateStartDate(Date startDate) {
        Date today = new Date();
        // 开始时间不能大于当前时间.
        if (startDate.compareTo(today) == 1) {
            log.warn("startDate.compareTo(today)==1, set startDate = today:" + today);
            startDate = today;
        }
        return startDate;
    }

    /**
     * 将传入的年月日转化为Date类型
     *
     * @param year  the year
     * @param month the month
     * @param day   the day
     * @return the date
     */
    public static Date YmdToDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    /**
     * 根据指定格式获取日期数据
     *
     * @param date    ：指定日期
     * @param pattern ：日期格式
     * @return
     * @author tangzhi, 2012-11-28
     */
    private static String getFormatDateTime(Date date, String pattern) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return format.format(date);
    }

    /**
     * Str to date date.
     *
     * @param str       the str
     * @param formatStr the format str
     * @return the date
     * @throws Exception the exception
     */
    public static Date strToDate(String str, String formatStr) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.parse(str);
    }

    /**
     * Date to str string.
     *
     * @param date      the date
     * @param formatStr the format str
     * @return the string
     * @throws Exception the exception
     */
    public static String dateToStr(Date date, String formatStr) throws Exception {
        String returnStr = "";
        if (null != date) {
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            returnStr = sdf.format(date);
        }
        return returnStr;
    }

    /**
     * 获取起始日期过后的几天
     *
     * @param nowDate the now date
     * @param y       the y
     * @return the next date
     * @throws ParseException the parse exception
     */
    public static String getNextDate(String nowDate, long y) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        //如果不给定起算时间则从今天算起
        if (StringUtils.isEmpty(nowDate)) {
            nowDate = sdf.format(new Date());
        }
        cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(nowDate));
        cal.add(Calendar.DAY_OF_YEAR, Long.valueOf(y).intValue());
        return sdf.format(cal.getTime()).toString();
    }

    /**
     * 获取周 信息
     *
     * @param date the date
     * @return string
     */
    public static String getWeek(Date date) {
        String weekStr = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        weekStr = String.valueOf(week);
        return weekStr;
    }

    /**
     * 判断是否是 周六日
     *
     * @param date the date
     * @return boolean
     */
    public static boolean isWeek(Date date) {
        boolean isWeek = false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 0 || week == 6) {
            isWeek = true;
        }
        return isWeek;
    }

    /**
     * 日期之差 天数
     *
     * @param beginDate the begin date
     * @param endDate   the end date
     * @return long
     */
    public static long getDates(String beginDate, String endDate) {
        Date date1 = null;
        Date date2 = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            date1 = df.parse(beginDate);
            date2 = df.parse(endDate);
            long days = date2.getTime() - date1.getTime();
            days = days / 1000 / 60 / 60 / 24;
            return days;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 日期之差 天数
     *
     * @param beginDate the begin date
     * @param endDate   the end date
     * @return long
     */
    public static long getDates(Date beginDate, Date endDate) {
        try {
            String beginDateStr = dateToStr(beginDate, "yyyy-MM-dd");
            String endDateStr = dateToStr(endDate, "yyyy-MM-dd");
            return getDates(beginDateStr, endDateStr);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据开始日期，结束日期计算出这段日期内的工作日天数（适用周六日休息）
     *
     * @param beginDate the begin date
     * @param endDate   the end date
     * @return work day
     * @throws ParseException the parse exception
     */
    public static int get_work_day(String beginDate, String endDate) throws ParseException {
        int workDay = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        try {
            start.setTime(format.parse(beginDate));
            end.setTime(format.parse(endDate));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (start.after(end)) {
            return -1;
        } else {
            while (start.before(end)) {
                if (start.get(Calendar.DAY_OF_WEEK) != 1 && start.get(Calendar.DAY_OF_WEEK) != 7) {
                    workDay++;
                }
                start.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        if (end.get(Calendar.DAY_OF_WEEK) != 1 && end.get(Calendar.DAY_OF_WEEK) != 7) {
            workDay++;
        }
        // System.out.println(workDay);
        return workDay;
    }

    /**
     * 根据开始日期，结束日期计算出这段日期内的休息天数（适用周六日休息）
     *
     * @param beginDate the begin date
     * @param endDate   the end date
     * @return play day
     * @throws ParseException the parse exception
     */
    public static int get_play_day(String beginDate, String endDate) throws ParseException {
        int playDay = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        try {
            start.setTime(format.parse(beginDate));
            end.setTime(format.parse(endDate));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (start.after(end)) {
            return -1;
        } else {
            while (start.before(end)) {
                if (start.get(Calendar.DAY_OF_WEEK) == 1 || start.get(Calendar.DAY_OF_WEEK) == 7) {
                    playDay++;
                }
                start.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        if (end.get(Calendar.DAY_OF_WEEK) == 1 || end.get(Calendar.DAY_OF_WEEK) == 7) {
            playDay++;
        }
        //System.out.println(playDay);
        return playDay;
    }

    /**
     * 比较两个时间大小
     *
     * @param beginDate the begin date
     * @param endDate   the end date
     * @return int
     */
    public static int compare_date(String beginDate, String endDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        try {
            start.setTime(format.parse(beginDate));
            end.setTime(format.parse(endDate));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (start.after(end)) {
            return -1;
        } else if (start.before(end)) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 获取系统时间 yyyy-MM-dd
     *
     * @return the string
     */
    public static String getCurrentDate() {
        Date data = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = df.format(data);
        return currentDate;
    }

    /**
     * 获取系统时间 yyyy年MM月dd日
     *
     * @return the string
     */
    public static String getCurrentDateOne() {
        Date data = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        String currentDate = df.format(data);
        return currentDate;
    }

    /**
     * 当月第一天
     *
     * @return string
     */
    public static String getFirstDay() {
        // 获取当前年份、月份、日期
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        int day = cale.get(Calendar.DATE);
        int hour = cale.get(Calendar.HOUR_OF_DAY);
        int minute = cale.get(Calendar.MINUTE);
        int second = cale.get(Calendar.SECOND);
        int dow = cale.get(Calendar.DAY_OF_WEEK);
        int dom = cale.get(Calendar.DAY_OF_MONTH);
        int doy = cale.get(Calendar.DAY_OF_YEAR);

        // 获取当月第一天和最后一天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String getFirstDay;
        // 获取前月的第一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        return getFirstDay = format.format(cale.getTime());

    }

    /**
     * 当月最后一天
     *
     * @return last day
     */
    public static String getLastDay() {
        // 获取当前年份、月份、日期
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        int day = cale.get(Calendar.DATE);
        int hour = cale.get(Calendar.HOUR_OF_DAY);
        int minute = cale.get(Calendar.MINUTE);
        int second = cale.get(Calendar.SECOND);
        int dow = cale.get(Calendar.DAY_OF_WEEK);
        int dom = cale.get(Calendar.DAY_OF_MONTH);
        int doy = cale.get(Calendar.DAY_OF_YEAR);

        // 获取当月第一天和最后一天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String getLastDay;
        // 获取前月的最后一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        return getLastDay = format.format(cale.getTime());
    }

    /**
     * Date parse string.
     *
     * @param date the date
     * @return the string
     * @throws ParseException the parse exception
     */
    public static String dateParse(String date) throws ParseException {
        String strDate[] = date.split("-");
        String str = strDate[0] + "年" + strDate[1] + "月" + strDate[2] + "日";
        return str;
    }

    /**
     * 日期加上天数的时间
     *
     * @param date the date
     * @param day  the day
     * @return date
     */
    public static Date dateAddDay(Date date, int day) {
        return add(date, Calendar.DAY_OF_YEAR, day);
    }

    /**
     * 日期加上月数的时间
     *
     * @param date  the date
     * @param month the month
     * @return date
     */
    public static Date dateAddMonth(Date date, int month) {
        return add(date, Calendar.MONTH, month);
    }

    /**
     * 日期加上年数的时间
     *
     * @param date the date
     * @param year the year
     * @return date
     */
    public static Date dateAddYear(Date date, int year) {
        return add(date, Calendar.YEAR, year);
    }

    /**
     * 时间转换为yyyy-MM-dd HH:mm:ss格式的字符串
     *
     * @param date the date
     * @return string
     */
    public static String dateToString_1(Date date) {
        return new SimpleDateFormat(DateUtil.YYYY_MM_DD_MM_HH_SS).format(date);
    }

    /**
     * 计算两个时间之间相差的天数
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return long
     */
    public static long diffDays(Date startDate, Date endDate) {
        long days = 0;
        long start = startDate.getTime();
        long end = endDate.getTime();
        // 一天的毫秒数1000 * 60 * 60 * 24=86400000
        days = (end - start) / 86400000;
        return days;
    }

    /**
     * 计算两个时间之间相差的天数，可显示时，分，秒
     *
     * @param endDate   the end date
     * @param startDate the start date
     * @return string
     */
    public static String diffDaysToTime(Date endDate, Date startDate) {
        long days = 0;
        long start = startDate.getTime();
        long end = endDate.getTime();
        // 一天的毫秒数1000 * 60 * 60 * 24=86400000
        long ms = (end - start) / 1000;
        days = ms / 86400;
        long hour = (ms - days * 86400) / 3600;
        long minute = (ms - days * 86400 - hour * 3600) / 60;
        // long second = minute / 60;
        String time = days + "天" + hour + "小时" + minute + "分";
        return time;
    }

    /**
     * Gets link url.
     *
     * @param flag    the flag
     * @param content the content
     * @param id      the id
     * @return the link url
     * @MethodName: getLinkUrl
     * @Param: DateUtil flag ： true 转换 false 不转换
     * @Author: gang.lv
     * @Date: 2013 -5-8 下午02:52:44
     * @Return:
     * @Descb:
     * @Throws:
     */
    public static String getLinkUrl(boolean flag, String content, String id) {
        if (flag) {
            content = "<a href='finance.do?id=" + id + "'>" + content + "</a>";
        }
        return content;
    }

    /**
     * 获取当前日期的前n天，格式化为yyyy-MM-dd
     *
     * @param n the n
     * @return 如2014 -05-09
     */
    public static String getPreviousDay(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, n); // 得到前一天
        Date date = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    /**
     * <p>
     * 计算时间差，并 返回天、时、分、秒字符串。
     *
     * @param raisestarttime the raisestarttime
     * @param raiseterm      the raiseterm
     * @param days_late      the days late
     * @return String remaining time
     * @throws </p>
     * @since XMJR V2.0.2
     */
    public static String getRemainingTime(String raisestarttime, String raiseterm, String days_late) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = df.parse(raisestarttime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long starttime = date.getTime();
        long nowtime = new Date().getTime();
        long raiselong = Long.parseLong(raiseterm);
        long daylatelong = Long.parseLong(days_late);
        long raisemillis = raiselong * 24 * 60 * 60 * 1000;
        long daylatemillis = daylatelong * 24 * 60 * 60 * 1000;
        long resulttime = (starttime - nowtime + raisemillis + daylatemillis) / 1000;
        long day = resulttime / (24 * 60 * 60);
        long hour = (resulttime / 60 / 60) % 24;
        long min = (resulttime / 60) % 60;
        long sec = (resulttime % 60) % 60;
        return day + "天" + hour + "时" + min + "分" + sec + "秒";
    }

    /**
     * 将时间戳转为字符串
     *
     * @param cc_time the cc time
     * @return str time
     */
    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /**
     * 时间转换为时间戳
     *
     * @param format the format
     * @param date   the date
     * @return time cur
     * @throws ParseException the parse exception
     */
    public static long getTimeCur(String format, Date date) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.parse(sf.format(date)).getTime();
    }

    /**
     * 时间转换为时间戳
     *
     * @param format the format
     * @param date   the date
     * @return time cur
     * @throws ParseException the parse exception
     */
    public static long getTimeCur(String format, String date) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.parse(sf.format(date)).getTime();
    }

    /**
     * <p>Title:    . </p>
     * <p>Description </p>
     *
     * @param patter the patter
     * @return string
     * @Author <a href="190332447@qq.com"/>杨文生</a>
     * @CreateDate 2016 /11/11 12:22
     */
    public static String getPreOneMonth(String patter) {
        Calendar theCa = Calendar.getInstance();
        theCa.setTime(new Date());
        theCa.add(theCa.DATE, -30);
        Date date = theCa.getTime();
        return date2Str(date, patter);
    }

    /**
     * 计算剩余时间 (多少天多少时多少分)
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return string
     */
    public static String remainDateToString(Date startDate, Date endDate) {
        StringBuilder result = new StringBuilder();
        if (endDate == null) {
            return "过期";
        }
        long times = endDate.getTime() - startDate.getTime();
        if (times < -1) {
            result.append("过期");
        } else {
            long temp = 1000 * 60 * 60 * 24;
            // 天数
            long d = times / temp;

            // 小时数
            times %= temp;
            temp /= 24;
            long m = times / temp;
            // 分钟数
            times %= temp;
            temp /= 60;
            long s = times / temp;

            result.append(d);
            result.append("天");
            result.append(m);
            result.append("小时");
            result.append(s);
            result.append("分");
        }
        return result.toString();
    }

    /**
     * Str to date date.
     *
     * @param dateString the date string
     * @return the date
     */
    public static Date strToDate(String dateString) {
        Date date = null;
        try {
            date = new SimpleDateFormat(DateUtil.YYYY_MM_DD_MM_HH_SS).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Str to yymmdd date date.
     *
     * @param dateString the date string
     * @return the date
     */
    public static Date strToYYMMDDDate(String dateString) {
        Date date = null;
        try {
            date = new SimpleDateFormat(DateUtil.YYYY_MM_DD).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private static Date add(Date date, int type, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, value);
        return calendar.getTime();
    }

    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
     *
     * @param date the date
     * @return yyyy -MM-dd HH:mm:ss
     */
    public static String date2Str(Date date) {
        return date2Str(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
     *
     * @param date the date
     * @return date
     */
    public static Date str2Date(String date) {
        if (StringUtils.isNotEmpty(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new Date();
        } else {
            return null;
        }
    }

    /**
     * 按照参数format的格式，日期转字符串
     *
     * @param date   the date
     * @param format the format
     * @return string
     */
    public static String date2Str(Date date, String format) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } else {
            return "";
        }
    }

    /**
     * 把时间根据时、分、秒转换为时间段
     *
     * @param StrDate the str date
     * @return the string
     */
    public static String getTimes(String StrDate) {
        String resultTimes = "";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now;

        try {
            now = new Date();
            Date date = df.parse(StrDate);
            long times = now.getTime() - date.getTime();
            long day = times / (24 * 60 * 60 * 1000);
            long hour = (times / (60 * 60 * 1000) - day * 24);
            long min = ((times / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long sec = (times / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

            StringBuffer sb = new StringBuffer();
            //sb.append("发表于：");
            if (hour > 0) {
                sb.append(hour + "小时前");
            } else if (min > 0) {
                sb.append(min + "分钟前");
            } else {
                sb.append(sec + "秒前");
            }

            resultTimes = sb.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return resultTimes;
    }


    /**
     * Is same day of millis boolean.
     *
     * @param ms1 the ms 1
     * @param ms2 the ms 2
     * @return the boolean
     */
    public static boolean isSameDayOfMillis(final long ms1, final long ms2) {
        final long interval = ms1 - ms2;
        return interval < MILLIS_IN_DAY
                && interval > -1L * MILLIS_IN_DAY
                && toDay(ms1) == toDay(ms2);
    }

    private static long toDay(long millis) {
        return (millis + TimeZone.getDefault().getOffset(millis)) / MILLIS_IN_DAY;
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis the time millis
     * @return string
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     *
     * @return the date
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     *
     * @param pattern the pattern
     * @return the date
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 获取过去的天数
     *
     * @param date the date
     * @return long
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date the date
     * @return long
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date the date
     * @return long
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before the before
     * @param after  the after
     * @return distance of two date
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * 根据出生日期计算年龄
     *
     * @param birthday the birthday
     * @return age by birth
     */
    public static int getAgeByBirth(Date birthday) {
        int age = 0;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());// 当前时间

            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);

            if (birth.after(now)) {//如果传入的时间，在当前时间的后面，返回0岁
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {//兼容性更强,异常后返回数据
            return 0;
        }
    }
}
