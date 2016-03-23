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

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StrKit.
 */
public class StrKit {
	
	private StrKit(){

    }

	/**
	 * 首字母变小写
     * <p>the first char to lower<br>
	 * @param str the want to change string
	 * @return return changed string
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
     * <p>the first char to upper<br>
     * @param str the want to change string
     * @return return changed string
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
     * @param str judge words
     * @return if str is null or "" return true,else false
     */
	public static boolean isBlank(String str) {
		return StringUtils.isBlank(str);
		//return str == null || "".equals(str.trim());
	}
	
	/**
	 * 如果其中一个字符串为 null 或者为  "" 时返回 true
     * @param strings judge words
     * @return if one str is null or "" return true,else false
	 */
	public static boolean isBlank(String... strings) {
		if (strings == null) {
            return true;
        }
		for (String str : strings) {
            if (isBlank(str)) {
                return true;
            }
        }
		return false;
	}
	
	/**
	 * 字符串不为 null 而且不为  "" 时返回 true
	 */
	public static boolean notBlank(String str) {
		return StringUtils.isNotBlank(str);
	}

    /**
     * 所有字符串不为 null 而且不为  "" 时返回 true
     */
	public static boolean notBlank(String... strings) {
		if (strings == null) {
			return false;
		}
		for (String str : strings) {
            if (isBlank(str)) {
                return false;
            }
        }
		return true;
	}

    /**
     * 所有字符串为 null 时返回 true
     */
    public static boolean isNull(Object... paras) {
        if (paras == null) {
            return true;
        }
        for (Object obj : paras) {
            if (obj == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 所有字符串不为 null 时返回 true
     */
	public static boolean notNull(Object... paras) {
		if (paras == null) {
            return false;
        }
		for (Object obj : paras) {
            if (obj == null) {
                return false;
            }
        }
		return true;
	}

    /**
     *  to utf8 string
     *  js可以用decodeURI(msg)反向解码
     */
	public static String toUtf8String(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c <= 255) {
				sb.append(c);
			}else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				}catch (Exception ex) {
					b = new byte[0];
				}

                for (byte aB : b) {
                    int k = aB;
                    if (k < 0) {
                        k += 256;
                    }
                    sb.append("%").append(Integer.toHexString(k).toUpperCase());
                }
			}
		}
		return sb.toString();
	}

    /**
     * 获取字符串对象，为Null或者为空字符串,返回""
     * 可能会有空白字符产生
     */
	public static String getStr(Object obj){
		return getStr(obj,"");
	}
	
	/**
	 * 获取字符串对象，为Null或者为空字符串,返回defaultString
     * 可能会有空白字符产生
	 */
	public static String getStr(Object obj,String defaultStr){
		return (obj == null || isBlank(obj.toString())) ? defaultStr : obj.toString();
	}

    /**
     * 获取字符串对象，为Null或者为空字符串,返回""，并且会trim
     */
	public static String getStrAndTrim(Object obj){
		return getStrAndTrim(obj,"");
	}

    /**
     * 获取字符串对象，为Null或者为空字符串,返回defaultString，并且会trim
     */
	public static String getStrAndTrim(Object obj,String defaultStr){
		return (obj == null || isBlank(obj.toString())) ? defaultStr.trim() : obj.toString().trim();
	}

    /**
     *  多个相同的字符替换：比如将其中所有的问号，挨个换成数组内的值
     */
	public static String replace(String str,String fix,String[] array){
        String rs = str;
        for (String anArray : array) {
            rs = rs.replaceFirst(fix, anArray);
        }
		return rs;
	}

    /**
     * 对所有的问号替换
     */
	public static String replace(String str,String[] array){
		String rs = str;
		rs = StrKit.replace(rs,"\\?",array);
		return rs;
	}



    /**
     * 是否包含中文
     */
	public static boolean containsChineseChar(String str){
       boolean temp = false;
       Pattern p = Pattern.compile("[\u4e00-\u9fa5]"); 
       Matcher m = p.matcher(str); 
       if(m.find()){
           temp = true;
       }
       return temp;
	}

    /**
     * 将所有对象中不为空的第一个字符串筛选出来，如果都为空，返回""
     */
	public static String getNotNullFirstStr(String... paras) {
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

    /**
     *  从List中 拼接字符串 fix做结合
     */
	public static String getStrs(List<String> strList,String fix){
		StringBuilder sb = new StringBuilder();
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

    /**
     * 增加一个方法，处理返回的对象，形成String
     */
	public static String getStrByObj(Object obj,String dateFormat){
		String rs = "";
		
		if(obj == null){
			return rs;
		}
		
		if(obj instanceof BigDecimal){
            BigDecimal bigDecimal = ((BigDecimal)obj);
            rs = bigDecimal.toString();
		}else if(obj instanceof java.sql.Date
				|| obj instanceof java.sql.Time
				|| obj instanceof java.sql.Timestamp
				|| obj instanceof java.util.Date){
			rs = DateKit.toStr(DateKit.transDate(obj),dateFormat);
		}else{
			rs = obj.toString();
		}

		return rs;
	}

    /**
     *  增加一个方法，处理返回的对象，形成String
     */
	public static String getStrByObj(Object obj){
		return getStrByObj(obj,DateKit.dateFormat);
	}
}




