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
package com.zfer.kit;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BeanReflectKit {
	
	/**
	 * 得到实体类所有通过注解映射了数据表的字段
	 * @param clazz 类对象
	 * @param fields 调用传入null
	 * @return
	 */
	public static List<Field> getClassFieldList(Class<?> clazz, List<Field> fields) {
		List<Field> rsFieldList = fields;
		if (rsFieldList == null) {
			rsFieldList = new ArrayList<Field>();
		}
		Field[] allFields = clazz.getDeclaredFields();// 得到所有定义字段
		for (Field field : allFields) {// 得到所有field并存放到一个list中.
			rsFieldList.add(field);
		}
		if (clazz.getSuperclass() != null
				&& !clazz.getSuperclass().equals(Object.class)) {
			getClassFieldList(clazz.getSuperclass(), rsFieldList);
		}
		return rsFieldList;
	}
	
	/**
	 * 反射字段名称，设置对象
	 * @param beanEntity 实例
	 * @param fieldValueMap 字段名称 字段值的map
	 * @return
	 * @throws NumberFormatException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws ParseException
	 * @throws InstantiationException
	 */
	public static <T> T setBeanFieldVal(T beanEntity,Map<String,String> fieldValueMap) throws NumberFormatException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, ParseException, InstantiationException{
		Method[] methods = beanEntity.getClass().getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.startsWith("set") == false)	// only setter method
				continue;
			Class<?>[] types = method.getParameterTypes();
			if (types.length != 1)						// only one parameter
				continue;
			
			if(JavaTypeKit.judgeBasicType(types[0])){ //判断是否基本类型
				String attrName = methodName.substring(3);
				String value = fieldValueMap.get(StrKit.firstCharToLowerCase(attrName));
				if (value != null) {
					method.invoke(beanEntity, JavaTypeKit.convert(types[0], value));
				}
			}else{
				if(types[0].toString().startsWith("interface")){//是接口类型
					continue;
				}
				Object newModel = types[0].newInstance();
				newModel = setBeanFieldVal(newModel, fieldValueMap);
				if (newModel != null) {
					method.invoke(beanEntity, newModel);
				}
			}
			
		}
		
		return beanEntity;
	}
	
}
