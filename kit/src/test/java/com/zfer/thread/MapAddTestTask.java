package com.zfer.thread;

import java.util.concurrent.ConcurrentHashMap;

public class MapAddTestTask implements Runnable {
	private ConcurrentHashMap<String, Integer> map;

	public MapAddTestTask(ConcurrentHashMap<String, Integer> map) {
		this.map = map;
	}

	public void run() {
		Integer rs = map.get("key");//先得出来
		rs = rs + 1;//加1
		map.put("key", rs);//放入
	}
}