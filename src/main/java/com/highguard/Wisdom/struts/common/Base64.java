package com.highguard.Wisdom.struts.common;

import java.io.IOException;

import com.highguard.Wisdom.logging.WisdomLogger;
import com.highguard.Wisdom.struts.constant.Constants;

public class Base64 {
	/**
	 * @param change to base64
	 */
	public static String getBase64(String str) {
		String Base64Code = (new sun.misc.BASE64Encoder()).encode( str.getBytes());
		return Base64Code;
	}
	
	/**
	 * @param change base64 to String
	 */
	public static String getFromBASE64(String s) { 
		if (s == null) return null; 
		try { 
			byte[] b = (new sun.misc.BASE64Decoder()).decodeBuffer(s); 
			return new String(b,Constants.ENCODING_GBK); 
		} catch (Exception e) { 
			return null; 
		} 
	}
	
	public static String encode(byte[] buffer) {
		return (new sun.misc.BASE64Encoder()).encode(buffer);
	}
	
	public static byte[] decoder(String str) {
		byte[] buffer = null;
		try {
			buffer = (new sun.misc.BASE64Decoder()).decodeBuffer(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			WisdomLogger.logError(e, "Base64.decoder");
		}
		return buffer;
	}
}
