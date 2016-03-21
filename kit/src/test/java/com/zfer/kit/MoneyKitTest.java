package com.zfer.kit;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class MoneyKitTest {
	@Test
	public void testGetPartMap(){
		Map map = MoneyKit.getPartMap("123456789.01");
		System.out.println(map);
		assertEquals(map.toString(),"{billion=1, must=2, million=3, hundredThousand=4, tenThousand=5, thousand=6, hundred=7, ten=8, oneYuan=9, corner=0, penny=1}");
	}
	
	@Test
	public void testChinese1(){
		String str = MoneyKit.toChinese("0");
		assertEquals("零元整",str);
	}
}
