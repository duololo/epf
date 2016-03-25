/**
 * Copyright (c) 2016-2020, 济南-云山 (77079588@qq.com).
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zfer.kit;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;

/**
 * 日期工具类，使用完美的Joda-time实现(java8日期时间模块的同一作者)
 * Joda-Time 轻松化解了处理日期和时间的痛苦和繁琐。
 *
 * @author yunshan
 * @version 1.0
 */
public class DateKit {

    /**
     * Constructor.
     */
    private DateKit() {
        super();
    }

    /**
     * Output the date time in ISO8601 format (yyyy-MM-ddTHH:mm:ss.SSSZZ).
     * yyyy-MM-dd HH:mm:ss E 后面是：星期几
     * length:10
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Output the date time in ISO8601 format (yyyy-MM-ddTHH:mm:ss.SSSZZ).
     * yyyy-MM-dd HH:mm:ss E 后面是：星期几
     * length:8
     */
    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * Output the date time in ISO8601 format (yyyy-MM-ddTHH:mm:ss.SSSZZ).
     * yyyy-MM-dd HH:mm:ss E 后面是：星期几
     * length:19
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    //-----------------------------------------------------------------------

    /**
     * 常用的格式：获取当前日期
     *
     * @return currentDate
     */
    public static String getCurrentDate() {
        DateTime dt = new DateTime();
        return dt.toString(DATE_FORMAT);
    }

    /**
     * 常用的格式：获取当前日期和时间
     *
     * @return currentDateTime
     */
    public static String getCurrentDateTime() {
        DateTime dt = new DateTime();
        return dt.toString(DATE_TIME_FORMAT);
    }

    //-----------------------------------------------------------------------

    /**
     * string to date.
     *
     * @param str input string
     * @return Date
     */
    public static Date toDate(String str) {
        String format = getFormat(str);
        return toDate(str, format);
    }

    /**
     * string to date.
     *
     * @param str    input string
     * @param format formatString
     * @return Date
     */
    public static Date toDate(String str, String format) {
        if (StrKit.isBlank(str)) {
            return null;
        }
        DateTime dt = DateTimeFormat.forPattern(format).parseDateTime(str);
        return dt.toDate();
    }

    /**
     * string to date.
     *
     * @param str    input string
     * @param format format string
     * @return DateTime
     */
    public static DateTime toDateTime(String str, String format) {
        if (StrKit.isBlank(str)) {
            return null;
        }
        return DateTimeFormat.forPattern(format).parseDateTime(str);
    }

    /**
     * date to string.
     *
     * @param date   input date object
     * @param format fomat pattern string
     * @return date to string
     */
    public static String toStr(Date date, String format) {
        if (date == null) {
            return "";
        }
        DateTime dt = new DateTime(date);
        return dt.toString(format);
    }

    /**
     * get general format pattern.
     *
     * @param str date string
     * @return base date string length
     */
    private static String getFormat(String str) {
        if (StrKit.isBlank(str)) {
            return DATE_TIME_FORMAT;
        }
        String format = DATE_TIME_FORMAT;
        if (str.length() == DATE_FORMAT.length()) {
            format = DATE_FORMAT;
        } else if (str.length() == TIME_FORMAT.length()) {
            format = TIME_FORMAT;
        }
        return format;
    }

    //-----------------------------------------------------------------------

    /**
     * get year.
     *
     * @param date date object
     * @return year of date
     */
    public static int getYear(Date date) {
        DateTime dt = new DateTime(date);
        return dt.getYear();
    }

    /**
     * get month.
     *
     * @param date date object
     * @return month of date
     */
    public static int getMonth(Date date) {
        DateTime dt = new DateTime(date);
        return dt.getMonthOfYear();
    }

    /**
     * get day.
     *
     * @param date input date
     * @return day of date object
     */
    public static int getDay(Date date) {
        DateTime dt = new DateTime(date);
        return dt.getDayOfMonth();
    }

    //-----------------------------------------------------------------------

    /**
     * 比较日期大小，不要使用Date的before after方法.
     *
     * @param date1 input date1
     * @param date2 input date2
     * @return two date is equals
     */
    public static boolean isEquals(Date date1, Date date2) {
        DateTime dt1 = new DateTime(date1);
        DateTime dt2 = new DateTime(date2);
        return dt1.isEqual(dt2);
    }

    /**
     * 比较日期大小，不要使用Date的before after方法.
     *
     * @param date1 input date1
     * @param date2 input date2
     * @return is date1 is before of date2
     */
    public static boolean isBeforeAfter(Date date1, Date date2) {
        DateTime dt1 = new DateTime(date1);
        DateTime dt2 = new DateTime(date2);
        return dt1.isBefore(dt2);
    }

    /**
     * 比较日期大小，不要使用Date的before after方法.
     *
     * @param date1 input date1
     * @param date2 input date2
     * @return is date1 is after date2
     */
    public static boolean isAfterBefore(Date date1, Date date2) {
        DateTime dt1 = new DateTime(date1);
        DateTime dt2 = new DateTime(date2);
        return dt1.isAfter(dt2);
    }

    //-----------------------------------------------------------------------

    /**
     * diffYear 直接取值年份来处理，并非取值天数、秒数来处理
     *
     * @param date1 input date1
     * @param date2 input date2
     * @return two dates diffYear
     */
    public static int diffYear(Date date1, Date date2) {
        if (StrKit.isNull(date1, date2)) {
            return 0;
        }
        DateTime dt1 = new DateTime(date1);
        DateTime dt2 = new DateTime(date2);
        int year1 = dt1.getYear();
        int year2 = dt2.getYear();
        return Math.abs(year1 - year2);
    }

    //-----------------------------------------------------------------------

    /**
     * 获取preDay.
     *
     * @param dateStr like 2014-09-23 ISO8601形式
     * @return preDay the pre of day
     */
    public static String getPreDay(String dateStr) {
        if (StrKit.isBlank(dateStr)) {
            return "";
        }
        DateTime dt = new DateTime(dateStr).minusDays(1);
        return dt.toString(DATE_FORMAT);
    }


    /**
     * 获取 postDay.
     *
     * @param dateStr like 2014-09-23 ISO8601形式
     * @return postDay
     */
    public static String getPostDay(String dateStr) {
        if (StrKit.isBlank(dateStr)) {
            return "";
        }
        DateTime dt = new DateTime(dateStr).plusDays(1);
        return dt.toString(DATE_FORMAT);
    }

    /**
     * 获取某日期 的月末日期 2013-02-01.
     *
     * @param dateStr input dateStr
     * @return end of month date
     */
    public static String getEndDateOfMonth(String dateStr) {
        DateTime dt = new DateTime(dateStr)
                .dayOfMonth().withMaximumValue();
        return dt.toString(DATE_FORMAT);
    }

    //-----------------------------------------------------------------------

    /**
     * 将年份开头字符串 类似
     * 2015 | 2015-01 | 2015-1-12 | 2015-12-10 12:12:23 |
     * 2015.02 2015.03 2015.07
     * 转换成Date格式
     * 不支持毫秒 微秒 纳秒；
     *
     * @param someDateStr input param
     * @return not usual pattern string to date object
     */
    public static Date tranMixStr2Date(String someDateStr) {
        if (StrKit.isBlank(someDateStr)) {
            return null;
        }
        DateTime dt = new DateTime();
        String str = someDateStr.replace(".", "-").trim();

        int spaceL = str.split(" ").length - 1;

        int centerL = str.split("-").length - 1;
        int pointL = str.split(":").length - 1;

        if (spaceL == 0) {
            if (centerL == 0) {
                dt = toDateTime(str, "yyyy");
            }
            if (centerL == 1) {
                dt = toDateTime(str, "yyyy-MM");
            }
            if (centerL == 2) {
                dt = toDateTime(str, "yyyy-MM-dd");
            }
        }

        if (spaceL == 1) {
            if (pointL == 0) {
                dt = toDateTime(str, "yyyy-MM-dd HH");
            }
            if (pointL == 1) {
                dt = toDateTime(str, "yyyy-MM-dd HH:mm");
            }
            if (pointL == 2) {
                dt = toDateTime(str, "yyyy-MM-dd HH:mm:ss");
            }
        }
        if (dt == null) {
            return null;
        }
        return dt.toDate();
    }

    /**
     * date类型的转换.
     *
     * @param dateObj want to transDate
     * @return if dateObj instanceof java.sql.Date Timestamp Time java.util.Date
     * return java.util.Date
     * else dateObj
     */
    public static Object transDateObj2UtilDate(Object dateObj) {
        if (!(dateObj instanceof java.sql.Date
                || dateObj instanceof java.sql.Timestamp
                || dateObj instanceof java.sql.Time
                || dateObj instanceof java.util.Date)) {
            return dateObj;
        }
        long timeLong;
        if (dateObj instanceof java.sql.Date) {
            timeLong = ((java.sql.Date) dateObj).getTime();
        } else if (dateObj instanceof java.sql.Timestamp) {
            timeLong = ((java.sql.Timestamp) dateObj).getTime();
        } else if (dateObj instanceof java.sql.Time) {
            timeLong = ((java.sql.Time) dateObj).getTime();
        } else {
            timeLong = ((java.util.Date) dateObj).getTime();
        }
        return new java.util.Date(timeLong);
    }
}

