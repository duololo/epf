package com.zfer.thread;//package com.zfer.thread;
//
//import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
//import net.sourceforge.groboutils.junit.v1.TestRunnable;
//
//import org.junit.Test;
//
//public class MultiThreadedTest {
//	/**
//	 * 多线程测试用例
//	 */
//	@Test
//	public void MultiRequestsTest() {
//		// 构造一个Runner
//		TestRunnable runner = new TestRunnable() {
//			@Override
//			public void runTest() throws Throwable {
//				// 测试内容
//			}
//		};
//		int runnerCount = 100;
//		// Rnner数组，想当于并发多少个。
//		TestRunnable[] trs = new TestRunnable[runnerCount];
//		for (int i = 0; i < runnerCount; i++) {
//			trs[i] = runner;
//		}
//		// 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入
//		MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
//		try {
//			// 开发并发执行数组里定义的内容
//			mttr.runTestRunnables();
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//	}
//}
