package com.zfer.kit;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;

public class DateKit2Test {

	@Test
	public void testToDate() {
		DateTime dt = DateKit.toDate("2015-03-12");
		int year = dt.getYear();
		int month = dt.getMonthOfYear();
		int day = dt.getDayOfMonth();
		assertEquals(year,2015);
		assertEquals(month,3);
		assertEquals(day,12);
	}

	@Test
	public void testToTime() {
		DateTime dt = DateKit.toTime("13:12:10");
		int hour = dt.getHourOfDay();
		int minute = dt.getMinuteOfHour();
		int second = dt.getSecondOfMinute();
		assertEquals(hour,13);
		assertEquals(minute,12);
		assertEquals(second,10);
	}

	@Test
	public void testToDateTimeString() {
		DateTime dt = DateKit.toDateTime("2015-09-12 14:23:34");
		int hour = dt.getHourOfDay();
		assertEquals(hour,14);
	}

	@Test
	public void testToDateTimeStringString() {
		DateTime dt = DateKit.toDateTime("2015-09-12 14:23","yyyy-MM-dd HH:ss");
		int hour = dt.getHourOfDay();
		assertEquals(hour,14);
	}

	@Test
	public void testToDateStr() {
		
	}

	@Test
	public void testToTimeStr() {
		
	}

	@Test
	public void testToDateTimeStrDateTime() {
		
	}

	@Test
	public void testToDateTimeStrDateTimeString() {
		DateTime dt = new DateTime(2012,2,1,12,23,45);
		String str = DateKit.toDateTimeStr(dt,"yyyy-MM-dd HH:mm:ss");
		assertEquals("2012-02-01 12:23:45",str);
	}

	@Test
	public void testGetYear() {
		DateTime dt = new DateTime(2016,2,1,12,23,45);
		int value = DateKit.getYear(dt);
		assertEquals(2016,value);
	}

	@Test
	public void testGetMonth() {
		DateTime dt = new DateTime(2016,5,1,12,23,45);
		int value = DateKit.getMonth(dt);
		assertEquals(5,value);
	}

	@Test
	public void testGetDay() {
		DateTime dt = new DateTime(2016,5,1,12,23,45);
		int value = DateKit.getDay(dt);
		assertEquals(1,value);
	}

	@Test
	public void testCompareTo() {
		DateTime dt1 = new DateTime(2016,7,1,12,23,45);
		DateTime dt2 = new DateTime(2016,6,1,12,23,45);
		boolean rs = DateKit.isBeforeAfter(dt1,dt2);
		assertEquals(rs,false);
	}

	@Test
	public void testDiffYearStringString() {
		int year = DateKit.diffYear("2001-09-12", "2002-09-12");
		assertEquals(year,1);
	}

	@Test
	public void testDiffYearDateTimeDateTime() {
		
	}

	@Test
	public void testGetPreDayString() {
		String preDay = DateKit.getPreDay("2015-12-23");
		assertEquals(preDay,"2015-12-22");
		String preDay1 = DateKit.getPreDay("2015-12-01");
		assertEquals(preDay1,"2015-11-30");
	}

	@Test
	public void testGetPreDayDateTime() {
		
	}

	@Test
	public void testGetPostDayString() {
		String preDay = DateKit.getPostDay("2015-12-23");
		assertEquals(preDay,"2015-12-24");
		String preDay1 = DateKit.getPostDay("2015-12-30");
		assertEquals(preDay1,"2015-12-31");
	}

	@Test
	public void testGetPostDayDateTime() {
		
	}

	@Test
	public void testGetMonthEndDateString() {
		
	}

	@Test
	public void testGetMonthEndDateDateTime() {
		String value = DateKit.getEndOfMonthDate("2015-12-23");
		assertEquals(value,"2015-12-31");
	}

	@Test
	public void testTranMixStr2Date() {
		DateTime value = DateKit.tranMixStr2Date("2015-12");
		assertEquals(2015,value.getYear());
		assertEquals(12,value.getMonthOfYear());
		assertEquals(1,value.getDayOfMonth());
		DateTime value1 = DateKit.tranMixStr2Date("2015-12-01 12");
		assertEquals(2015,value1.getYear());
		assertEquals(12,value1.getMonthOfYear());
		assertEquals(1,value1.getDayOfMonth());
		assertEquals(12,value1.getHourOfDay());
	}

}
