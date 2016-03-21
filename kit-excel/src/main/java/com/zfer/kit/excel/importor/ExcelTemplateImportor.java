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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.zfer.kit.excel.ExcelPoiKit;


/*
 * Template Excel Importor
 */
public class ExcelTemplateImportor<T> extends ExcelAbstractImportor<T> {
	
	@Override
	public void initConfig() throws Exception {

		ExcelPoiKit excelKit = new ExcelPoiKit();
		excelKit.handleExcel("", getTemplateExcelInputStream());
		Sheet sheet = excelKit.getSheet();//ExcelPoiKit.getSheet("", getTemplateExcelInputStream());
		if(sheet == null) return;
		int rows = sheet.getPhysicalNumberOfRows();
		if(rows <= 0) return;
		
		//1.get 0 row,get colNum - FieldName Map
		Map<Integer, String> colNumAndFieldNameMap = ExcelPoiKit.getColNumAndValMap(sheet.getRow(0));
		super.setColNumAndFieldNameMap(colNumAndFieldNameMap);
		
		//2.get 1 row,get colNum - FieldDisplayName Map
		Map<Integer, String> colNumAndFieldDisplayNameMap = ExcelPoiKit.getColNumAndValMap(sheet.getRow(1));
		super.setColNumAndFieldDisplayNameMap(colNumAndFieldDisplayNameMap);
		
		//3.get 2 row,get required value Fields  List
		Row validateRequiredFieldRow = sheet.getRow(2);
		List<Integer> requiredFieldsList = new ArrayList<Integer>();
		Map<Integer, String> colNumAndRequiredValMap = ExcelPoiKit.getColNumAndValMap(validateRequiredFieldRow);
		for (Map.Entry<Integer, String> entry : colNumAndRequiredValMap.entrySet()) {
			if("required".equals(entry.getValue())){
				requiredFieldsList.add(entry.getKey());
			}
		}
		super.setRequiredFieldsList(requiredFieldsList);
		
		//4.get 3 row,get colNum - value regex
		Map<Integer, String> colNumAndFieldValRegexMap = ExcelPoiKit.getColNumAndValMap(sheet.getRow(3));
		super.setColNumAndFieldValRegexMap(colNumAndFieldValRegexMap);
		
	}
}