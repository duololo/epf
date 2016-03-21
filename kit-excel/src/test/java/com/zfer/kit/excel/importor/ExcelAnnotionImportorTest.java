package com.zfer.kit.excel.importor;

import static org.junit.Assert.*;
import java.io.FileInputStream;
import java.util.List;

import org.junit.Test;
import com.zfer.kit.excel.importor.ExcelAbstractImportor;


public class ExcelAnnotionImportorTest {
	
	@Test
	public void testImportExcel2003() throws Exception {
		FileInputStream fis = new FileInputStream("D://student_2003.xls");
		
		ExcelAbstractImportor<StudentVO> util = new ExcelAnnotionImportor<StudentVO>();// 创建excel工具类
		util.setExcelEntityClass(StudentVO.class);
		
		util.importExcel("学生信息0", fis);// 导入
		List<StudentVO> list = util.getExcelRightDataList();
		
		assertEquals(
				list.toString(),
				"[StudentVO [id=1, name=柳波, company=2009-10-09, age=18, clazz=五期提高班], StudentVO [id=2, name=柳波, company=2016-08-09, age=29, clazz=五期提高班]]");
		
		fis.close();
	}
	
	@Test
	public void testImportExcel2007() throws Exception {
		FileInputStream fis = new FileInputStream("D://student_2007.xlsx");
		
		ExcelAbstractImportor<StudentVO> util = new ExcelAnnotionImportor<StudentVO>();// 创建excel工具类
		util.setExcelEntityClass(StudentVO.class);
		util.importExcel("学生信息0", fis);// 导入
		List<StudentVO> list = util.getExcelRightDataList();
		assertEquals(
				list.toString(),
				"[StudentVO [id=1, name=柳波, company=2009-10-09, age=18, clazz=五期提高班], StudentVO [id=2, name=柳波, company=2016-08-09, age=29, clazz=五期提高班]]");
		
		fis.close();
	}
	
	
}
