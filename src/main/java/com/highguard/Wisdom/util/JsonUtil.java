package com.highguard.Wisdom.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletRequest;

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
	public static String getRequestInputStream(ServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					request.getInputStream(), "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return buffer.toString();
	}

}
