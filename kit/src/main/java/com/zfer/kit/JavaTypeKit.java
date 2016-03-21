package com.zfer.kit;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Convert String to other type object.
 */
public class JavaTypeKit {
	
	private static final int timeStampLen = "2011-01-18 16:18:18".length();
	private static final String timeStampPattern = "yyyy-MM-dd HH:mm:ss";
	private static final String datePattern = "yyyy-MM-dd";
	
	/**
	 * test for all types of mysql
	 * 
	 * 表单提交测试结果:
	 * 1: 表单中的域,就算不输入任何内容,也会传过来 "", 也即永远不可能为 null.
	 * 2: 如果输入空格也会提交上来
	 * 3: 需要考 model中的 string属性,在传过来 "" 时是该转成 null还是不该转换,
	 *    我想, 因为用户没有输入那么肯定是 null, 而不该是 ""
	 * 
	 * 注意: 1:当clazz参数不为String.class, 且参数s为空串blank的情况,
	 *       此情况下转换结果为 null, 而不应该抛出异常
	 *      2:调用者需要对被转换数据做 null 判断，参见 ModelInjector 的两处调用
	 */
	public static final Object convert(Class<?> clazz, String s) throws ParseException {
		// mysql type: varchar, char, enum, set, text, tinytext, mediumtext, longtext
		if (clazz == String.class) {
			return ("".equals(s) ? null : s);	// 用户在表单域中没有输入内容时将提交过来 "", 因为没有输入,所以要转成 null.
		}
		s = s.trim();
		if ("".equals(s)) {	// 前面的 String跳过以后,所有的空字符串全都转成 null,  这是合理的
			return null;
		}
		// 以上两种情况无需转换,直接返回, 注意, 本方法不接受null为 s 参数(经测试永远不可能传来null, 因为无输入传来的也是"")
		
		Object result = null;
		// mysql type: int, integer, tinyint(n) n > 1, smallint, mediumint
		if (clazz == Integer.class || clazz == int.class) {
			result = new BigDecimal(s).intValue();
		}
		// mysql type: bigint
		else if (clazz == Long.class || clazz == long.class) {
			result = new BigDecimal(s).longValue();
		}
		// 经测试java.util.Data类型不会返回, java.sql.Date, java.sql.Time,java.sql.Timestamp 全部直接继承自 java.util.Data, 所以 getDate可以返回这三类数据
		else if (clazz == java.util.Date.class) {
        	if (s.length() >= timeStampLen) {	// if (x < timeStampLen) 改用 datePattern 转换，更智能
        		// Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]
        		// result = new java.util.Date(java.sql.Timestamp.valueOf(s).getTime());	// error under jdk 64bit(maybe)
        		result = new SimpleDateFormat(timeStampPattern).parse(s);
        	}
			else {
				// result = new java.util.Date(java.sql.Date.valueOf(s).getTime());	// error under jdk 64bit
				result = new SimpleDateFormat(datePattern).parse(s);
			}
        }
		// mysql type: date, year
        else if (clazz == java.sql.Date.class) {
        	if (s.length() >= timeStampLen) {	// if (x < timeStampLen) 改用 datePattern 转换，更智能
        		// result = new java.sql.Date(java.sql.Timestamp.valueOf(s).getTime());	// error under jdk 64bit(maybe)
        		result = new java.sql.Date(new SimpleDateFormat(timeStampPattern).parse(s).getTime());
        	}
        	else {
        		// result = new java.sql.Date(java.sql.Date.valueOf(s).getTime());	// error under jdk 64bit
        		result = new java.sql.Date(new SimpleDateFormat(datePattern).parse(s).getTime());
        	}
        }
		// mysql type: time
        else if (clazz == java.sql.Time.class) {
        	result = java.sql.Time.valueOf(s);
		}
		// mysql type: timestamp, datetime
        else if (clazz == java.sql.Timestamp.class) {
        	result = java.sql.Timestamp.valueOf(s);
		}
		// mysql type: real, double
        else if (clazz == Double.class  || clazz == double.class) {
        	result = new BigDecimal(s).doubleValue();
		}
		// mysql type: float
        else if (clazz == Float.class || clazz == float.class) {
        	result = new BigDecimal(s).floatValue();
		}
		// mysql type: bit, tinyint(1)
        else if (clazz == Boolean.class || clazz == boolean.class) {
        	result = Boolean.parseBoolean(s) || "1".equals(s);
		}
		// mysql type: decimal, numeric
        else if (clazz == BigDecimal.class) {
        	result = new BigDecimal(s);
		}
		// mysql type: unsigned bigint
		else if (clazz == BigInteger.class) {
			result = new BigInteger(s);
		}
		// mysql type: binary, varbinary, tinyblob, blob, mediumblob, longblob. I have not finished the test.
        else if (clazz == byte[].class) {
			result = s.getBytes();
		}
		else {
			throw new RuntimeException(clazz.getName() + " can not be converted, please use other type of attributes in your model!");
        }
		
		return result;
	}
	
	
	/**
	 * 基本类型Map对象
	 */
	private static Map<Class<?>,Object> basicTypeValueMap = new HashMap<Class<?>,Object>();
	
	static {  
		basicTypeValueMap.put(int.class, 0);
		basicTypeValueMap.put(Integer.class, new Integer(0));
		basicTypeValueMap.put(BigInteger.class, new BigInteger("0"));
		basicTypeValueMap.put(BigDecimal.class, new BigDecimal("0"));
		
		basicTypeValueMap.put(short.class, 0);
		basicTypeValueMap.put(Short.class, new Short((short) 0));
		
		basicTypeValueMap.put(long.class, 0);
		basicTypeValueMap.put(Long.class, new Long(0l));
		
		basicTypeValueMap.put(float.class, 0.0);
		basicTypeValueMap.put(Float.class, new Float(0.0f));
		
		basicTypeValueMap.put(double.class, 0.0);
		basicTypeValueMap.put(Double.class, new Double(0.0d));
		
		basicTypeValueMap.put(boolean.class, false);
		basicTypeValueMap.put(Boolean.class, new Boolean(false));

		basicTypeValueMap.put(String.class, "");
		
		basicTypeValueMap.put(char.class, ' ');
		basicTypeValueMap.put(Character.class, new Character(' '));
		
		basicTypeValueMap.put(byte[].class, "".getBytes());
		
		basicTypeValueMap.put(java.util.Date.class, new java.util.Date(System.currentTimeMillis()));
		basicTypeValueMap.put(java.sql.Date.class, new java.sql.Date(System.currentTimeMillis()));
		basicTypeValueMap.put(java.sql.Time.class, new java.sql.Time(System.currentTimeMillis()));
		basicTypeValueMap.put(java.sql.Timestamp.class, new java.sql.Timestamp(System.currentTimeMillis()));
	}  
	
	/**
	 * 获取基本属性的初始化值
	 * @param clazz
	 * @return
	 */
	public static Object getBasicTypeInitValue(Class<?> clazz) {
		return basicTypeValueMap.get(clazz);
	}
	
	/**
	 * 判断是否为基本类型
	 * @param clazz
	 * @return
	 */
	public static boolean judgeBasicType(Class<?> clazz) {
		return basicTypeValueMap.containsKey(clazz);
	}
}

