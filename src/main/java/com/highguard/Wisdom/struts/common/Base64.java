package com.highguard.Wisdom.struts.common;

import java.io.IOException;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import com.highguard.Wisdom.logging.WisdomLogger;
import com.highguard.Wisdom.struts.constant.Constants;

public class Base64 {
	/**
	 * @param change to base64
	 */
	public static String getBase64(String str) {
		Encoder encoder = java.util.Base64.getEncoder();
		String Base64Code = encoder.encodeToString(str.getBytes());
//		String Base64Code = (new sun.misc.BASE64Encoder()).encode( str.getBytes());
		return Base64Code;
	}

	/**
	 * @param change base64 to String
	 */
	public static String getFromBASE64(String s) {
		if (s == null)
			return null;
		try {
			Decoder decoder = java.util.Base64.getDecoder();
			byte[] b = decoder.decode(s);
//			byte[] b = (new sun.misc.BASE64Decoder()).decodeBuffer(s); 
			return new String(b, Constants.ENCODING_GBK);
		} catch (Exception e) {
			return null;
		}
	}

	public static String encode(byte[] buffer) {
		Encoder encoder = java.util.Base64.getEncoder();
		String Base64Code = encoder.encodeToString(buffer);
		return Base64Code;
	}

	public static byte[] decoder(String str) {
		byte[] buffer = null;
		try {
			Decoder decoder = java.util.Base64.getDecoder();
			buffer = decoder.decode(str);
//			buffer = (new sun.misc.BASE64Decoder()).decodeBuffer(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			WisdomLogger.logError(e, "Base64.decoder");
		}
		return buffer;
	}
}
