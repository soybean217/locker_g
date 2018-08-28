package com.highguard.Wisdom.struts.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 名族类
 * @author Administrator
 *
 */
public class Native {

	public static  Map<String,String> getNative(){
		Map<String,String> map = new HashMap<String,String>();
		String value = "汉族,蒙古族,回族,藏族,维吾尔族,苗族,彝族,壮族,布依族,朝鲜族,满族,侗族,瑶族,白族,土家族,哈尼族,哈萨克族,傣族,黎族,傈僳族,佤族,畲族,高山族,拉祜族,水族,东乡族,纳西族,景颇族,柯尔克孜族,土族,达斡尔族,仫佬族,羌族,布朗族,撒拉族,毛南族,仡佬族,锡伯族,阿昌族,普米族,塔吉克族,怒族,乌孜别克族,俄罗斯族,鄂温克族,德昂族,保安族,裕固族,京族,塔塔尔族,独龙族,鄂伦春族,赫哲族,门巴族,珞巴族,基诺族";
		for (int i = 0; i < value.split(",").length; i++) {
			map.put(i+1+"",value.split(",")[i] );
		}
		return map;
	}
}