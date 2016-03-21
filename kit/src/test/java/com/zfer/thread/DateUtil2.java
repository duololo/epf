package com.zfer.thread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil2 {
    
    
    public static  String formatDate(Date date)throws ParseException{
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//虽然没有线程安全问题，但是开销很大
        return sdf.format(date);
    }
    
    public static Date parse(String strDate) throws ParseException{
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(strDate);
    }
}