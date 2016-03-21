package com.zfer.thread;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 方法内部变量 完全线程安全
 * 非静态类变量 不带static，每次new一个线程安全 单例模式不安全
 * 静态类变量 都是不安全的
 * @author zfer
 */
public class ConcurrentHashMapTest  implements Runnable {
	private ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<String, Integer>();;
	
	public static void main(String[] args) {
		ConcurrentHashMapTest t = new ConcurrentHashMapTest();
		for (int i = 0; i < 7000; i++) {
			new Thread(new ConcurrentHashMapTest(), "线程" + i).start();
		}
	}

	@Override
	public void run() {
		addAndPrint();
	}
	
	private synchronized void addAndPrint(){
		map.put("key", 111);
		map.put("key", 222);
		map.put("key", 333);
		map.put("key", 444);
		System.out.println(map.get("key"));
	}
}