package com.highguard.Wisdom.util;

import net.sf.json.JSONObject;

public class JsonUtil {
	//返回一个包含三个属性的JSONObject对象
	public static JSONObject JsonMsg(boolean state,String msgCode,String msg){
		JSONObject jsonMsg=new JSONObject();
		jsonMsg.put("state", state);
		jsonMsg.put("msgCode", msgCode);
		jsonMsg.put("msg", msg);
		return jsonMsg;
	}
}
