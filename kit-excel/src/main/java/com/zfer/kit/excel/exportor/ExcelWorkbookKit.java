package com.zfer.kit.excel.exportor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.zfer.kit.excel.ExcelPoiKit;



/**
 * 根据模板和对应的数据导出相应的excel
 *
 */
public class ExcelWorkbookKit {
	/**
	 * 在已经存在的workbook上进行处理
	 * @param sheetName
	 * @param sheetData
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public Workbook handleWorkbook(Workbook workbook,String sheetName,Map<Integer, Map<Integer,String>> sheetData) throws InvalidFormatException, IOException{
		Sheet sheet = ExcelPoiKit.getSheet(sheetName, workbook);
		handleSheet(workbook,sheet,sheetData);
		return workbook;
	}
	
	public Workbook handleWorkbook(Workbook workbook,int sheetIndex,Map<Integer, Map<Integer,String>> sheetData) throws InvalidFormatException, IOException{
		Sheet sheet = ExcelPoiKit.getSheet(sheetIndex, workbook);
		handleSheet(workbook,sheet,sheetData);
		return workbook;
	}
	
	/**
	 * 导出excel有各种各样的导出需求，这里实现一些示例，因为需求各不相同
	 * 有一个模板excel
	 * 指明某个excel的某个sheet，如果为空，默认第一个
	 * 数据格式：Map<Integer, Map<Integer,String>> 行号：<列号：字符串>
	 * @throws IOException 
	 * @throws InvalidFormatException
	 */
	public Workbook handleWorkbook(InputStream templateInput,String sheetName,Map<Integer, Map<Integer,String>> sheetData) throws InvalidFormatException, IOException{
		Workbook workbook = WorkbookFactory.create(templateInput);
		Sheet sheet = ExcelPoiKit.getSheet(sheetName, workbook);
		handleSheet(workbook,sheet,sheetData);
		return workbook;
	}
	
	public Workbook handleWorkbook(InputStream templateInput,int sheetIndex,Map<Integer, Map<Integer,String>> sheetData) throws InvalidFormatException, IOException{
		Workbook workbook = WorkbookFactory.create(templateInput);
		Sheet sheet = ExcelPoiKit.getSheet(sheetIndex, workbook);
		handleSheet(workbook,sheet,sheetData);
		return workbook;
	}
	
	private void handleSheet(Workbook workbook,Sheet sheet,Map<Integer, Map<Integer,String>> sheetData) throws InvalidFormatException, IOException{
		for (Map.Entry<Integer, Map<Integer,String>> sheetEntry : sheetData.entrySet()) {
			Integer rowNum = sheetEntry.getKey();
			Map<Integer,String> rowData = sheetEntry.getValue();
			
			for (Map.Entry<Integer, String> rowEntry : rowData.entrySet()) {
				Integer colNum = rowEntry.getKey();
				Cell cell = sheet.getRow(rowNum).getCell(colNum);
				if(cell != null){
					handleCell(workbook,cell,rowEntry.getValue());
				}
			}
		}
	}
	
	//这里是个人特殊需求，也可定义接口或继承覆盖，不过使得程序更复杂了
	private void handleCell(Workbook workbook,Cell cell,String newData){
		CellStyle style = workbook.createCellStyle(); 
		Font font = workbook.createFont();
        font.setColor(HSSFColor.RED.index);
        style.setFont(font);
		
		String oldCellData = ExcelPoiKit.getCellVal(cell);
		cell.setCellValue(oldCellData + "(错误：" + newData + ")");//也可以加一些样式处理
		cell.setCellStyle(style);
	}

}
