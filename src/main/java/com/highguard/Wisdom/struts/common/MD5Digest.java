package com.highguard.Wisdom.struts.common;

import java.security.MessageDigest;

import com.highguard.Wisdom.logging.WisdomLogger;
import com.highguard.Wisdom.struts.constant.Constants;

public class MD5Digest {
	public static String encodeByMD5(String str) {
		String newStr = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			newStr = Base64.encode(md5.digest(str.getBytes(Constants.ENCODING_TYPE)));
		} catch (Exception e) {
			WisdomLogger.logError(e, "MD5Digest.encodeByMD5");
		}
		return newStr;
	}
	public static String getMd5(byte[] source) {
		String value = null;
		char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();
			
			char str[] = new char[16*2];
			
			int k = 0;
			for(int i = 0;i < 16;i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			value = new String(str);
		} catch (Exception e) {
			WisdomLogger.logError(e, "MD5Digest.getMd5");
		}
		
		return value;
	}
}
