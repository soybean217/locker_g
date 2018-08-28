package com.highguard.Wisdom.util;

import java.util.Date;

public class CodeUtil {
	
	public static synchronized String getCzOrderNo(String type){
		String code=type+"CZ-X65-";
		code+=DateUtil.formatDate(new Date(), "yyMMddHHmmssS");
		return code;
	}

}
