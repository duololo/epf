/**
 * Copyright (c) 2016-2020, 济南-云山 (77079588@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zfer.kit.excel.importor;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.zfer.kit.BeanReflectKit;
import com.zfer.kit.StrKit;
import com.zfer.kit.excel.ExcelPoiKit;


/*
 * abstract excel importor 
 */
public abstract class ExcelAbstractImportor<T> {
	
	private Class<T> excelEntityClass;

	/**
	 * readExcelValueStartRowNum default 1,0 is head
	 */
	private Integer readExcelValueStartRowNum = 1;
	
	//trim space string
	private boolean trimValue;
	
	//template file
	private InputStream templateExcelInputStream;
	
	//colNum - fieldName
	private Map<Integer, String> colNumAndFieldNameMap;
	
	//colNum - display name
	private Map<Integer, String> colNumAndFieldDisplayNameMap;
	
	//requiredFieldsList 
	private List<Integer> requiredFieldsList;
	
	//colNum - fieldValueRegex
	private Map<Integer, String> colNumAndFieldValRegexMap;
	
	//sheet errors 如果校验过程中出现错误，则不抛出异常，所有的错误反映到该对象上
	private ExcelImportSheetErrorMsg excelImportSheetErrorMsg;
	
	//返回的正确的excel数据
	private List<T> excelRightDataList = new ArrayList<T>();
	
	//返回的所有的excel数据，包含正确 错误的
	private List<T> excelAllDataList = new ArrayList<T>();
	
	//返回的输入的workbook对象
	private Workbook dataWorkbook;
	
	public void importExcel(int sheetIndex, InputStream inputStream) throws Exception {
		ExcelPoiKit excelKit = new ExcelPoiKit();
		excelKit.handleExcel(sheetIndex,inputStream);
		Sheet sheet = excelKit.getSheet();//Sheet sheet = ExcelPoiKit.getSheet(sheetIndex, inputStream);
		dataWorkbook = excelKit.getWorkbook();
		importExcel(sheet, inputStream);
	}
	
	public void importExcel(String sheetName, InputStream inputStream) throws Exception {
		ExcelPoiKit excelKit = new ExcelPoiKit();
		excelKit.handleExcel(sheetName, inputStream);
		Sheet sheet = excelKit.getSheet();//Sheet sheet = ExcelPoiKit.getSheet(sheetName, inputStream);
		dataWorkbook = excelKit.getWorkbook();
		importExcel(sheet, inputStream);
	}
	
	public void importExcel(Sheet sheet, InputStream inputStream) throws Exception {
		if(sheet == null) return;
		int rows = sheet.getPhysicalNumberOfRows();
		if(rows <= 0) return;
		
		initConfig();//abstract to impl
		
		Map<Integer,Map<Integer,String>> rowNumAndRowErrorMsgMap = new HashMap<Integer,Map<Integer,String>>();//每行对应的列错误信息对象
		for (int i = readExcelValueStartRowNum; i < rows; i++) {//default 2,1 is head
			Row row = sheet.getRow(i);
			if(row == null){continue;}
			
			Map<Integer,String> colNumAndCellValMap = new HashMap<Integer,String>();
			Map<Integer,String> rowError = new HashMap<Integer,String>();
			for (int cellColNum = 0; cellColNum < getMaxColNum(colNumAndFieldNameMap)+1; cellColNum++) {
				Cell cell = row.getCell(cellColNum);
				String cellVal = ExcelPoiKit.getCellVal(cell);
				
				if(trimValue)
					cellVal = StrKit.getStrTrim(cellVal);
				
				//validate 1
				if(requiredFieldsList != null && requiredFieldsList.contains(cellColNum) && StrKit.isBlank(cellVal)){
					rowError.put(cellColNum,ExcelImportSheetErrorMsg.emptyErrorMsg);
				}
				
				//validate 2 
				if(colNumAndFieldValRegexMap != null && colNumAndFieldValRegexMap.size() != 0){
					String regex = colNumAndFieldValRegexMap.get(cellColNum);
					if(StrKit.notBlank(regex,cellVal)){
						Pattern r = Pattern.compile(regex);
						Matcher m = r.matcher(cellVal);
						boolean regexrs = m.matches();
						if(!regexrs){
							rowError.put(cellColNum,ExcelImportSheetErrorMsg.invalidErrorMsg);
						}
					}
				}
				
				colNumAndCellValMap.put(cellColNum, cellVal);
			}
			
			//在校验无错误情况下
			if(rowError.isEmpty()){
				T entity = setEntityFieldVal(colNumAndFieldNameMap,colNumAndCellValMap);
				if(entity != null) excelRightDataList.add(entity);
			}else{
				rowNumAndRowErrorMsgMap.put(i, rowError);
			}
			
			//将所有数据放入，可能因为格式的问题，设置对象有异常
			try{
				T entity = setEntityFieldVal(colNumAndFieldNameMap,colNumAndCellValMap);
				if(entity != null) excelAllDataList.add(entity);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		excelImportSheetErrorMsg = new ExcelImportSheetErrorMsg(rowNumAndRowErrorMsgMap);
	}
	
	//init bean entity value from map values
	private T setEntityFieldVal(Map<Integer, String> colNumAndFieldNameMap,Map<Integer,String> colNumAndCellValMap) throws InstantiationException, IllegalAccessException, NumberFormatException, IllegalArgumentException, InvocationTargetException, ParseException{
		T entity = null;
		entity = excelEntityClass.newInstance();
		Map<String,String> fieldNameAndValueMap = new HashMap<String,String>();
		for (Map.Entry<Integer,String> entry : colNumAndCellValMap.entrySet()) {
			Integer colNum = entry.getKey();
			fieldNameAndValueMap.put(colNumAndFieldNameMap.get(colNum), colNumAndCellValMap.get(colNum));
		}
		entity = BeanReflectKit.setBeanFieldVal(entity, fieldNameAndValueMap);
		return entity;
	}
	
	//get max colNum
	private int getMaxColNum(Map<Integer, String> colNumAndFieldNameMap){
		int maxColNum = 0;
		for (Map.Entry<Integer, String> entry : colNumAndFieldNameMap.entrySet()) {
			maxColNum = Math.max(maxColNum, entry.getKey());
		}
		return maxColNum;
	}
	
	/**
	 * init config
	 * @return 
	 * @return
	 * @throws Exception
	 */
	public abstract void initConfig() throws Exception;


	
	
	
	
	

	public Class<T> getExcelEntityClass() {
		return excelEntityClass;
	}

	public void setExcelEntityClass(Class<T> excelEntityClass) {
		this.excelEntityClass = excelEntityClass;
	}
	
	public Map<Integer, String> getColNumAndFieldNameMap() {
		return colNumAndFieldNameMap;
	}

	public void setColNumAndFieldNameMap(Map<Integer, String> colNumAndFieldNameMap) {
		this.colNumAndFieldNameMap = colNumAndFieldNameMap;
	}

	public List<Integer> getRequiredFieldsList() {
		return requiredFieldsList;
	}

	public void setRequiredFieldsList(List<Integer> requiredFieldsList) {
		this.requiredFieldsList = requiredFieldsList;
	}

	public Integer getReadExcelValueStartRowNum() {
		return readExcelValueStartRowNum;
	}

	public void setReadExcelValueStartRowNum(Integer readExcelValueStartRowNum) {
		this.readExcelValueStartRowNum = readExcelValueStartRowNum;
	}

	public InputStream getTemplateExcelInputStream() {
		return templateExcelInputStream;
	}

	public void setTemplateExcelInputStream(InputStream templateExcelInputStream) {
		this.templateExcelInputStream = templateExcelInputStream;
	}

	public Map<Integer, String> getColNumAndFieldDisplayNameMap() {
		return colNumAndFieldDisplayNameMap;
	}

	public void setColNumAndFieldDisplayNameMap(
			Map<Integer, String> colNumAndFieldDisplayNameMap) {
		this.colNumAndFieldDisplayNameMap = colNumAndFieldDisplayNameMap;
	}

	public Map<Integer, String> getColNumAndFieldValRegexMap() {
		return colNumAndFieldValRegexMap;
	}

	public void setColNumAndFieldValRegexMap(
			Map<Integer, String> colNumAndFieldValRegexMap) {
		this.colNumAndFieldValRegexMap = colNumAndFieldValRegexMap;
	}

	public ExcelImportSheetErrorMsg getExcelImportSheetErrorMsg() {
		return excelImportSheetErrorMsg;
	}

	public void setExcelImportSheetErrorMsg(ExcelImportSheetErrorMsg excelImportSheetErrorMsg) {
		this.excelImportSheetErrorMsg = excelImportSheetErrorMsg;
	}

	public List<T> getExcelRightDataList() {
		return excelRightDataList;
	}

	public void setExcelRightDataList(List<T> excelRightDataList) {
		this.excelRightDataList = excelRightDataList;
	}

	public List<T> getExcelAllDataList() {
		return excelAllDataList;
	}

	public void setExcelAllDataList(List<T> excelAllDataList) {
		this.excelAllDataList = excelAllDataList;
	}

	public Workbook getDataWorkbook() {
		return dataWorkbook;
	}

	public void setDataWorkbook(Workbook dataWorkbook) {
		this.dataWorkbook = dataWorkbook;
	}

	public boolean isTrimValue() {
		return trimValue;
	}

	public void setTrimValue(boolean trimValue) {
		this.trimValue = trimValue;
	}
}