package com.zfer.thread;
import java.text.DateFormat;  
import java.text.ParseException;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
/** 
 * 使用ThreadLocal以空间换时间解决SimpleDateFormat线程安全问题。 
 * @author  
 * 
 */ 
public class DateUtil3 {  
       
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";  
       
    @SuppressWarnings("rawtypes")  
    private static ThreadLocal threadLocal = new ThreadLocal() {  
        protected synchronized Object initialValue() {  
            return new SimpleDateFormat(DATE_FORMAT);  
        }  
    };  
   
    public static DateFormat getDateFormat() {  
        return (DateFormat) threadLocal.get();  
    }  
   
    public static Date parse(String textDate) throws ParseException {  
        return getDateFormat().parse(textDate);  
    }  
}