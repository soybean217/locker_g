package com.highguard.Wisdom.struts.actions.appApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.highguard.Wisdom.mgmt.hibernate.beans.SmsRecord;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;
import com.highguard.Wisdom.mgmt.hibernate.beans.UserInfo;
import com.highguard.Wisdom.mgmt.manager.UserInfoManager;
import com.highguard.Wisdom.mgmt.manager.UserManager;
import com.highguard.Wisdom.struts.constant.SmsTemplate;
import com.highguard.Wisdom.struts.dto.UserInformation;
import com.highguard.Wisdom.struts.service.ValidationService;
import com.highguard.Wisdom.util.JsonUtil;
import com.highguard.Wisdom.util.NumberUtil;
import com.highguard.Wisdom.util.Sendsms;
import com.highguard.Wisdom.util.StringUtil;

/**
 * 用户登录 和 亲情账户action
 * @author west
 *
 */
@Controller
@Scope("prototype")
public class ValidationAction extends ApiBaseAction {
	@Resource
	UserInfoManager userInfoManager;
	@Resource
	UserManager userManager;
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");

	
	private String phone;
	private String yzm;
	private String userId;
	private String id;
	private String userType;
	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
		this.userType = userType;
	}


	/**
	 * 添加亲情账户
	 */
	public void getaddFamilyNum(){
		JSONObject jsonMsg = JsonUtil.JsonMsg(true, "", "亲情账号已添加！");	
		Map map = new HashMap();
		map.put("phone", phone);
		map.put("userId", userId);
		if(null!= phone && !phone.equals("")){
			if(userInfoManager.getUserInfoListCount(map)){
				//该号码已经是亲情号
				jsonMsg=JsonUtil.JsonMsg(false, "", "该号码已经是亲情号！");
			}else if(userInfoManager.getUserListCount(map)){//该号码已经是主账号
				jsonMsg=JsonUtil.JsonMsg(false, "", "该号码已经是主账号！");
			}else if(userInfoManager.getUserInfoListCountOverThree(map)){
				jsonMsg=JsonUtil.JsonMsg(false, "", "亲情号已经达到3个！");
			}			 
			else {
				UserInfo userInfo = new UserInfo();
				userInfo.setCreatetime(sdf.format(new Date()));
				userInfo.setUserId(Integer.parseInt(userId));
				userInfo.setPhone(phone);
				userInfoManager.saveUserInfo(userInfo);
				jsonMsg=JsonUtil.JsonMsg(true, "", "添加亲情账号成功");
			}
		}else{
			jsonMsg=JsonUtil.JsonMsg(false, "", "亲情号码不能为空！");
		}
		writeToFrontPage(jsonMsg);
	}
	
	
	/**
	 * 删除亲情号
	 */
	public void deleteFamilyNum(){
		JSONObject jsonMsg = JsonUtil.JsonMsg(true, "", "亲情账号已删除！");	
		userInfoManager.deleteUserInfo(id);
		writeToFrontPage(jsonMsg);
		
	}
	
	/**
	 * 获取所有亲情号
	 */
	public void familyNumList(){
		if(null!=userId){
			List<UserInfo> list = userInfoManager.getUserInfoListByUserId(userId);
			JSONObject jsonMsg = JsonUtil.JsonMsg(true, "", "查询成功");
			jsonMsg.put("data", JSONArray.fromObject(list));
			writeToFrontPage(jsonMsg);
		}else{
			writeToFrontPage(JsonUtil.JsonMsg(false, "", "请补齐参数！"));
		}
	}
	
	public void getUserInfo(){
		if(null!=userId||null!=userType){
			//TODO  过滤不必要的字段信息
			UserInformation userinfo = validationService.getUserInformationById(userType,Integer.parseInt(userId));
			JSONObject jsonMsg = JsonUtil.JsonMsg(true, "", "查询成功");
			
			JSONObject json = new JSONObject();
			json.put("userMaster", JSONObject.fromObject(userinfo.getUserMaster()));
			json.put("userType", userinfo.getUserType());
			json.put("phoneNumber", userinfo.getPhoneNumber());
			json.put("userId", userinfo.getUserId());
			
			JSONArray array=new JSONArray();
			List<UserInfo> viceUsers = userinfo.getViceUsers();
			for (int i = 0; i < viceUsers.size(); i++) {
				JSONObject jsonTemp=new JSONObject();
				jsonTemp.put("phone", viceUsers.get(i).getPhone());
				jsonTemp.put("id", viceUsers.get(i).getId());
				array.add(jsonTemp);
			}
			
			json.put("viceUsers", array);
			
			jsonMsg.put("data", json);
			writeToFrontPage(jsonMsg);
		}else{
			writeToFrontPage(JsonUtil.JsonMsg(false, "", "请补齐参数！"));
		}
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getYzm() {
		return yzm;
	}

	public void setYzm(String yzm) {
		this.yzm = yzm;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
