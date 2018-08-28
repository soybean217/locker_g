package com.highguard.Wisdom.struts.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encoder {
	  /**利用MD5进行加密
	   * @param str  待加密的字符串
	   * @return  加密后的字符串
	   * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
	   * @throws UnsupportedEncodingException  
	   */
	  public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException,Exception{
	    String returnCode = null;
		try{
		    //确定计算方法
		    MessageDigest md5=MessageDigest.getInstance("MD5");
		    //加密后的字符串
		    returnCode=Base64.encode(md5.digest(str.getBytes("utf-8")));
	    }catch(Exception e){
	    	throw e;
	    }
	    return returnCode;
	  } 
}
