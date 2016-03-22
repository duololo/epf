package com.zfer.kit.excel.importor;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.zfer.kit.excel.ExcelPoiKit;
import com.zfer.kit.excel.exportor.ExcelWorkbookKit;


public class ExcelTemplateImportorTest {
	InputStream fis2003;
	InputStream fis2007;
	InputStream fis_exception2003;
	InputStream fis_exception2007;
	InputStream templateFis;
	
	@Before
	public void setUp(){
		fis2003 = Thread.currentThread().getContextClassLoader().getResourceAsStream("student_2003.xls");
		fis2007 = Thread.currentThread().getContextClassLoader().getResourceAsStream("student_2007.xlsx");
		fis_exception2003 = Thread.currentThread().getContextClassLoader().getResourceAsStream("student_exception.xls");
		fis_exception2007 = Thread.currentThread().getContextClassLoader().getResourceAsStream("student_exception.xlsx");
		templateFis = Thread.currentThread().getContextClassLoader().getResourceAsStream("student_template.xls");
	}
	
	@After
	public void tearDown(){
		if (fis2003 != null) try {fis2003.close();} catch (IOException e) {e.printStackTrace();}
		if (fis2007 != null) try {fis2007.close();} catch (IOException e) {e.printStackTrace();}
		if (templateFis != null) try {templateFis.close();} catch (IOException e) {e.printStackTrace();}
		if (fis_exception2003 != null) try {fis_exception2003.close();} catch (IOException e) {e.printStackTrace();}
		if (fis_exception2007 != null) try {fis_exception2007.close();} catch (IOException e) {e.printStackTrace();}
	}
	
	@Test
	public void testImportExcel2003() throws Exception {
		ExcelAbstractImportor<StudentNullAnnoVO> util = new ExcelTemplateImportor<StudentNullAnnoVO>();// 创建excel导入工具类
		util.setExcelEntityClass(StudentNullAnnoVO.class);//设置导出的实体类型
		util.setTemplateExcelInputStream(templateFis);//设置模板
		
		util.importExcel("学生信息0", fis2003);// 导入
		List<StudentNullAnnoVO> list = util.getExcelRightDataList();//获取校验正确的数据
		List<StudentNullAnnoVO> allList = util.getExcelAllDataList();//获取所有的数据
		
		assertEquals(
				list.toString(),
				"[StudentVO [id=1, name=柳波, company=2009-10-09, age=18, clazz=五期提高班], StudentVO [id=2, name=柳波, company=2016-08-09, age=29, clazz=五期提高班]]");
		
		ExcelImportSheetErrorMsg error = util.getExcelImportSheetErrorMsg();
		assertEquals("{}", error.getExcelImportRowErrorMsgMap().toString());
	}
	
	@Test
	public void testImportExcel2007() throws Exception {
		ExcelAbstractImportor<StudentNullAnnoVO> util = new ExcelTemplateImportor<StudentNullAnnoVO>();// 创建excel工具类
		util.setExcelEntityClass(StudentNullAnnoVO.class);
		util.setTemplateExcelInputStream(templateFis);
		
		util.importExcel("学生信息0", fis2007);// 导入
		List<StudentNullAnnoVO> list = util.getExcelRightDataList();
		assertEquals(
				list.toString(),
				"[StudentVO [id=1, name=柳波, company=2009-10-09, age=18, clazz=五期提高班], StudentVO [id=2, name=柳波, company=2016-08-09, age=29, clazz=五期提高班]]");
		
		ExcelImportSheetErrorMsg error = util.getExcelImportSheetErrorMsg();
		assertEquals("{}", error.getExcelImportRowErrorMsgMap().toString());
	}
	
	@Test
	public void testImportExcelRequiredException2003() throws FileNotFoundException,Exception {
		ExcelAbstractImportor<StudentNullAnnoVO> util = new ExcelTemplateImportor<StudentNullAnnoVO>();// 创建excel工具类
		util.setExcelEntityClass(StudentNullAnnoVO.class);
		util.setTemplateExcelInputStream(templateFis);
		
		util.importExcel("学生信息0", fis_exception2003);// 导入
		
		ExcelImportSheetErrorMsg error = util.getExcelImportSheetErrorMsg();
		assertEquals("{1={1=此处不能为空，请填写}, 2={2=此处格式不符合，请修改, 5=此处不能为空，请填写}}", 
				error.getExcelImportRowErrorMsgMap().toString());
	}
	
	
	@Test
	public void testImportExcelRegexException2007() throws FileNotFoundException,Exception {
		ExcelAbstractImportor<StudentNullAnnoVO> util = new ExcelTemplateImportor<StudentNullAnnoVO>();// 创建excel工具类
		util.setExcelEntityClass(StudentNullAnnoVO.class);
		util.setTemplateExcelInputStream(templateFis);
		
		util.importExcel("学生信息0", fis_exception2007);// 导入
		
		ExcelImportSheetErrorMsg error = util.getExcelImportSheetErrorMsg();
		assertEquals("{1={1=此处不能为空，请填写}, 2={2=此处格式不符合，请修改, 5=此处不能为空，请填写}}", 
				error.getExcelImportRowErrorMsgMap().toString());
	}
	
	@Test
	public void testImportExceptionExport2007() throws FileNotFoundException,Exception {
		
		ExcelAbstractImportor<StudentNullAnnoVO> util = new ExcelTemplateImportor<StudentNullAnnoVO>();// 创建excel工具类
		util.setExcelEntityClass(StudentNullAnnoVO.class);
		util.setTemplateExcelInputStream(templateFis);
		
		util.importExcel("", fis_exception2007);// 导入
		ExcelImportSheetErrorMsg error = util.getExcelImportSheetErrorMsg();
		
		ExcelWorkbookKit exportor = new ExcelWorkbookKit();
		FileInputStream fis1 = new FileInputStream("D://student_exception.xlsx");
		FileOutputStream output = new FileOutputStream("D://student_export_excepiton.xlsx");
		
		Workbook workbook = WorkbookFactory.create(fis1);		
		workbook = exportor.handleWorkbook(workbook,0, error.getExcelImportRowErrorMsgMap());
		workbook.write(output);
		output.close();
		
		FileInputStream inputrs = new FileInputStream("D://student_export_excepiton.xlsx");
		
		Workbook workbook2 = WorkbookFactory.create(inputrs); 
		Cell cell = ExcelPoiKit.getSheet(0, workbook2).getRow(2).getCell(2);
		String cellValue = ExcelPoiKit.getCellVal(cell);
		assertEquals("x20(错误：此处格式不符合，请修改)", cellValue);
		
		inputrs.close();
		
	}
	
	@Test
	public void testImportExceptionExport2003() throws FileNotFoundException,Exception {
		ExcelAbstractImportor<StudentNullAnnoVO> util = new ExcelTemplateImportor<StudentNullAnnoVO>();// 创建excel工具类
		util.setExcelEntityClass(StudentNullAnnoVO.class);
		util.setTemplateExcelInputStream(templateFis);
		
		try{
		util.importExcel("学生信息0", fis_exception2003);// 导入
		}catch(NumberFormatException e){
			
		}
		
		ExcelImportSheetErrorMsg error = util.getExcelImportSheetErrorMsg();
		Workbook wb = util.getDataWorkbook();
		
		ExcelWorkbookKit exportor = new ExcelWorkbookKit();
		FileOutputStream output = new FileOutputStream("D://student_export_excepiton.xls");
		Workbook workbook = exportor.handleWorkbook(wb, 0, error.getExcelImportRowErrorMsgMap());
		workbook.write(output);
		output.close();
		
		
		FileInputStream inputrs = new FileInputStream("D://student_export_excepiton.xls");
		
		Workbook workbook2 = WorkbookFactory.create(inputrs); 
		Cell cell = ExcelPoiKit.getSheet(0, workbook2).getRow(2).getCell(2);
		String cellValue = ExcelPoiKit.getCellVal(cell);
		assertEquals("x20(错误：此处格式不符合，请修改)", cellValue);
		
		inputrs.close();
		
	}
	
}
