package com.highguard.Wisdom.struts.common;

import javax.servlet.http.HttpServletRequest;

import com.highguard.Wisdom.struts.constant.Constants;

public class OAsystemCommon {
	public static String getUser(HttpServletRequest req) throws Exception{
		String user = req.getHeader(Constants.Key_Current_User);
        
		if (user != null) {
			try{
				byte[] buffers = Base64.decoder(user);
				user = new String(buffers, Constants.ENCODING_TYPE).trim();
			} catch (Exception e){
				throw e;
			}
		}
		return user;
	}
	
	public static String decodePlus(String value) {
		char[] charArray = value.toCharArray();
		if (charArray != null && charArray.length > 0) {
			for (int i = 0; i < charArray.length; i++) {
				if (charArray[i] == ' ') {
					charArray[i] = '+';
				}
			}
			return String.valueOf(charArray);
		}
		return "";
	}
	
	public static Process processCMD(String cmd) throws Exception {
		//使用Runtime来执行command,生成Process对象
		Process process = Runtime.getRuntime().exec(cmd);
		return process;
	}
}
