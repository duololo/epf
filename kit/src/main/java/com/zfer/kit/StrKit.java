/**
 * Copyright (c) 2011-2015
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zfer.kit;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StrKit.
 *
 * @author yunshan
 * @version 1.0
 */
public class StrKit {

    /**
     * init.
     */
    private StrKit() {
        super();
    }

    /**
     * 首字母变小写.
     * <p>the first char to lower.<br>
     *
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
     * 首字母变大写.
     * <p>the first char to upper.<br>
     *
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
     * 字符串为 null 或者为  "" 时返回 true.
     *
     * @param str judge words
     * @return if str is null or "" return true,else false
     */
    public static boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }

    /**
     * 如果其中一个字符串为 null 或者为  "" 时返回 true.
     *
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
     * 字符串不为 null 而且不为  "" 时返回 true.
     *
     * @param str judge words
     * @return 字符串不为 null 而且不为  "" 时返回 true
     */
    public static boolean notBlank(String str) {
        return StringUtils.isNotBlank(str);
    }

    /**
     * 所有字符串不为 null 而且不为  "" 时返回 true.
     *
     * @param strings judge words
     * @return 所有字符串不为 null 而且不为  "" 时返回 true
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
     * 所有对象为 null 时返回 true.
     *
     * @param paras judge objects
     * @return 所有对象为 null 时返回 true
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
     * 所有对象不为 null 时返回 true.
     *
     * @param paras judge objects
     * @return 所有对象不为 null 时返回 true
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
     * to utf8 string.
     * js可以用decodeURI(msg)反向解码.
     *
     * @param str want to change word
     * @return utf8 string of str
     */
    public static String toUtf8String(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt <= 255) {
                sb.append(charAt);
            } else {
                byte[] bytes;
                try {
                    bytes = Character.toString(charAt).getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    bytes = new byte[0];
                }

                for (byte ab : bytes) {
                    int abk = ab;
                    if (abk < 0) {
                        abk += 256;
                    }
                    sb.append("%").append(Integer.toHexString(abk).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    /**
     * 获取字符串对象，为Null或者为空字符串,返回"".
     * 可能会有空白字符产生.
     *
     * @param obj input param
     * @return string of obj
     */
    public static String getStr(Object obj) {
        return getStr(obj, "");
    }

    /**
     * 获取字符串对象，为Null或者为空字符串,返回defaultString.
     * 可能会有空白字符产生.
     *
     * @param obj input param
     * @param defaultStr input param
     * @return string of obj,if is blank return defaultStr
     */
    public static String getStr(Object obj, String defaultStr) {
        return (obj == null || isBlank(obj.toString())) ? defaultStr : obj.toString();
    }

    /**
     * 获取字符串对象，为Null或者为空字符串,返回""，并且会trim.
     *
     * @param obj input param
     * @return string of obj,if is blank return defaultStr and trim
     */
    public static String getStrAndTrim(Object obj) {
        return getStrAndTrim(obj, "");
    }

    /**
     * 获取字符串对象，为Null或者为空字符串,返回defaultString，并且会trim.
     *
     * @param obj input param
     * @param defaultStr param
     * @return string of obj,if is blank return defaultStr and trim
     */
    public static String getStrAndTrim(Object obj, String defaultStr) {
        return (obj == null || isBlank(obj.toString())) ? defaultStr.trim() : obj.toString().trim();
    }

    /**
     * 多个相同的字符替换：比如将其中所有的问号，挨个换成数组内的值.
     *
     * @param str input param
     * @param fix input param
     * @param array input param
     * @return replace str width array[i] and append by fix
     */
    public static String replace(String str, String fix, String[] array) {
        String rs = str;
        for (String anArray : array) {
            rs = rs.replaceFirst(fix, anArray);
        }
        return rs;
    }

    /**
     * 对所有的问号替换.
     *
     * @param str input param
     * @param array input param
     * @return replace str width array[i] and append by ?
     */
    public static String replace(String str, String[] array) {
        String rs = str;
        rs = StrKit.replace(rs, "\\?", array);
        return rs;
    }


    /**
     * 是否包含中文.
     *
     * @param str input param
     * @return if contains Chinese Char return true,else false
     */
    public static boolean containsChineseChar(String str) {
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }

    /**
     * 将所有对象中不为空的第一个字符串筛选出来，如果都为空，返回"".
     *
     * @param paras input param
     * @return first not null string
     */
    public static String getNotNullFirstStr(String... paras) {
        String rs = "";
        if (paras == null) {
            return rs;
        }
        for (String str : paras) {
            if (notBlank(str)) {
                rs = str;
                break;
            }
        }
        return rs;
    }

    /**
     * 从List中 拼接字符串 fix做结合.
     *
     * @param strList input param
     * @param fix input param
     * @return return string of list append by fix
     */
    public static String getSplitStr(List<String> strList, String fix) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strList.size(); i++) {
            String str = strList.get(i);
            if (StrKit.notBlank(str)) {
                sb.append(str);
                if (i + 1 != strList.size()) {
                    sb.append(fix);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 增加一个方法，处理返回的对象，形成String.
     *
     * @param obj input param
     * @param dateFormat input param
     * @return return str by obj
     */
    public static String getStrByObj(Object obj, String dateFormat) {
        String rs = "";

        if (obj == null) {
            return rs;
        }

        if (obj instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal) obj;
            rs = bigDecimal.toString();
        } else if (obj instanceof java.sql.Date
                || obj instanceof java.sql.Time
                || obj instanceof java.sql.Timestamp
                || obj instanceof java.util.Date) {
            Date dateObj = (Date) DateKit.transDateObj2UtilDate(obj);
            rs = DateKit.toStr(dateObj, dateFormat);
        } else {
            rs = obj.toString();
        }

        return rs;
    }

    /**
     * 增加一个方法，处理返回的对象，形成String.
     *
     * @param obj input param
     * @return return str by obj
     */
    public static String getStrByObj(Object obj) {
        return getStrByObj(obj, DateKit.DATE_TIME_FORMAT);
    }
}