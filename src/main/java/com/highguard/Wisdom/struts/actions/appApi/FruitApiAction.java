package com.highguard.Wisdom.struts.actions.appApi;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.highguard.Wisdom.struts.service.FruitService;
import com.highguard.Wisdom.util.JsonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 水果相关查询结构
 * @author west
 *
 */
@Controller
@Scope("prototype")
public class FruitApiAction extends ApiBaseAction{
	
	@Resource
	FruitService fruitService;
	
	private int deviceId;
	
	public void fruitQueryByDevice(){
		if(0!=deviceId){
			List<Map<String, Object>> fruitsByDevice = fruitService.getFruitsByDevice(deviceId);
			JSONObject jsonMsg = JsonUtil.JsonMsg(true, "", "查询成功");
			jsonMsg.put("data", JSONArray.fromObject(fruitsByDevice));
			writeToFrontPage(jsonMsg);
		}else{
			writeToFrontPage(JsonUtil.JsonMsg(false, "", "请补齐参数！"));
		}
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	
	
	
}
