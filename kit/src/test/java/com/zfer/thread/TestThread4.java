package com.zfer.thread;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestThread4 {
	public static void main(String[] args) throws InterruptedException {
		ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<String, Integer>();
		map.put("key", 0);
		
		ExecutorService pool = Executors.newCachedThreadPool();
		
		for (int i = 1; i <= 10; i++) {  
			pool.execute(new MapAddTestTask(map));
        }
		
		pool.shutdown();
		pool.awaitTermination(1, TimeUnit.DAYS);
		
		System.out.println(map.get("key"));
	}
}

