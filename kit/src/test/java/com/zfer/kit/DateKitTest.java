package com.zfer.kit;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DateKitTest {


    @Test
    public void testGetCurrentDate() {
        assertEquals(10,DateKit.getCurrentDate().length());
        assertEquals(19,DateKit.getCurrentDateTime().length());
    }


    @Test
    public void testToDate() {
        Date date = DateKit.toDate("2016-03-23 12:12:45","yyyy-MM-dd HH:mm:ss");
        assertEquals(1458706365000L,date.getTime());

        Calendar cal = Calendar.getInstance();
        cal.set(2016,2,23,0, 0, 0);
        Date date2 = cal.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date2Str = dateFormat.format(cal.getTime());

        Date date1 = DateKit.toDate("2016-03-23");
        assertEquals(date2Str,DateKit.toStr(date1,DateKit.DATE_FORMAT));
    }

    @Test
    public void testGetYear(){
        java.util.Date date = new Date(1458704405425L);//2016-03-23

        assertEquals(2016,DateKit.getYear(date));
        assertEquals(3,DateKit.getMonth(date));
        assertEquals(23,DateKit.getDay(date));
    }

    @Test
    public void testIsEquals(){
        String dateStr1 = "2017-02-09";
        String dateStr2 = "2017-03-09";

        Date date1 = DateKit.toDate(dateStr1);
        Date date2 = DateKit.toDate(dateStr2);

        assertEquals(false,DateKit.isEquals(date1,date2));
        assertEquals(false,DateKit.isAfterBefore(date1,date2));
        assertEquals(true,DateKit.isBeforeAfter(date1,date2));

    }

    @Test
    public void testDiffYear1(){
        String dateStr1 = "2017-02-09";
        String dateStr2 = "2017-03-09";

        Date date1 = DateKit.toDate(dateStr1);
        Date date2 = DateKit.toDate(dateStr2);

        assertEquals(0,DateKit.diffYear(date1,date2));
    }

    @Test
    public void testDiffYear2(){
        String dateStr1 = "2017-02-09";
        String dateStr2 = "2020-03-09";

        Date date1 = DateKit.toDate(dateStr1);
        Date date2 = DateKit.toDate(dateStr2);

        assertEquals(3,DateKit.diffYear(date1,date2));
    }

    @Test
    public void testGetPostDay(){
        assertEquals("2017-02-08",DateKit.getPreDay("2017-02-09"));
        assertEquals("2017-01-31",DateKit.getPreDay("2017-02-01"));

        assertEquals("2017-02-10",DateKit.getPostDay("2017-02-09"));
        assertEquals("2017-02-01",DateKit.getPostDay("2017-01-31"));

        assertEquals("2017-01-31",DateKit.getEndDateOfMonth("2017-01-12"));
    }

    @Test
    public void testTranMixStr2Date(){
        assertEquals("2017-09-01", DateKit.toStr(DateKit.tranMixStr2Date("2017-09"),DateKit.DATE_FORMAT));

        java.sql.Date date1 = new java.sql.Date(DateKit.toDate("2017-09-03").getTime());
        Date date2 = (Date) DateKit.transDateObj2UtilDate(date1);
        assertEquals("2017-09-03", DateKit.toStr(date2,DateKit.DATE_FORMAT));
    }



}
