package com.zfer.kit;

import org.joda.time.DateTime;

import junit.framework.TestCase;

public class DateKitTest extends TestCase {
	public void testToDateString() {
		
	}

	public void testToDateStringString() {
		
	}

	public void testToStrDate() {
		
	}

	public void testToStrDateString() {
		
	}

	public void testGetYear() {
		
	}

	public void testGetMonth() {
		
	}

	public void testGetDay() {
		
	}

	public void testCompareTo() {
		DateTime dt1 = DateKit.toDate("2015-10-25");
		DateTime dt2 = DateKit.toDate("2015-10-22");
		int day1 = dt1.getDayOfMonth();
		int day2 = dt2.getDayOfMonth();
		int expected = Math.abs(day1-day2);
		assertEquals(expected, 3);
	}

	public void testDiffYearStringString() {
	}

	public void testDiffYearDateDate() {
	}

	public void testGetPreDay() {
		
	}

	public void testGetPostDay() {
		
	}

	public void testGetMonthEndDate() {
		
	}

}
