package com.highguard.Wisdom.struts.actions.appApi;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.highguard.Wisdom.mgmt.hibernate.beans.Device;
import com.highguard.Wisdom.struts.service.DeviceService;
import com.highguard.Wisdom.util.JsonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 设备相关查询结构
 * @author west
 *
 */
@Controller
@Scope("prototype")
public class DeviceApiAction extends ApiBaseAction{
	
	@Resource
	DeviceService deviceService;
	
	private String location;
				
	public void deviceList() throws UnsupportedEncodingException{
		if(null!= location && !location.equals("")){
			location = new String(location.getBytes("ISO-8859-1") , "utf-8");
		}
		JSONObject jsonMsg = JsonUtil.JsonMsg(true, "", "查询成功");
		List<Device> deviceList = deviceService.getDeviceList(location);
		jsonMsg.put("data", JSONArray.fromObject(deviceList));
		writeToFrontPage(jsonMsg);
	}
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
	
	
}
