package com.highguard.Wisdom.struts.common;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
@SuppressWarnings("unchecked")
public class DictionaryTool {

	public static Map getSex() {
		Map sexMap = new LinkedHashMap();
		sexMap.put("1", "男性");
		sexMap.put("2", "女性");
		return sexMap;
	}

	public static Map getParty() {
		Map partyMap = new LinkedHashMap();
		partyMap.put("01", "中国党员");
		partyMap.put("02", "中国预备党员");
		partyMap.put("03", "共青团员");
		partyMap.put("04", "其他党派");
		partyMap.put("05", "群众");
		return partyMap;
	}

	public static Map getNation() {
		return Native.getNative();
	}

	public static Map getEdu() {
		Map eduMap = new LinkedHashMap();
		eduMap.put("1", "小学及以下");
		eduMap.put("2", "初中");
		eduMap.put("3", "高中");
		eduMap.put("4", "中专");
		eduMap.put("5", "大专");
		eduMap.put("6", "本科");
		eduMap.put("7", "研究生");
		eduMap.put("8", "硕士");
		eduMap.put("9", "博士");
		return eduMap;
	}
	

	public static Map getItem() {
		Map eduMap = new LinkedHashMap();
		eduMap.put("1", "俯卧撑（2分钟）");
		eduMap.put("2", "仰卧起坐（3分钟）");
		eduMap.put("3", "单杠引体向上（1分钟）");
		eduMap.put("4", "双杠臂屈伸（1分钟）");
		eduMap.put("5", "单腿深蹲起");
		eduMap.put("6", "双腿深蹲起");
		eduMap.put("7", "立定跳远");
		eduMap.put("8", "立位体前屈");
		eduMap.put("9", "100米跑");
		eduMap.put("10", "10*5往返跑");
		eduMap.put("11", "100米负重跑");
		eduMap.put("12", "3000米跑");
		eduMap.put("13", "1500米跑");
		eduMap.put("14", "组合练习");
		eduMap.put("15", "1000米跑");
		return eduMap;
	}
	
	public static Map getRangeItem() {
		Map eduMap = new LinkedHashMap();
		eduMap.put("1", "3000米跑");
		eduMap.put("2", "跑步训练");
		return eduMap;
	}
	
	public static Map getFirstProject() {
		Map eduMap = new LinkedHashMap();
		eduMap.put("1", "体能");
		return eduMap;
	}
	
	public static Map getScoreType() {
		Map eduMap = new LinkedHashMap();
		eduMap.put("1", "二级制");
		eduMap.put("2", "四级制");
	
		return eduMap;
	}
	
	
	public static Map getAgeStage() {
		Map eduMap = new LinkedHashMap();
		eduMap.put("1", "<25");
		eduMap.put("2", "25~29");
		eduMap.put("3", "30~34");
		eduMap.put("4", "35~39");
		eduMap.put("5", ">=40");
		return eduMap;
	}
	
	public static Map getSecondProject() {
		Map eduMap = new LinkedHashMap();
		eduMap.put("1", "3000米");
		eduMap.put("2", "5000米");
		return eduMap;
	}
	
	public static Map getGroupMap(){
		Map eduMap = new LinkedHashMap();
		eduMap.put(1, "党办秘书");
		eduMap.put(2, "综合部门值班室");
		eduMap.put(3, "值班室");
		eduMap.put(4, "省军区首长");
		eduMap.put(6, "部首长");
		eduMap.put(9, "处领导");
		eduMap.put(10, "司办办件秘书");
		eduMap.put(16, "普通用户");
		eduMap.put(60, "系统管理员");
		return eduMap;
	}

}
