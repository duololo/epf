package com.zfer.kit;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class MoneyKit2Test {
	
	@Test
	public void testChinese1(){
		String d1 = "2020004.01";
		String str1 = MoneyKit.toChinese(d1);
		String str2 = MoneyKit2.toChinese(d1);
		//assertEquals(str1, str2);
		
		d1 = "04.01";
		str1 = MoneyKit.toChinese(d1);
		str2 = MoneyKit2.toChinese(d1);
		//assertEquals(str1, str2);
		
		d1 = "4343";
		str1 = MoneyKit.toChinese(d1);
		str2 = MoneyKit2.toChinese(d1);
		assertEquals(str1, str2);
	}
}
