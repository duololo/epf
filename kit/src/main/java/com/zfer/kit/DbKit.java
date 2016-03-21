package com.zfer.kit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.sql.Clob;;

public class DbKit {
	private DbKit(){}

	/** 
	 * 将Oracle的Clob(这里使用java.sql.Clob接口传入) 转成String类型   
	 * 使用方法：
	 * CLOB clob = (CLOB) resultSet.getClob("OPINION");
	 * String str = clobToString(clob);
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static String clobToString(Clob clob) throws SQLException, IOException {
		if(null == clob){
			return "";
		}
		String reString = "";   
		Reader is = clob.getCharacterStream();
		BufferedReader br = new BufferedReader(is);   
		String str = br.readLine();   
		StringBuffer sb = new StringBuffer();   
		while (str != null) {  
			sb.append(str);   
			str = br.readLine();   
		}   
		reString = sb.toString();  
		return reString;   
	}
}
