package com.zfer.kit;

public class ExceptionKit {
	
	String defaultMsg = "系统繁忙，请稍候再试";
	boolean debug = true; 
	
	//TODO 国际化
	public String getFriendlyExceptionMsg(Exception e){
		String tipMessage = "";
		String eClassName = "";
		if(e.getCause() != null){
			eClassName = e.getCause().getClass().getName();
		}else if(e.getClass() != null){
			eClassName = e.getClass().getName();
		}
		
		if("java.lang.IllegalArgumentException".equals(eClassName)){
			tipMessage = e.getCause().getLocalizedMessage();
			if(debug){
				tipMessage = e.getCause().getMessage();
			}
			if(StrKit.isBlank(tipMessage)) tipMessage = "无效的参数";
        }else if("java.lang.NullPointerException".equals(eClassName)){
        	 tipMessage = "系统异常，请稍候再试[nullpointer]";
        }else{
        	tipMessage = e.getLocalizedMessage();
        	if(debug){
				tipMessage = e.getLocalizedMessage();
			}
        }
		
		if(StrKit.isBlank(tipMessage)){
			tipMessage = defaultMsg;
		}
		return tipMessage;
	}
}
