package com.zfer.kit;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 金额工具类
 * 1.金额数字 转大写
 * 2.繁体金额大写 转简体金额
 * 3.金额数字 和 大写（简/繁）是否相等
 */
public final class MoneyKit {

	// 这个类不能实例化
	private MoneyKit() {}

	/**
	 * 大写金额
	 * 
	 * @param str
	 * @return
	 */
	public static String toChinese(String str) {
		return MoneyKit2.toChinese(str);
	}

	/**
	 * 大小写金额比较 支持最小单位：厘
	 * 
	 * @param amount
	 *            小写金额
	 * @param chineseAmount
	 *            大写金额
	 * @return
	 */
	public static boolean isAmtEqChn(String amount, String chineseAmount) {
		if (StrKit.isBlank(chineseAmount)) {
			return false;
		}

		// 格式化大写金额去整
		chineseAmount = chineseAmount.replace("整", "");
		chineseAmount = chineseAmount.replace("正", "");
		chineseAmount = chineseAmount.replace("人民币", "");
		chineseAmount = toSimplified(chineseAmount);
		String toChnAmt = toChinese(amount);
		if (chineseAmount.equals(toChnAmt)) {
			return true;
		}
		return false;
	}

	/**
	 * 大写金额简体化
	 * 
	 * @param chnAmt
	 * @return
	 */
	public static String toSimplified(String chnAmt) {
		if (StrKit.isBlank(chnAmt)) {
			return null;
		}
		char[] amtArrChar = chnAmt.toCharArray();
		Map<String, String> mapping = getSimpToTradMapping();
		for (int i = 0; i < amtArrChar.length; i++) {
			if (mapping.containsKey(String.valueOf(amtArrChar[i]))) {
				amtArrChar[i] = mapping.get(String.valueOf(amtArrChar[i]))
						.charAt(0);
			}
		}
		return String.valueOf(amtArrChar);
	}

	/**
	 * 繁体对应
	 * 
	 * @return Map
	 */
	private static Map<String, String> getSimpToTradMapping() {
		Map<String, String> mapping = new HashMap<String, String>();
		mapping.put("圆", "元");
		mapping.put("圓", "元");
		mapping.put("貳", "贰");
		mapping.put("陸", "陆");
		mapping.put("億", "亿");
		mapping.put("萬", "万");
		return mapping;
	}
	
	
	/**
	 * 格式化金额		
	 * @param num
	 * @return
	 */
	public static String formatMoney(double num) {
		return formatMoney(num,2);
	}
	public static String formatMoney(String str) {
		return formatMoney(str,2);
	}
	public static String formatMoney(double num, int len) {
		NumberFormat formater = null;
		if (len == 0) {
			formater = new DecimalFormat("###,###");
		} else {
			StringBuffer buff = new StringBuffer();
			buff.append("###,###.");
			for (int i = 0; i < len; i++) {
				buff.append("#");
			}
			formater = new DecimalFormat(buff.toString());
		}
		String result = formater.format(num);
		if(result.indexOf(".") == -1){
			result = result + ".00";
		}
		return result;
	}
	
	public static String formatMoney(String str, int len) {
		if (StrKit.isBlank(str)) return "";
		str = delMoneyFormat(str);//先去除格式化
		double num = 0.0f;
		try {num = new BigDecimal(str).doubleValue();}catch(Exception e){e.printStackTrace();}
		return formatMoney(num,len);
	}
	
	/**
	 * 格式化金额		
	 * @param str
	 * @return
	 */
	public static String delMoneyFormat(String str) {
		if(StrKit.isBlank(str)) return "";
		return str.replace(",", "");
	}
	
	public static Map<String,String> getPartMap2(String amount){
		amount = "¥" + amount;
		return getPartMap(amount);
	}
	
	/**
	 * 把一个数字金额转换成,暂时支持小数点后两位
	 * 	private String billion;//亿万
		private String must;//千万
		private String million;//百万
		private String hundredThousand;//十万
		private String tenThousand;//一万
		private String thousand;//千
		private String hundred;//百
		private String ten;//十
		private String oneYuan;//元
		private String corner;//角
		private String penny;//分
	 * @param amount
	 */
	public static Map<String,String> getPartMap(String amount){
		if(StrKit.isBlank(amount)) return null;
		amount = amount.replace(",", "");
		if(!amount.contains(".")){
			amount = amount + ".00";
		}
		
		Map<String,String> map = new LinkedHashMap<String,String>();
		
		int posIndex = amount.indexOf(".");
		
		String billion = getCharByIndex(amount,posIndex - 9);//亿万
		String must = getCharByIndex(amount,posIndex - 8);//千万
		String million = getCharByIndex(amount,posIndex - 7);//百万
		String hundredThousand = getCharByIndex(amount,posIndex - 6);//十万
		String tenThousand = getCharByIndex(amount,posIndex - 5);//一万
		String thousand = getCharByIndex(amount,posIndex - 4);//千
		String hundred = getCharByIndex(amount,posIndex - 3);//百
		String ten = getCharByIndex(amount,posIndex - 2);//十
		String oneYuan = getCharByIndex(amount,posIndex - 1);//元
		String corner = getCharByIndex(amount,posIndex + 1);
		String penny = getCharByIndex(amount,posIndex + 2);
		
		map.put("billion", billion);
		map.put("must", must);
		map.put("million", million);
		map.put("hundredThousand", hundredThousand);
		map.put("tenThousand", tenThousand);
		map.put("thousand", thousand);
		map.put("hundred", hundred);
		map.put("ten", ten);
		map.put("oneYuan", oneYuan);
		map.put("corner", corner);
		map.put("penny", penny);
		
		return map;
	}
	
	private static String getCharByIndex(String str,int index){
		try{
			return String.valueOf(str.charAt(index));
		}catch(StringIndexOutOfBoundsException e){
			return "";
		}
	}

	/**
	 * 从string得到double的金额
	 * @param str
	 */
	public static double getDouble(String str){
		if(StrKit.isBlank(str)) return 0.0f;
		str = MoneyKit.delMoneyFormat(str);
		return new BigDecimal(str).doubleValue();
	}

	public static void main(String[] args) {
		String amount = "2,221,999.99";
		System.out.println("小写金额" + amount + "转换大写金额:" + toChinese(amount));

		String chnAmount = "玖萬玖仟玖佰玖拾玖圓玖角玖分";
		System.out.println(chnAmount + "-简体化:" + toSimplified(chnAmount));

		String chineseAmount1 = "玖萬玖仟玖佰玖拾玖圓";
		System.out.println("小写金额：" + amount + " 与 大写金额:" + chineseAmount1
				+ " 比较结果--------" + isAmtEqChn(amount,chineseAmount1));
		
		String chineseAmount2 = toSimplified(chineseAmount1);
		System.out.println("小写金额：" + amount + " 与 大写金额:" + chineseAmount2 
				+ " 比较结果--------" + isAmtEqChn(amount, chineseAmount2));
		
		String rs1 = formatMoney("222,302,9.99", 3);
		System.out.println(rs1);
		String rs3 = formatMoney("", 3);
		System.out.println(rs3);
		
		String rs2 = delMoneyFormat("22,230,291.00");
		System.out.println(rs2);
	}
}
