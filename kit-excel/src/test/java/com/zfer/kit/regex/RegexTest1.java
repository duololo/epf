package com.zfer.kit.regex;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexTest1 {
	
	@Test
	public void testDateRegex1(){
		String str = "2015/1/9";
		String pattern = "\\d{4}(\\-|\\/|.)\\d{1,2}\\1\\d{1,2}";

		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(str);
		boolean result = m.matches();
		assertEquals(true,result);
	}
}
