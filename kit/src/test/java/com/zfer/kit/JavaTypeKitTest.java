package com.zfer.kit;

import static org.junit.Assert.*;

import org.junit.Test;

public class JavaTypeKitTest {

	@Test
	public void testJudgeBasicType() {
		boolean flag = JavaTypeKit.judgeBasicType(int.class);
		assertEquals(flag, true);
		
		flag = JavaTypeKit.judgeBasicType(Object.class);
		assertEquals(flag, false);
	}

}
