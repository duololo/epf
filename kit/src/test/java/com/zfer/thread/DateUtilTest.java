package com.zfer.thread;

import java.text.ParseException;
import java.util.Date;

import com.zfer.kit.DateKit;
import com.zfer.kit.DbKit;

public class DateUtilTest {
    
    public static class TestSimpleDateFormatThreadSafe extends Thread {
        @Override
        public void run() {
            while(true) {
                try {
                    this.join(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                System.out.println(this.getName()+":	"+DateKit.toDate("2013-05-24 06:02:20"));
            }
        }    
    }
    
    
    public static void main(String[] args) {
        for(int i = 0; i < 1500; i++){
            new TestSimpleDateFormatThreadSafe().start();
        }
            
    }
}