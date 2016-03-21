package com.zfer.thread;
public class TestThread3 implements Runnable  
{  
    public void run()  
    {  
        int local_i = 4;  
        System.out.println("[" + Thread.currentThread().getName()  
                + "]获取local_i 的值:" + local_i);  
        local_i = 10;  
        System.out.println("[" + Thread.currentThread().getName()  
                + "]获取local_i*2的值:" + local_i * 2);  
    }  
      
    public static void main(String[] args)  
    {  
        TestThread3 t = new TestThread3();  
        //启动尽量多的线程才能很容易的模拟问题  
        for (int i = 0; i < 3000; i++)  
        {  
            //每个线程对在对象t中运行，模拟单例情况   
            new Thread(t, "线程" + i).start();  
        }  
    }  
}  