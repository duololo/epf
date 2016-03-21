package com.zfer.kit.excel;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.NumberToTextConverter;

import com.zfer.kit.StrKit;


public class ExcelPoiKit {
	
	private Workbook workbook;
	private Sheet sheet;
	
	public void handleExcel(String sheetName, InputStream inputStream)
			throws IOException, InvalidFormatException{
		workbook = WorkbookFactory.create(inputStream); 
		sheet = getSheet(sheetName,workbook);
	}
	
	public void handleExcel(int sheetIndex, InputStream inputStream)
			throws IOException, InvalidFormatException{
		workbook = WorkbookFactory.create(inputStream); 
		sheet = getSheet(sheetIndex,workbook);
	}
	
//	private Sheet getSheet(String sheetName, InputStream inputStream)
//			throws IOException, InvalidFormatException {
//		Workbook workbook = WorkbookFactory.create(inputStream); 
//		return getSheet(sheetName,workbook);
//	}
//	
//	private Sheet getSheet(int sheetIndex, InputStream inputStream)
//			throws IOException, InvalidFormatException {
//		Workbook workbook = WorkbookFactory.create(inputStream); 
//		return getSheet(sheetIndex,workbook);
//	}
	
	/**
	 * get sheet
	 * @param sheetName
	 * @param workbook
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static Sheet getSheet(String sheetName, Workbook workbook)
			throws IOException, InvalidFormatException {
		Sheet sheet = null;
		if (StrKit.notBlank(sheetName)) {
			sheet = workbook.getSheet(sheetName);
		}
		if (sheet == null) {
			sheet = workbook.getSheetAt(0);
		}
		return sheet;
	}
	
	public static Sheet getSheet(int sheetIndex, Workbook workbook)
			throws IOException, InvalidFormatException {
		Sheet sheet = workbook.getSheetAt(sheetIndex);
		if (sheet == null) {
			sheet = workbook.getSheetAt(0);
		}
		return sheet;
	}
	
	/**
	 * replace excel's A,B,C... to 0,1,2,3
	 * @param col
	 */
	public static int getExcelCol(String col) {
		col = col.toUpperCase();
		int count = -1;//from -1
		char[] cs = col.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
		}
		return count;
	}
	
	/**
	 * get cell value
	 * @param cell
	 * @return String
	 */
	public static String getCellVal(Cell cell){
		if(cell == null){
			return null;
		}
		String result = new String();
		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC://number
				if (HSSFDateUtil.isCellDateFormatted(cell)) {// handle dateformate
					SimpleDateFormat sdf = null;
					if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
						sdf = new SimpleDateFormat("HH:mm");
					} else {
						sdf = new SimpleDateFormat("yyyy-MM-dd");
					}
					Date date = cell.getDateCellValue();
					result = sdf.format(date);
				} else if (cell.getCellStyle().getDataFormat() == 58) {
					// handle special dateformat id=58
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					double value = cell.getNumericCellValue();
					Date date = org.apache.poi.ss.usermodel.DateUtil
							.getJavaDate(value);
					result = sdf.format(date);
				} else {
//					double value = cell.getNumericCellValue();
//					CellStyle style = cell.getCellStyle();
//					DecimalFormat format = new DecimalFormat();
//					String temp = style.getDataFormatString();
//					// format set general
//					if (temp.equals("General")) {
//						format.applyPattern("#");
//					}
//					result = format.format(value);
					result = NumberToTextConverter.toText(cell.getNumericCellValue());  
				}
				break;
			case HSSFCell.CELL_TYPE_STRING:
				result = cell.getRichStringCellValue().toString();
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				result = String.valueOf(cell.getBooleanCellValue());
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				result = "";
			default:
				result = "";
				break;
		}
		return result;
	}
	
	/**
	 * get row value 
	 * @return Map<Integer colNum,Object cellValue>
	 */
	public static Map<Integer,String> getColNumAndValMap(Row row){
		Map<Integer, String> colNumAndValMap = new HashMap<Integer, String>();
		if(row == null) return colNumAndValMap;
		for (int colNum = 0; colNum < row.getLastCellNum(); colNum++) {
			Cell cell = row.getCell(colNum);
			String cellVal = ExcelPoiKit.getCellVal(cell);
			colNumAndValMap.put(colNum,cellVal);
		}
		return colNumAndValMap;
	}

	public Workbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}
	
	
}
