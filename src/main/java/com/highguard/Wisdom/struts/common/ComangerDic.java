package com.highguard.Wisdom.struts.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 字典类
 * 
 * @author Administrator
 * 
 */
public class ComangerDic {
	/**
	 * 根据页面传来的字典名称和code获取具体的值
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public static String getSelectValue(String name, String value) {
		Map map = new HashMap();
		String returnValue = "";
		if (name.equals("sex")) {
			map = DictionaryTool.getSex();
		} else if (name.equals("nation")) {
			map = DictionaryTool.getNation();
		} else if (name.equals("edu")) {
			map = DictionaryTool.getEdu();
		} else if (name.equals("party")) {
			map = DictionaryTool.getParty();
		}else if (name.equals("item")) {
			map = DictionaryTool.getItem();
		}else if(name.equals("rangeitem")){
			map = DictionaryTool.getRangeItem();
		}else if(name.equals("firstproject")){
			map = DictionaryTool.getFirstProject();
		}else if(name.equals("secondproject")){
			map = DictionaryTool.getSecondProject();
		}else if(name.equals("scoretype")){
			map = DictionaryTool.getScoreType();
		}else if(name.equals("agestage")){
			map = DictionaryTool.getAgeStage();
		}
        String rvalue = (String) map.get(value);
		return rvalue;
	}
    public String getName(){
    	return "aaa";
    }
}
