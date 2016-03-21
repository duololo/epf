package com.zfer.thread;
public class TestThread2 implements Runnable  
{  
    private int instance_i;//实例变量  
      
    public void run()  
    {  
        instance_i = 4;  
        System.out.println("[" + Thread.currentThread().getName()  
                + "]获取instance_i 的值:" + instance_i);  
        instance_i = 10;  
        System.out.println("[" + Thread.currentThread().getName()  
                + "]获取instance_i*3的值:" + instance_i * 2);  
    }  
      
    //这里如果每个线程都new一个类，则不会出现
    public static void main(String[] args)  
    {  
        TestThread2 t = new TestThread2();  
        //启动尽量多的线程才能很容易的模拟问题   
        for (int i = 0; i < 3000; i++)  
        {  
            //每个线程对在对象t中运行，模拟单例情况  
            new Thread(t, "线程" + i).start();  
        }  
    }  
}  