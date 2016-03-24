/**
 * Copyright (c) 2016-2020, 济南-云山 (77079588@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zfer.kit;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * 日期工具类，使用完美的Joda-time实现(java8日期时间模块的同一作者)
 * Joda-Time 轻松化解了处理日期和时间的痛苦和繁琐。
*/
public class DateKit {
	
	//这个类不能实例化
	private DateKit() {
	}
	
	//yyyy-MM-dd HH:mm:ss.SSS
	public static String dateFormat = "yyyy-MM-dd";
	public static String timeFormat = "HH:mm:ss";
	public static String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	
	//String To Date
	public static DateTime toDate(String dateStr) {
		return toDateTime(dateStr,DateKit.dateFormat); 
	}
	public static DateTime toTime(String timeStr) {
		return toDateTime(timeStr,DateKit.timeFormat); 
	}
	public static DateTime toDateTime(String dateTimeStr) {
		return toDateTime(dateTimeStr,DateKit.dateTimeFormat); 
	}
	public static DateTime toDateTime(String dateStr, String format) {
		if(StrKit.isBlank(dateStr)) return null;
		return DateTimeFormat.forPattern(format).parseDateTime(dateStr); 
	}
	
	//Date To String
	public static String toDateStr(DateTime dt) {
		return toDateTimeStr(dt, DateKit.dateFormat);
	}
	public static String toTimeStr(DateTime dt) {
		return toDateTimeStr(dt, DateKit.timeFormat);
	}
	public static String toDateTimeStr(DateTime dt) {
		return toDateTimeStr(dt, DateKit.dateTimeFormat);
	}
	public static String toDateTimeStr(DateTime dt, String format) {
		if(dt == null) return "";
		return dt.toString(format);
	}
	public static String toStr(java.util.Date date, String format) {
		if(date == null) return "";
		DateTime dt = new DateTime(date);
		return dt.toString(format);
	}
	
	//Get Year Month Day
	public static int getYear(DateTime dt){
		return dt.getYear();
	}
	public static int getMonth(DateTime dt){
		return dt.getMonthOfYear();
	}
	public static int getDay(DateTime dt){
		return dt.getDayOfMonth();
	}
	
	/**
	 * 比较日期大小，不要使用Date的before after方法
	 * 如果两个日期一样则返回0，如果第一个日期比第二个数晚则返回>0，反之返回<0
	 * @param dt1
	 * @param dt2
	 * @return
	 */
	public static boolean isEqualsDate(DateTime dt1,DateTime dt2){
		return compareTo(dt1,dt2) == 0 ? true : false;
	}
	public static boolean isAfterBefore(DateTime dt1,DateTime dt2){
		return compareTo(dt1,dt2) < 0 ? true : false;
	}
	public static boolean isBeforeAfter(DateTime dt1,DateTime dt2){
		return compareTo(dt1,dt2) > 0 ? true : false;
	}
	public static long compareTo(DateTime dt1,DateTime dt2){
		return dt2.getMillis()-dt1.getMillis();
	}
	
	/**
	 * diffYear 直接取值年份来处理，并非取值天数、秒数来处理
	 * @param dt1Str
	 * @param dt2Str
	 * @return
	 */
	public static int diffYear(String dt1Str,String dt2Str){
		return diffYear(toDate(dt1Str), toDate(dt2Str));
	}
	public static int diffYear(DateTime dt1,DateTime dt2){
		int year1 = getYear(dt1);
		int year2 = getYear(dt2);
		return Math.abs(year1 - year2);
	}
	
	//获取preDay
	public static String getPreDay(String dateStr){
		if(StrKit.isBlank(dateStr)) return "";
		DateTime dt = getPreDay(toDate(dateStr));
		return toDateStr(dt);
	}
	public static DateTime getPreDay(DateTime dt){
		return dt.minusDays(1);
	}
	
	//获取postDay
	public static String getPostDay(String dateStr){
		if(StrKit.isBlank(dateStr)) return "";
		DateTime dt = getPostDay(toDate(dateStr));
		return toDateStr(dt);
	}
	public static DateTime getPostDay(DateTime dt){
		return dt.plusDays(1);
	}
	
	//获取某日期 的月末日期 2013-02-01
	public static String getEndOfMonthDate(String dateStr){
		return toDateStr(getEndOfMonthDate(toDate(dateStr)));
	}
	public static DateTime getEndOfMonthDate(DateTime dt){
		return dt.dayOfMonth().withMaximumValue();
	}
	
	/**
	 * 将年份开头字符串 类似
	 * 2015 | 2015-01 | 2015-1-12 | 2015-12-10 12:12:23 |
	 * 2015.02 2015.03 2015.07
	 * 转换成Date格式
	 * 不支持毫秒 微秒 纳秒；
	 * @param someDateStr
	 */
	public static DateTime tranMixStr2Date(String someDateStr){
		DateTime dt = new DateTime();
		String str = someDateStr.replace(".", "-").trim();
		
		int spaceL = str.split(" ").length-1;
		
		int centerL = str.split("-").length-1;
		int pointL = str.split(":").length-1;
		
		if(spaceL == 0){
			if(centerL == 0){
				dt = toDateTime(str,"yyyy");
			}
			if(centerL == 1){
				dt = toDateTime(str,"yyyy-MM");
			}
			if(centerL == 2){
				dt = toDateTime(str,"yyyy-MM-dd");
			}
		}
		
		if(spaceL == 1){
			if(pointL == 0){
				dt = toDateTime(str,"yyyy-MM-dd HH");
			}
			if(pointL == 1){
				dt = toDateTime(str,"yyyy-MM-dd HH:mm");
			}
			if(pointL == 2){
				dt = toDateTime(str,"yyyy-MM-dd HH:mm:ss");
			}
		}
		return dt;
	}
	
	//date类型的转换
	public static java.util.Date transDate(Object dateObj){
		long timeLong = 0;
		if(dateObj instanceof java.util.Date){
			timeLong = ((java.util.Date)dateObj).getTime();
		}else if(dateObj instanceof java.sql.Date){
			timeLong = ((java.sql.Date)dateObj).getTime();
		}else if(dateObj instanceof java.sql.Timestamp){
			timeLong = ((java.sql.Timestamp)dateObj).getTime();
		}else if(dateObj instanceof java.sql.Time){
			timeLong = ((java.sql.Time)dateObj).getTime();
		}
		java.util.Date date = new java.util.Date(timeLong);
		return date;
	}
}

