package com.zfer.kit;

import java.math.BigDecimal;

public class NumberKit {
	/**
	 * validate number str
	 */
	public static boolean validateNumberStr(String moneyStr){
		if(StrKit.isBlank(moneyStr))
			moneyStr = "0.00";
		moneyStr = moneyStr.replace(",", "").replace("%", "");
		try{
			new BigDecimal(moneyStr);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * validate number str
	 */
	public static String getNumberStr(String moneyStr){
		if(StrKit.isBlank(moneyStr))
			return "0.00";
		if(validateNumberStr(moneyStr)){
			moneyStr = moneyStr.replace(",", "").replace("%", "");
			return moneyStr;
		}
		return "0.00";
	}
}
