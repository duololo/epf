package com.zfer.kit.excel.importor;

import java.util.Map;

public class ExcelImportSheetErrorMsg {
	public static final String emptyErrorMsg = "此处不能为空，请填写";
	public static final String invalidErrorMsg = "此处格式不符合，请修改";
	
	public ExcelImportSheetErrorMsg(){
		super();
	}
	
	public ExcelImportSheetErrorMsg(Map<Integer, Map<Integer,String>> excelImportRowErrorMsgMap) {
		super();
		this.setExcelImportRowErrorMsgMap(excelImportRowErrorMsgMap);
	}
	
	private Map<Integer, Map<Integer,String>> excelImportRowErrorMsgMap;

	public Map<Integer, Map<Integer,String>> getExcelImportRowErrorMsgMap() {
		return excelImportRowErrorMsgMap;
	}

	public void setExcelImportRowErrorMsgMap(
			Map<Integer, Map<Integer,String>> excelImportRowErrorMsgMap) {
		this.excelImportRowErrorMsgMap = excelImportRowErrorMsgMap;
	}

}
