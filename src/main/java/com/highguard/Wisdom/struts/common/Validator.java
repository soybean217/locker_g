/*
 * Copyright(C)2009 Suzhou Highguard network Co. Ltd.
 * All right reserved.
 */
package com.highguard.Wisdom.struts.common;
/**
 * @description 验证类
 * @author 王从胜
 * @date 2009/11/17
 * @version Version 1.0
 */
public class Validator {
	/**
	 * 判断字段是否为空
	 * @param str 字符串
	 */
	public static boolean isNull(String str) {
		if(str == null || str.trim().equals("")) {
			return true;
		}		
		return false;
	}
	/**
	 * 判断字段是否为数字
	 * @param str 字符串
	 */
	public static boolean isNumber(String str) {
		String regex = "[0-9]\\d*";
		if(str.trim().matches(regex)) {
			return true;
		}
		return false;
	}
	/**
	 * 判断字段是否为端口
	 * @param str 字符串
	 */
	public static boolean isPort(String str) {
		return (isNumber(str) && Integer.valueOf(str)<65536);
	}
	/**
	 * 判断IP地址是否合法
	 * @param str 字符串
	 */
	public static boolean isIp(String str) {
		if(isNull(str)) {
			return false;
		}
		String regex = "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))";
		if(str.trim().matches(regex)) {
			return true;
		}		
		return false;
	}
	/**
	 * 判断MAC地址是否合法
	 * @param str 字符串
	 */
	public static boolean isMac(String str) {
		if(isNull(str)) {
			return false;
		}
		String regex = "[A-F\\d]{2}-[A-F\\d]{2}-[A-F\\d]{2}-[A-F\\d]{2}-[A-F\\d]{2}-[A-F\\d]{2}";
		if(str.trim().matches(regex)) {
			return true;
		}		
		return false;
	}
	/**
	 * 判断Email地址是否合法
	 * @param str 字符串
	 */
	public static boolean isEmail(String str) {
		if(isNull(str)) {
			return false;
		}
		String regex = "\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		if(str.trim().matches(regex)) {
			return true;
		}
		return false;
	}
}
