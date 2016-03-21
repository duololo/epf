/**
 * Copyright (c) 2011-2015
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StrKit.
 */
public class StrKit {
	
	private StrKit(){}
	
	/**
	 * 首字母变小写
	 */
	public static String firstCharToLowerCase(String str) {
		char firstChar = str.charAt(0);
		if (firstChar >= 'A' && firstChar <= 'Z') {
			char[] arr = str.toCharArray();
			arr[0] += ('a' - 'A');
			return new String(arr);
		}
		return str;
	}
	
	/**
	 * 首字母变大写
	 */
	public static String firstCharToUpperCase(String str) {
		char firstChar = str.charAt(0);
		if (firstChar >= 'a' && firstChar <= 'z') {
			char[] arr = str.toCharArray();
			arr[0] -= ('a' - 'A');
			return new String(arr);
		}
		return str;
	}
	
	/**
	 * 字符串为 null 或者为  "" 时返回 true
	 */
	public static boolean isBlank(String str) {
		return str == null || "".equals(str.trim());
	}
	
	/**
	 * 如果其中一个字符串为 null 或者为  "" 时返回 true
	 */
	public static boolean isBlank(String... strings) {
		if (strings == null)
			return true;
		for (String str : strings)
			if (str == null || "".equals(str.trim()))
				return true;
		return false;
	}
	
	/**
	 * 字符串不为 null 而且不为  "" 时返回 true
	 */
	public static boolean notBlank(String str) {
		return !(str == null || "".equals(str.trim()));
	}
	
	public static boolean notBlank(String... strings) {
		if (strings == null)
			return false;
		for (String str : strings)
			if (str == null || "".equals(str.trim()))
				return false;
		return true;
	}
	
	public static boolean notNull(Object... paras) {
		if (paras == null)
			return false;
		for (Object obj : paras)
			if (obj == null)
				return false;
		return true;
	}
	
	public static boolean isNull(Object... paras) {
		if (paras == null)
			return true;
		for (Object obj : paras)
			if (obj == null)
				return true;
		return false;
	}
	
	//toutf8string，js可以用decodeURI(msg)反向解码
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			}
			else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				}
				catch (Exception ex) {
					b = new byte[0];
				}

				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}
	
	//获取字符串对象，为Null或者为空字符串,返回""
	public static String getStr(Object obj){
		return getStr(obj,"");
	}
	
	//获取字符串对象，为Null或者为空字符串,返回defaultString
	public static String getStr(Object obj,String defaultStr){
		return (obj == null || "".equals(obj.toString().trim())) ? defaultStr : obj.toString();
	}
	
	/* 可能会有空白字符产生 */
	public static String getStrTrim(Object obj){
		return getStrTrim(obj,"");
	}
	public static String getStrTrim(Object obj,String defaultStr){
		return (obj == null || "".equals(obj.toString().trim())) ? defaultStr : obj.toString().trim();
	}
	
	//多个相同的字符替换：比如将其中所有的问号，挨个换成数组内的值
	public static String replace(String str,String fix,String[] array){
		for(int i=0;i<array.length;i++){
			str = str.replaceFirst(fix, array[i]);
		}
		return str;
	}
	
	//对所有的问号替换
	public static String replace(String str,String[] array){
		str = StrKit.replace(str,"\\?",array);
		return str;
	}
	
	public static String replaceSql(String str,String[] array){
		for(int i=0;i<array.length;i++){
			str = str.replaceFirst("\\?", "'" + array[i] + "'");
		}
		return str;
	}
	
	
	//是否包含中文
	public static boolean containsChineseChar(String str){
       boolean temp = false;
       Pattern p = Pattern.compile("[\u4e00-\u9fa5]"); 
       Matcher m = p.matcher(str); 
       if(m.find()){ 
           temp = true;
       }
       return temp;
	}
	
	//将所有对象中不为空的第一个字符串筛选出来，如果都为空，返回""
	public static String getNotNullStr(String... paras) {
		String rs = "";
		if (paras == null){
			return rs;
		}
		for (String str : paras){
			if (notBlank(str)){
				rs = str;
				break;
			}
		}
		return rs;
	}
	
	//从List中 拼接字符串 fix做结合
	public static String getStrs(List<String> strList,String fix){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<strList.size();i++){
			String str = strList.get(i);
			if(StrKit.notBlank(str)){
				sb.append(str);
				if(i+1 != strList.size()){
					sb.append(fix);
				}
			}
		}
		return sb.toString();
	}
	
	//增加一个方法，处理返回的对象，形成String
	public static String getStrByObj(Object obj,String dateFormat){
		String rs = "";
		
		if(obj == null){
			return rs;
		}
		
		if(obj instanceof BigDecimal){
			rs = ((BigDecimal)obj).toString();
		}
		else if(obj instanceof java.sql.Date 
				|| obj instanceof java.util.Date
				|| obj instanceof java.sql.Time
				|| obj instanceof java.sql.Timestamp){
			rs = DateKit.toStr(DateKit.transDate(obj),dateFormat);;
		}
		else{
			rs = obj.toString();
		}
		
		return rs;
	}
	
	//增加一个方法，处理返回的对象，形成String
	public static String getStrByObj(Object obj){
		return getStrByObj(obj,DateKit.dateFormat);
	}
	
	public static void main(String[] args) {
		String str = "select code,?,? from aa where code=?";
		String str11 = StrKit.replace(str, new String[]{"222","333","444"});
		System.out.println(str11);
		
		List<String> strList = new ArrayList<String>();
		strList.add("ssss3");
		String strs = StrKit.getStrs(strList, ";");
		System.out.println(strs);
	}
}




