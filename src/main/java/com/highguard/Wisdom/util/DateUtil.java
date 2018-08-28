package com.highguard.Wisdom.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static String formatDate(Date date,String pattern){
		SimpleDateFormat format =new SimpleDateFormat(pattern);
		return  format.format(date);
	}
}
