package com.highguard.Wisdom.util;

import java.util.Random;

import com.mysql.jdbc.MiniAdmin;

public class NumberUtil {
	public static int randomNumber(int min,int max) {
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }
	
	public static String getRandomNumbers(int length){
		String numbers="";
		for (int i = 0; i < length; i++) {
			numbers+=NumberUtil.randomNumber(0, 10);
		}
		return numbers;
	}
	
}
