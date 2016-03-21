package com.zfer.kit.excel.importor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfer.kit.BeanReflectKit;
import com.zfer.kit.excel.ExcelPoiKit;
/*
 *  annotion excel importor 
 */
public class ExcelAnnotionImportor<T> extends ExcelAbstractImportor<T> {
	
	@Override
	public void initConfig() throws Exception {
		Map<Integer, String> colNumAndFieldNameMap = new HashMap<Integer, String>();
		List<Field> fieldList = BeanReflectKit.getClassFieldList(getExcelEntityClass(), null);
		for(Field field : fieldList){
			if (field.isAnnotationPresent(ExcelVOAnnotation.class)) {
				ExcelVOAnnotation attr = field.getAnnotation(ExcelVOAnnotation.class);
				int colNum = ExcelPoiKit.getExcelCol(attr.column());
				colNumAndFieldNameMap.put(colNum, field.getName());
			}
		}
		setColNumAndFieldNameMap(colNumAndFieldNameMap);
		
	}
	
}