package com.zfer.thread;

/**  
 * 线程安全问题模拟执行  
 *  ------------------------------  
 *       线程1      |    线程2  
 *  ------------------------------  
 *   static_i = 4;  | 等待  
 *   static_i = 10; | 等待  
 *    等待          | static_i = 4;  
 *   static_i * 2;  | 等待  
 *  ----------------------------- 
* */  
public class TestThread1 implements Runnable {
	private static int static_i;// 静态变量

	public void run() {
		static_i = 4;
		System.out.println("[" + Thread.currentThread().getName() + "]获取static_i 的值:" + static_i);
		static_i = 10;
		System.out.println("[" + Thread.currentThread().getName() + "]获取static_i*2的值:" + static_i * 2);
	}

	/*
	 * 正常每个线程输出4 20，如果出现4切入4*2=8；获取10切入被输出，则线程不安全
	 * @param args
	 */
	public static void main(String[] args) {
		TestThread1 t = new TestThread1();
		// 启动尽量多的线程才能很容易的模拟问题
		for (int i = 0; i < 100; i++) {
			// t可以换成new Test(),保证每个线程都在不同的对象中执行，结果一样
			new Thread(t, "线程" + i).start();
		}
	}
}